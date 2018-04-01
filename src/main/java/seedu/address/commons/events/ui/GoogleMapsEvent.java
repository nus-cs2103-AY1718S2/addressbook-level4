package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class GoogleMapsEvent extends BaseEvent {

    public GoogleMapsEvent() {

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
