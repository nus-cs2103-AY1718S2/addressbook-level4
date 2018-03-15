package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.theme.Theme;

//@@author aquarinte
/**
 * Indicates a request to change Medeina's theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {
    public final Theme theme;

    public ChangeThemeRequestEvent(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
