package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.calendarfx.model.CalendarSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.model.calendar.exceptions.AppointmentNotFoundException;
import seedu.address.model.calendar.exceptions.DuplicateAppointmentException;
import seedu.address.model.calendar.exceptions.EditApointmentFailException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model, PredictionModel {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    //self instantiate new AddressBook and UserPrefs
    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }


    @Override
    public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
        addressBook.addAppointment(appointmentEntry);
        indicateAddressBookChanged();

    }

    @Override
    public void removeAppointment(String searchText) throws AppointmentNotFoundException {
        addressBook.removeAppointment(searchText);
        indicateAddressBookChanged();
    }

    @Override
    public void editAppointment(String searchText, AppointmentEntry reference) throws EditApointmentFailException {
        addressBook.editAppointment(searchText, reference);
        indicateAddressBookChanged();

    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        this.indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public CalendarSource getCalendar() {
        return addressBook.getCalendar();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    @Override
    public void preparePredictionData(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> targets) {
        ObservableList<Person> personList = this.getAddressBook().getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            double as = personList.get(i).getActualSpending().value;

            //the person has no actual spending recorded
            if (as == 0.0) {
                continue;
            }

            ArrayList<Double> row = new ArrayList<>();
            //record down the actual value
            row.add(personList.get(i).getIncome().value);
            row.add(personList.get(i).getAge().value.doubleValue());
            targets.add(as);


            //push to matrix
            matrix.add(row);
        }
    }

    @Override
    public void updatePredictionResult(ArrayList<Double> weights) throws CommandException {
        ObservableList<Person> personList = this.addressBook.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getActualSpending().value != 0.0) {
                //the person already has known value of spending
                continue;
            }

            //else update the person with expected spending
            Person p = personList.get(i);
            logger.info("Prediction results: income coefficient-> " + "\n"
                    + "Income coefficient: " + weights.get(0) + "\n"
                    + "Age coefficient: " + weights.get(1) + "\n"
            );
            Person updatedPerson = p.updateSelectedField(weights);
            //update the model here


            try {
                this.updatePerson(personList.get(i), updatedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            this.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }
}
