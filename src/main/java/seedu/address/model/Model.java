package seedu.address.model;

import java.io.IOException;
import java.util.function.Predicate;

import com.google.gdata.util.ServiceException;

import javafx.collections.ObservableList;
import seedu.address.external.exceptions.CredentialsException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<Student> PREDICATE_SHOW_FAVOURITE_STUDENTS = Student::isFavourite;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given student. */
    void deleteStudent(Student target) throws StudentNotFoundException;

    /** Adds the given student */
    void addStudent(Student student) throws DuplicateStudentException;

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     *
     * @throws DuplicateStudentException if updating the student's details causes the student to be equivalent to
     *      another existing student in the list.
     * @throws StudentNotFoundException if {@code target} could not be found in the list.
     */
    void updateStudent(Student target, Student editedStudent)
            throws DuplicateStudentException, StudentNotFoundException;

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /** Removes the given {@code tag} from all {@code Student}s. */
    void deleteTag(Tag tag);

    void addLesson(Student student, Day day, Time startTime, Time endTime)
            throws DuplicateLessonException, StudentNotFoundException, InvalidLessonTimeSlotException;

    void deleteLesson(Lesson target) throws LessonNotFoundException;

    Schedule getSchedule();

    String printSchedule();

    void displayStudentDetailsOnBrowserPanel(Student target) throws StudentNotFoundException;

    void updateProfilePicture(Student target, Student editedStudent, Student finalEditedStudent)
        throws DuplicateStudentException, StudentNotFoundException;

    void loginGoogleAccount() throws CredentialsException;

    void logoutGoogleAccount() throws CredentialsException;

    void synchronize() throws ServiceException, IOException;
}
