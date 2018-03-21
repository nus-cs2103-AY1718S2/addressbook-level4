package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;

/**
 * Wraps all data at the schedule level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Schedule implements ReadOnlySchedule {

    private final LessonList lessons;
    {
        lessons = new LessonList();
    }
    public void addLesson(Lesson l) throws InvalidLessonTimeSlotException {
        if(!isValidSlot(l)) {
            throw new InvalidLessonTimeSlotException();
        }
        lessons.add(l);
    }

    private boolean isValidSlot(Lesson l){
        for(Lesson lesson : lessons){
            if(l.clashesWith(lesson)){
                return false;
            }
        }
        return true;
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
    public void print(){
        System.out.println(this.toString());
        for(Lesson l : lessons){
            System.out.println(l.toString());
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(lessons);
    }
}
