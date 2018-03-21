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
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
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
    private final Schedule schedule;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, Schedule schedule) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.schedule = schedule;
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Schedule());
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
    public void addLesson(Student studentToAddLesson, Day day, Time startTime, Time endTime)
            throws DuplicateLessonException, StudentNotFoundException, InvalidLessonTimeSlotException {
        requireAllNonNull(studentToAddLesson, day, startTime, endTime);

        Lesson newLesson = new Lesson(studentToAddLesson, day, startTime, endTime);
        schedule.addLesson(newLesson);
    }
    @Override
    public Schedule getSchedule() {
        return schedule;
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
