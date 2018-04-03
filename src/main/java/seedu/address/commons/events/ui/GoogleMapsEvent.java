package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jingyinno
/**
 * Represents a Google Maps event in GoogleMapsDisplay
 */
public class GoogleMapsEvent extends BaseEvent {

    private String locations;
    private boolean isOneLocationEvent;

    public GoogleMapsEvent(String locations, boolean isOneLocationEvent) {
        this.locations = locations;
        this.isOneLocationEvent = isOneLocationEvent;
    }

    public String getLocations() {
        return locations;
    }

    public boolean getIsOneLocationEvent() {
        return isOneLocationEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
