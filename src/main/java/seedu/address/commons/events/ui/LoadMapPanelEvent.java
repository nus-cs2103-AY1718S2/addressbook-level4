//@@author jaronchan
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event that triggers the loading of map panel of specified feature UI.
 */
public class LoadMapPanelEvent extends BaseEvent {

    private final String featureTarget;

    public LoadMapPanelEvent(String feature) {
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
