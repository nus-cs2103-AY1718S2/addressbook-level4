package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
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

    public Schedule() {}
    /**
     * Creates an Schedule using the Lesson in the {@code toBeCopied}
     */
    public Schedule(ReadOnlySchedule toBeCopied) {
        this();
        resetData(toBeCopied);
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
    public String print(AddressBook addressBook) {
        addressBook.printAll();
        int index = 1;
        Student student;
        String finalMessage = "\n" + this.toString();
        System.out.println(this.toString());
        printAll();
        for (Lesson l : lessons) {
            student = addressBook.findStudentByKey(l.getUniqueKey());
            String message = index++ + " " + student.getName() +  " " + l.toString();
            finalMessage = finalMessage + "\n" + message;
            System.out.println(message);
        }
        return finalMessage;
    }
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlySchedule newData) {
        requireNonNull(newData);
        List<Lesson> newList = newData.getSchedule().stream()
                .map(Lesson::getLesson)
                .collect(Collectors.toList());

        try {
            setLessons(newList);
        } catch (DuplicateLessonException e) {
            throw new AssertionError("Schedules should not have duplicate lessons");
        }
    }

    public void setLessons(List<Lesson> lessons) throws DuplicateLessonException {
        this.lessons.setLessons(lessons);
    }

    public void printAll() {
        for(Lesson l : lessons) {
            System.out.println(l.getUniqueKey() +
            " " + l.getDay() + " " + l.getStartTime() + " " + l.getEndTime());
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
