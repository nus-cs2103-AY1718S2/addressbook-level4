package seedu.address.commons.events.ui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;

//@@author yeggasd
/**
 * Represents the TimeTable
 */
public class TimeTableEvent extends BaseEvent {

    private final ObservableList<ArrayList<String>> timetable;

    public TimeTableEvent(ObservableList<ArrayList<String>> timetable) {
        this.timetable = timetable;
    }

    public ObservableList<ArrayList<String>> getTimeTable() {
        return timetable;
    }

    @Override
    public String toString() {
        return null;
    }
}
