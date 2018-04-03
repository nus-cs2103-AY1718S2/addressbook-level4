package seedu.address.commons.events.ui;

import java.util.ArrayList;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.listevent.ListEvent;

/**
 * Indicates a request to load calendar events to to do list window.
 */
public class ShowTodoListDisplayContentEvent extends BaseEvent {

    private final ArrayList<ListEvent> eventList;

    public ShowTodoListDisplayContentEvent(ArrayList<ListEvent> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ArrayList<ListEvent> getListEvent() {
        return this.eventList;
    }
}
