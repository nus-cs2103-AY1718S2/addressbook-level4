//@@author jaronchan
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to load the direction between two addresses.
 */
public class LoadDirectionsEvent extends BaseEvent {

    private final String addressOrigin;

    private final String addressDestination;

    public LoadDirectionsEvent(String addressOrigin, String addressDestination) {
        this.addressOrigin = addressOrigin;
        this.addressDestination = addressDestination;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getAddressOrigin() {
        return addressOrigin;
    }

    public String getGetAddressDestination() {
        return addressDestination;
    }
}
