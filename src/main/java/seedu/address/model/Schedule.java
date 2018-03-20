package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Schedule implements ReadOnlySchedule {

    private final LessonList lessons;
    {
        lessons = new LessonList();
    }
    public void addLesson(Lesson l) {
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any student
        // in the student list.
        lessons.add(l);
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
