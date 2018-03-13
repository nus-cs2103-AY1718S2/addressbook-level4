package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Imports an address book to the existing address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports an address book "
            + "from filepath to the existing address book. "
            + "Parameters: FILEPATH\n"
            + "Example: " + COMMAND_WORD + " "
            + "FILEPATH";

    public static final String MESSAGE_SUCCESS = "Persons and tags from Addressbook file successfully imported.";
    public static final String MESSAGE_FILE_NOT_FOUND = "Addressbook file is not found.";
    public static final String MESSAGE_DATA_CONVERSION_ERROR = "Addressbook file found is not in correct "
            + "format.";

    private final String filepath;

    /**
     * Creates an ImportCommand to import the specified {@code AddressBook} from filepath to
     * current {@code AddressBook}
     */
    public ImportCommand(String filepath) {
        requireNonNull(filepath);
        this.filepath = filepath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.importAddressBook(filepath);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (DataConversionException dce) {
            throw new CommandException(MESSAGE_DATA_CONVERSION_ERROR);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filepath.equals(((ImportCommand) other).filepath));
    }
}
