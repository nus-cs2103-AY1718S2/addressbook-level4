package seedu.address.commons.events.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.WeeklyEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class TimetableChangedEvent extends BaseEvent {

    public final ObservableList<WeeklyEvent> timetable;

    public TimetableChangedEvent() {
        timetable = FXCollections.observableArrayList();
    }

    public TimetableChangedEvent(ObservableList<WeeklyEvent> newTimetable) {
        this.timetable = newTimetable;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
