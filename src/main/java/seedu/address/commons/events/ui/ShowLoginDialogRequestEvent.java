package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to view the login dialog.
 */
public class ShowLoginDialogRequestEvent extends BaseEvent {

    public final String loadUrl;

    public ShowLoginDialogRequestEvent(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
