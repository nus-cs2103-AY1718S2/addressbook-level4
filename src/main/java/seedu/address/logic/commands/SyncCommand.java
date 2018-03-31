package seedu.address.logic.commands;

import seedu.address.external.OAuth2Sample;

/**
 * Displays the user's schedule.
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": syncs your schedule to the cloud";

    public static final String MESSAGE_SUCCESS = "Schedule synced";

    public SyncCommand() {}

    @Override
    public CommandResult execute() {
        OAuth2Sample newService = new OAuth2Sample();
        newService.initialise();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
