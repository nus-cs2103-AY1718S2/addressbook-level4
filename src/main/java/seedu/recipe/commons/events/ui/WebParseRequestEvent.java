//@@author kokonguyen191
package seedu.recipe.commons.events.ui;

import seedu.recipe.commons.events.BaseEvent;
import seedu.recipe.logic.commands.util.WikiaQueryHandler;

/**
 * Indicates a request to parse the page loaded in BrowserPanel.
 */
public class WebParseRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
