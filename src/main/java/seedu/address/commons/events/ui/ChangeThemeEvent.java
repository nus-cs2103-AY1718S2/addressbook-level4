package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**@@author Codee */
/**
 * Indicates a request for theme change.
 */
public class ChangeThemeEvent extends BaseEvent {

    public final String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
