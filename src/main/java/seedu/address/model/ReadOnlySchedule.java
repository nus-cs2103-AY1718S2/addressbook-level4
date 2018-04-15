package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
//@@author demitycho
/**
 * Unmodifiable view of a schedule
 */
public interface ReadOnlySchedule {

    /**
     * Returns an unmodifiable view of the schedule.
     * This list will not contain any duplicate lessons or lessons that clash.
     */
    ObservableList<Lesson> getSchedule();

    Time getEarliestStartTime();

    Time getLatestEndTime();
}
