package seedu.address.model;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.student.Student;

/**
 * Wraps all data at the schedule level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Schedule implements ReadOnlySchedule {

    private final LessonList lessons;
    {
        lessons = new LessonList();
    }

    /**
     * Adds lesson to schedule
     * @param lessonToBeAdded
     * @throws InvalidLessonTimeSlotException if invalid
     */
    public void addLesson(Lesson lessonToBeAdded) throws InvalidLessonTimeSlotException {
        if (!isValidSlot(lessonToBeAdded)) {
            throw new InvalidLessonTimeSlotException();
        }
        lessons.add(lessonToBeAdded);
    }

    /**
     * Checks if lesson clashes with other lessons in the schedule
     * @return true/false
     */
    private boolean isValidSlot(Lesson l) {
        for (Lesson lesson : lessons) {
            if (l.clashesWith(lesson)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes {@code key} from this {@code Schedule}.
     * @throws LessonNotFoundException if the {@code key} is not in this {@code Schedule}.
     */
    public boolean removeLesson(Lesson key) throws LessonNotFoundException {
        if (lessons.remove(key)) {
            return true;
        } else {
            throw new LessonNotFoundException();
        }
    }

    /**
     * Prints the schedule as a list
     */
    public void print(AddressBook addressBook) {
        addressBook.printAll();
        int index = 1;
        Student student;
        System.out.println(this.toString());
        for (Lesson l : lessons) {
            student = addressBook.findStudentByKey(l.getUniqueKey());
            System.out.println(index++ + " " + student.getName() +  ": " + l.toString());
        }
    }

    //// util methods

    @Override
    public String toString() {
        return lessons.asObservableList().size() + " lessons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Lesson> getSchedule() {
        return lessons.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.lessons.equals(((Schedule) other).lessons));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(lessons);
    }
}
