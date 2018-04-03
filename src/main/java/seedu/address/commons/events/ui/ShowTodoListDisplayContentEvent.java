package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.listEvent.ListEvent;

import java.util.ArrayList;

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
