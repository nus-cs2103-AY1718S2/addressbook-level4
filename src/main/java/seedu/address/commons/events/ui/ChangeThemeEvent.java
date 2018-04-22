package seedu.address.commons.events.ui;

//@@author Yoochard

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for Changing theme
 */
public class ChangeThemeEvent extends BaseEvent {
    private String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
