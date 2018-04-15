//@@author amad-person
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a request to change the theme of the application.
 */
public class ChangeThemeEvent extends BaseEvent {

    private final String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return this.theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
