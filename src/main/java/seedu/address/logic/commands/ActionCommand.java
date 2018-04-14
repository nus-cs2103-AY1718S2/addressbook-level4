package seedu.address.logic.commands;

/**
 * Represents an action with some optional context of a given type
 * with hidden internal logic and the ability to be executed,
 * Only for use in the latter argument as a condition-action rule pair.
 * Allows for extra context data to be set during rule testing.
 */
public abstract class ActionCommand<T> extends Command {
    public abstract void setExtraData(T extra);
}
