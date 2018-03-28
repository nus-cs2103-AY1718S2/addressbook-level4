//@@author jaronchan

package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class ShowInvalidAddressOverlayEvent extends BaseEvent {

    private final boolean isInvalidAddress;

    public ShowInvalidAddressOverlayEvent(boolean isInvalidAddress) {
        this.isInvalidAddress = isInvalidAddress;
    }

    public boolean getAddressValidity() {
        return isInvalidAddress;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
