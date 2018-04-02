package seedu.address.commons.events.ui;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;

//@@author jingyinno
/**
 * Represents a schedule list in VenueTable
 */
public class VenueTableEvent extends BaseEvent {

    private final ObservableList<ArrayList<String>> schedule;

    public VenueTableEvent(ObservableList<ArrayList<String>> schedule) {
        this.schedule = schedule;
    }

    public ObservableList<ArrayList<String>> getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return null;
    }
}
