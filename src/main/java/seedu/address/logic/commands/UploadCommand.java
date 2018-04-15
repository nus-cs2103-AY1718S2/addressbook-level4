package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Password;
import seedu.address.storage.exceptions.GoogleAuthorizationException;
import seedu.address.storage.exceptions.RequestTimeoutException;

//@@author Caijun7
/**
 * Uploads an address book to the existing address book.
 */
public class UploadCommand extends Command {

    public static final String COMMAND_WORD = "upload";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Uploads the current view of StardyTogether "
            + "and saves it as specified filename in Google Drive. "
            + "Parameters: FILENAME PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + "addressbookbackup.xml "
            + "testpassword";

    public static final String MESSAGE_SUCCESS = "Current list of Persons, tags, or aliases from "
            + "StardyTogether are successfully uploaded.";
    public static final String MESSAGE_FILE_UNABLE_TO_SAVE = "Unable to save or overwrite to Google Drive. ";
    public static final String MESSAGE_NO_AUTHORIZATION = "Unable to access your Google Drive. "
            + "Please grant authorization.";
    public static final String MESSAGE_REQUEST_TIMEOUT = "Authorization request timed out. Please try again.";

    private final String filepath;
    private final Password password;

    /**
     * Creates an UploadCommand to upload the current view of {@code AddressBook} to the filepath without a password
     */
    public UploadCommand(String filepath) {
        requireNonNull(filepath);

        this.filepath = filepath;
        password = null;
    }

    /**
     * Creates an UploadCommand to upload the current view of {@code AddressBook} to the filepath with a password
     */
    public UploadCommand(String filepath, String password) {
        requireNonNull(filepath);
        requireNonNull(password);

        this.filepath = filepath;
        this.password = new Password(password);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.uploadAddressBook(filepath, password);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (GoogleAuthorizationException e) {
            throw new CommandException(MESSAGE_NO_AUTHORIZATION);
        } catch (RequestTimeoutException e) {
            throw new CommandException(MESSAGE_REQUEST_TIMEOUT);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_UNABLE_TO_SAVE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadCommand // instanceof handles nulls
                && filepath.equals(((UploadCommand) other).filepath));
    }
}
