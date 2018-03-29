//@@author kokonguyen191
package seedu.recipe.commons.events.ui;

import seedu.recipe.commons.events.BaseEvent;
import seedu.recipe.logic.commands.util.WikiaQueryHandler;

/**
 * Indicates a request to search for recipe on the internet
 */
public class InternetSearchRequestEvent extends BaseEvent {

    public final WikiaQueryHandler wikiaQueryHandlerImplementation;

    public InternetSearchRequestEvent(WikiaQueryHandler wikiaQueryHandlerImplementation) {
        this.wikiaQueryHandlerImplementation = wikiaQueryHandlerImplementation;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
