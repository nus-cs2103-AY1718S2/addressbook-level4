package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

public class ShowTodoListDisplayContentEvent extends BaseEvent {

    private final Event event;

    public ShowTodoListDisplayContentEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Event getEvent() {
        return this.event;
    }
}
