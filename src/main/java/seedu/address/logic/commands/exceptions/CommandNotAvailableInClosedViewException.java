package seedu.address.logic.commands.exceptions;

//@@author olimhc
/**
 * Signals that the command is not available in closed list view.
 */
public class CommandNotAvailableInClosedViewException extends CommandException {
    public CommandNotAvailableInClosedViewException() {
        super("Command is not available in closed list view."
                + " Please switch back to active list view with the command word: switch\n");
    }
}
