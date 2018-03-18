package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ImdbChangedEvent;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Imdb Imdb;
    private final FilteredList<Patient> filteredPatients;

    /**
     * Initializes a ModelManager with the given Imdb and userPrefs.
     */
    public ModelManager(ReadOnlyImdb addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.Imdb = new Imdb(addressBook);
        filteredPatients = new FilteredList<>(this.Imdb.getPersonList());
    }

    public ModelManager() {
        this(new Imdb(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyImdb newData) {
        Imdb.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyImdb getImdb() {
        return Imdb;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ImdbChangedEvent(Imdb));
    }

    @Override
    public synchronized void deletePerson(Patient target) throws PatientNotFoundException {
        Imdb.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Patient patient) throws DuplicatePatientException {
        Imdb.addPerson(patient);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Patient target, Patient editedPatient)
            throws DuplicatePatientException, PatientNotFoundException {
        requireAllNonNull(target, editedPatient);

        Imdb.updatePerson(target, editedPatient);
        indicateAddressBookChanged();
    }

    @Override
    /**
     * Removes {@code tag} from all Persons
     */
    public void deleteTag(Tag tag) {
        Imdb.removeTag(tag);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code Imdb}
     */
    @Override
    public ObservableList<Patient> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPatients);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
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
        return Imdb.equals(other.Imdb)
                && filteredPatients.equals(other.filteredPatients);
    }

}
