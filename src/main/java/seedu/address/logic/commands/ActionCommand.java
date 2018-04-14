package seedu.address.logic.commands;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents an action with some optional context of a given type
 * with hidden internal logic and the ability to be executed,
 * Only for use in the latter argument as a condition-action rule pair.
 * Allows for extra context data to be set during rule testing.
 */
public abstract class ActionCommand<T> extends Command {
    /**
     * Sets the additional data required by this action
     * @param data The object that matched this rule
     * @param event The event that triggered this rule check
     */
    public abstract void setExtraData(T data, BaseEvent event);
}
