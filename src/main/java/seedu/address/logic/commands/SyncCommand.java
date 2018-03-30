package seedu.address.logic.commands;

import seedu.address.external.GoogleContactService;

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
        GoogleContactService newService = new GoogleContactService();
        newService.initialise();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
