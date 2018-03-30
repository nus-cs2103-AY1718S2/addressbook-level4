package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Client> filteredStudents;
    private final FilteredList<Client> filteredTutors;
    private SortedList<Client> rankedFilteredTutors;
    private SortedList<Client> rankedFilteredStudents;

    private SortedList<Client> sortedFilteredTutors;
    private SortedList<Client> sortedFilteredStudents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
        filteredTutors = new FilteredList<>(this.addressBook.getTutorList());
        sortedFilteredTutors = new SortedList<>(filteredTutors);
        sortedFilteredStudents = new SortedList<>(filteredStudents);
        rankedFilteredStudents = new SortedList<>(filteredStudents);
        rankedFilteredStudents = new SortedList<>(filteredTutors);

    }

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

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteClient(Client target, Category category) throws PersonNotFoundException {
        addressBook.removeClient(target, category);
        indicateAddressBookChanged();
    }

    @Override
    public void updateClient(Client target, Client editedPerson, Category category)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson, category);

        addressBook.updatePerson(target, editedPerson, category);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTutor(Client tutor) throws DuplicatePersonException {
        addressBook.addTutor(tutor);
        updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addStudent(Client student) throws DuplicatePersonException {
        addressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        indicateAddressBookChanged();
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tutor} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public void sortByNameFilteredClientTutorList() {
        Comparator<Client> sortByName = (tutor1, tutor2)-> (tutor1.getName().fullName)
                .compareToIgnoreCase(tutor2.getName().fullName);
        sortedFilteredTutors.setComparator(sortByName);
        indicateAddressBookChanged();
    }

    @Override
    public void sortByNameFilteredClientStudentList() {
        Comparator<Client> sortByName = (student1, student2)-> (student1.getName().fullName)
                .compareToIgnoreCase(student2.getName().fullName);
        sortedFilteredStudents.setComparator(sortByName);
        indicateAddressBookChanged();
    }

    @Override
    public void sortByLocationFilteredClientTutorList() {
        Comparator<Client> sortByLocation = (tutor1, tutor2)-> (tutor1.getLocation().value)
                .compareToIgnoreCase(tutor2.getLocation().value);
        sortedFilteredTutors.setComparator(sortByLocation);
        indicateAddressBookChanged();
    }

    @Override
    public void sortByLocationFilteredClientStudentList() {
        Comparator<Client> sortByLocation = (student1, student2)-> (student1.getLocation().value)
                .compareToIgnoreCase(student2.getLocation().value);
        sortedFilteredStudents.setComparator(sortByLocation);
        indicateAddressBookChanged();
    }


    @Override
    public void sortByGradeFilteredClientTutorList() {
        Comparator<Client> sortByGrade = new SortByGradeComparator();
        sortedFilteredTutors.setComparator(sortByGrade);
        indicateAddressBookChanged();
    }

    @Override
    public void sortByGradeFilteredClientStudentList() {
        Comparator<Client> sortByGrade = new SortByGradeComparator();
        sortedFilteredStudents.setComparator(sortByGrade);
        indicateAddressBookChanged();
    }

    @Override
    public void sortBySubjectFilteredClientTutorList() {
        Comparator<Client> sortBySubject = (tutor1, tutor2)-> (tutor1.getSubject().value)
                .compareToIgnoreCase(tutor2.getSubject().value);
        sortedFilteredTutors.setComparator(sortBySubject);
        indicateAddressBookChanged();
    }

    @Override
    public void sortBySubjectFilteredClientStudentList() {
        Comparator<Client> sortBySubject = (student1, student2)-> (student1.getSubject().value)
                .compareToIgnoreCase(student2.getSubject().value);
        sortedFilteredStudents.setComparator(sortBySubject);
        indicateAddressBookChanged();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Client> getFilteredStudentList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredStudents);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
        indicateAddressBookChanged();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Client> getFilteredTutorList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredTutors);
    }

    @Override
    public void updateFilteredTutorList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredTutors.setPredicate(predicate);
        indicateAddressBookChanged();
    }
    //=========== Ranked Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public void updateRankedStudentList() {

        for (int i = 53; i > 50; i--) {
            for (int j = 0; j < filteredStudents.size(); j++) {
                System.out.println("filteredStudents ranking: " + filteredStudents.get(j).getRank());
                if (filteredStudents.get(j).getRank() == i) {
                    rankedFilteredStudents.add(filteredStudents.get(j));
                }
            }
        }
        filteredStudents.setAll(rankedFilteredStudents);
        indicateAddressBookChanged();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public void updateRankedTutorList() {

        for (int i = 3; i > 0; i--) {
            for (int j = 0; j < filteredTutors.size(); j++) {
                if (filteredTutors.get(j).getRank() == i) {
                    rankedFilteredTutors.add(filteredTutors.get(j));
                }
            }
        }
        filteredTutors.setAll(rankedFilteredTutors);
        indicateAddressBookChanged();
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
                && filteredStudents.equals(other.filteredStudents)
                && filteredTutors.equals(other.filteredTutors);
    }

}
