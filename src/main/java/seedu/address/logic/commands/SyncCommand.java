package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays the user's schedule.
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": syncs your schedule to the cloud";

    public static final String MESSAGE_SUCCESS = "Everything synced";

    public SyncCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.synchronize();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }
}
