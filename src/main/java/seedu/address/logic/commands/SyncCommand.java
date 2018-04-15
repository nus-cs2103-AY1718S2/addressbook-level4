package seedu.address.logic.commands;

import java.io.IOException;

import com.google.gdata.util.ServiceException;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author demitycho

/**
 * Syncs the user's contact list and schedule to Google Contacts and Calendar.
 */
public class SyncCommand extends Command {

    public static final String COMMAND_WORD = "sync";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": syncs your schedule to the cloud";

    public static final String MESSAGE_SUCCESS = "Google Contacts and Calendar synced!";
    public static final String MESSAGE_FAILED_SYNC = "Failed to sync!";
    public static final String MESSAGE_GOOGLE_SERVICE_ISSUE = "There was a problem with the Google Service. Try again!";
    public static final String MESSAGE_NOT_LOGGED_IN = "You are not logged in!\n"
            + "Or your credentials have expired, logout and re-login to sync";

    public SyncCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.synchronize();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FAILED_SYNC);
        } catch (ServiceException se) {
            throw new CommandException(MESSAGE_GOOGLE_SERVICE_ISSUE);
        } catch (NullPointerException ne) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }
    }
}
