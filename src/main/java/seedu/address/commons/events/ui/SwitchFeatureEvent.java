package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to switch the feature user interface.
 */
public class SwitchFeatureEvent extends BaseEvent {

    private final String featureTarget;

    public SwitchFeatureEvent(String feature) {
        this.featureTarget = feature;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getFeatureTarget() {
        return featureTarget;
    }

}