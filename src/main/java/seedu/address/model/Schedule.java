package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.student.Student;
//@@author demitycho
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
    public void addLesson(Lesson lessonToBeAdded)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
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
     * Resets the existing data of this {@code Schedule} with {@code newData}.
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
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new AssertionError("Schedules should not have clashing time slots");
        }
    }

    public void setLessons(List<Lesson> lessons)
            throws InvalidLessonTimeSlotException, DuplicateLessonException {
        this.lessons.setLessons(lessons);
    }

    /**
     * Deletes all Lessons in LessonList associated with a Student {@code UniqueKey key}
     * @param target
     * @throws LessonNotFoundException
     */
    public void removeStudentLessons(Student target)
            throws InvalidLessonTimeSlotException, DuplicateLessonException, LessonNotFoundException {
        lessons.removeStudentLessons(target);
    }

    /**
     * Finds the latest EndTime in the Schedule
     * @return
     */
    @Override
    public Time getLatestEndTime() {
        Time latest = new Time("00:00");
        for (Lesson l : lessons) {
            if (l.getEndTime().compareTo(latest) > 0) {
                latest = l.getEndTime();
            }
        }
        return latest;
    }

    /**
     * Finds the earliest StartTime in the Schedule
     * @return
     */
    @Override
    public Time getEarliestStartTime() {
        Time earliest = new Time("23:59");

        for (Lesson l : lessons) {
            if (l.getStartTime().compareTo(earliest) < 0) {
                earliest = l.getStartTime();
            }
        }
        return earliest;
    }

    //// util methods
    @Override
    public String toString() {
        return lessons.asObservableList().size() + " lessons";
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
