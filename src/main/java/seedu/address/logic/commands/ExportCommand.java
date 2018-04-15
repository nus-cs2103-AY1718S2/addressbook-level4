package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Password;

//@@author Caijun7
/**
 * Exports an address book to the existing address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current view of StardyTogether "
            + "to specified filepath. "
            + "Parameters: FILEPATH PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + "data/addressbookbackup.xml "
            + "testpassword";

    public static final String MESSAGE_SUCCESS = "Current list of Persons, tags, or aliases from "
            + "StardyTogether are successfully exported.";
    public static final String MESSAGE_FILE_UNABLE_TO_SAVE = "Unable to save or overwrite to given filepath. "
            + "Please give another filepath.";

    private final String filepath;
    private final Password password;

    /**
     * Creates an ExportCommand to export the current view of {@code AddressBook} to the filepath without a password
     */
    public ExportCommand(String filepath) {
        requireNonNull(filepath);

        this.filepath = filepath;
        password = null;
    }

    /**
     * Creates an ExportCommand to export the current view of {@code AddressBook} to the filepath with a password
     */
    public ExportCommand(String filepath, String password) {
        requireNonNull(filepath);
        requireNonNull(password);

        this.filepath = filepath;
        this.password = new Password(password);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.exportAddressBook(filepath, password);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_UNABLE_TO_SAVE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && filepath.equals(((ExportCommand) other).filepath));
    }
}
