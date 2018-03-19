package seedu.address.commons.events.ui;

import seedu.address.commons.core.Theme;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the application theme.
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final Theme newTheme;

    public ChangeThemeRequestEvent(Theme newTheme) {
        this.newTheme = newTheme;
    }

    @Override
    public String toString() {
        return "change theme to : " + newTheme.getThemeName();
    }
}
