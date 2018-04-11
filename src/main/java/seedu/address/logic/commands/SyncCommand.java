package seedu.address.logic.commands;

import java.io.IOException;

import com.google.gdata.util.ServiceException;

import seedu.address.logic.commands.exceptions.CommandException;

// @@author demitycho

/**
 * Syncs the user's contact list and schedule to Google Contacts and Calendar.
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": syncs your schedule to the cloud";

    public static final String MESSAGE_SUCCESS = "Google Contacts and Calendar synced!";

    public SyncCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.synchronize();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException ioe) {
            throw new CommandException("Failed to sync");
        } catch (ServiceException se) {
            throw new CommandException("GG");
        } catch (NullPointerException ne) {
            throw new CommandException("Lost");
        }
    }
}
