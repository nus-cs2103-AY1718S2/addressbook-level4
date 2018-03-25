package seedu.address.commons.events.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;

import java.util.ArrayList;

public class VenueTableEvent extends BaseEvent{

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
