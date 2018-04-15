package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.exceptions.StorageFileMissingException.STORAGE_FILE_MISSING;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.gdata.util.ServiceException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ScheduleChangedEvent;
import seedu.address.commons.events.model.StudentInfoChangedEvent;
import seedu.address.commons.events.model.StudentInfoDisplayEvent;
import seedu.address.commons.events.storage.ProfilePictureChangeEvent;
import seedu.address.commons.events.storage.RequiredStudentIndexChangeEvent;
import seedu.address.commons.exceptions.StorageFileMissingException;
import seedu.address.commons.util.FileUtil;
import seedu.address.external.GServiceManager;
import seedu.address.external.exceptions.CredentialsException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueKey;
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
    private final GServiceManager gServiceManager;
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, ReadOnlySchedule schedule) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.schedule = new Schedule(schedule);
        this.gServiceManager = new GServiceManager();
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Schedule());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData, ReadOnlySchedule newSchedule) {
        addressBook.resetData(newData);
        schedule.resetData(newSchedule);
        indicateStudentInfoChanged();
        indicateAddressBookChanged();
        indicateScheduleChanged();
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
    public synchronized void deleteStudent(Student target)
            throws InvalidLessonTimeSlotException, StudentNotFoundException,
            DuplicateLessonException, LessonNotFoundException {
        schedule.removeStudentLessons(target);
        indicateScheduleChanged();
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
        indicateStudentInfoChanged();
        indicateScheduleChanged();
    }


    @Override
    public void deleteTag(Tag tag) {
        addressBook.removeTag(tag);
    }

    //@@author
    @Override
    public void addLesson(Student studentToAddLesson, Day day, Time startTime, Time endTime)
            throws DuplicateLessonException, StudentNotFoundException, InvalidLessonTimeSlotException {
        requireAllNonNull(studentToAddLesson, day, startTime, endTime);
        UniqueKey studentKey = studentToAddLesson.getUniqueKey();
        Lesson newLesson = new Lesson(studentKey, day, startTime, endTime);
        schedule.addLesson(newLesson);
        indicateScheduleChanged();
    }

    private void indicateScheduleChanged() {
        raise(new ScheduleChangedEvent(schedule, addressBook));
        raise(new AddressBookChangedEvent(addressBook));
    }

    /**
     * Removes the {@code target} from schedule
     * @param target
     */
    @Override
    public synchronized void deleteLesson(Lesson target) throws LessonNotFoundException {
        schedule.removeLesson(target);
        indicateScheduleChanged();
    }

    @Override
    public ReadOnlySchedule getSchedule() {
        return schedule;
    }

    //@@author samuelloh
    /**
     * Displays Student details on a browser panel in the UI
     * @param target
     * @throws StudentNotFoundException
     */
    public void displayStudentDetailsOnBrowserPanel(Student target) throws StudentNotFoundException,
            StorageFileMissingException {
        addressBook.checkForStudentInAdressBook(target);
        checkIfStorageFileExists();
        indicateRequiredStudentIndexChange(filteredStudents.indexOf(target));
        indicateBrowserPanelToDisplayStudent(target);
    }

    /**
     * Checks if the xml file containing student's data exists.
     * @throws StorageFileMissingException
     */
    private void checkIfStorageFileExists() throws StorageFileMissingException {
        if (FileUtil.isFileExists(new File("data/addressBook.xml"))) {
            return;
        }
        throw new StorageFileMissingException(STORAGE_FILE_MISSING);

    }

    /** Raises an event to indicate that real xml data is required for moreInfo to function */

    /** Raises an event to indicate Browser Panel display changed to display student's information */
    private void indicateBrowserPanelToDisplayStudent(Student target) {
        raise(new StudentInfoDisplayEvent(target));
    }

    /** Raises an event to indicate a student's information has changed*/
    private void indicateStudentInfoChanged() {
        raise(new StudentInfoChangedEvent());
    }

    /** Raises an event to indicate an update of the student index required at the moment in storage */
    private void indicateRequiredStudentIndexChange(int studentIndex) {
        raise(new RequiredStudentIndexChangeEvent(studentIndex));
    }

    @Override
    public void updateProfilePicture (Student target, Student editedStudent, Student finalEditedStudent)
            throws DuplicateStudentException, StudentNotFoundException {

        requireAllNonNull(target, editedStudent);
        addressBook.updateStudent(target, editedStudent);
        indicateProfilePictureChange(editedStudent);
        addressBook.updateStudent(editedStudent, finalEditedStudent);
        indicateAddressBookChanged();
        indicateStudentInfoChanged();

    }

    /** Raises an event to indicate a student's profile picture has been changed*/
    private void indicateProfilePictureChange(Student target) {
        raise(new ProfilePictureChangeEvent(target));
    }
    //@@author

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

    //@@author demitycho
    @Override
    public void loginGoogleAccount() throws CredentialsException, IOException {
        this.gServiceManager.login();
    }

    @Override
    public void logoutGoogleAccount() throws CredentialsException {
        this.gServiceManager.logout();
    }

    @Override
    public void synchronize() throws ServiceException, IOException {
        this.gServiceManager.synchronize(addressBook, schedule);
    }
    //@@author

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
