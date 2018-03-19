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
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.StudentInfoDisplayEvent;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
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
    public synchronized void deleteStudent(Student target) throws StudentNotFoundException {
        addressBook.removeStudent(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addStudent(Student student) throws DuplicateStudentException {
        addressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateStudent(Student target, Student editedStudent)
            throws DuplicateStudentException, StudentNotFoundException {
        requireAllNonNull(target, editedStudent);

        addressBook.updateStudent(target, editedStudent);
        indicateAddressBookChanged();
    }


    @Override
    public void deleteTag(Tag tag) {
        addressBook.removeTag(tag);
    }

    @Override
    public void displayStudentDetailsOnBrowserPanel(Student target) throws StudentNotFoundException {
        indicateBrowserPanelToDisplayStudent(target);
    }

    /** Raises an event to indicate Browser Panel display changed to display student's information */
    private void indicateBrowserPanelToDisplayStudent(Student target) {
        raise(new StudentInfoDisplayEvent(target));
    }


    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return FXCollections.unmodifiableObservableList(filteredStudents);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
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
                && filteredStudents.equals(other.filteredStudents);
    }

}
