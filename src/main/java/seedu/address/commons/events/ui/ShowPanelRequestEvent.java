package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Ang-YC
/**
 * Indicates a request for panel show
 */
public class ShowPanelRequestEvent extends BaseEvent {

    private final String panel;

    public ShowPanelRequestEvent(String panel) {
        this.panel = panel;
    }

    public String getRequestedPanel() {
        return panel;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
