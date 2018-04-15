package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.commons.util.SecurityUtil;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author Caijun7
/**
 * Imports an address book to the existing address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a StardyTogether addressbook "
            + "from filepath to the existing address book. "
            + "Parameters: FILEPATH PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + "data/addressbook.xml "
            + "testpassword";

    public static final String MESSAGE_SUCCESS = "Persons, tags, and aliases from "
            + "StardyTogether file successfully imported.";
    public static final String MESSAGE_FILE_NOT_FOUND = "StardyTogether file is not found.";
    public static final String MESSAGE_DATA_CONVERSION_ERROR = "StardyTogether file found is not in correct "
            + "format or wrong password.";
    public static final String MESSAGE_PASSWORD_WRONG = "Password wrong for StardyTogether file.";

    private final String filepath;
    private final byte[] password;

    /**
     * Creates an ImportCommand to import the specified {@code AddressBook} from filepath to
     * current {@code AddressBook} and decrypt without password
     */
    public ImportCommand(String filepath) {
        requireNonNull(filepath);

        this.filepath = filepath;
        password = null;
    }

    /**
     * Creates an ImportCommand to import the specified {@code AddressBook} from filepath to
     * current {@code AddressBook} and decrypt with password
     */
    public ImportCommand(String filepath, String password) {
        requireNonNull(filepath);
        requireNonNull(password);

        this.filepath = filepath;
        this.password = SecurityUtil.hashPassword(password);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.importAddressBook(filepath, password);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (WrongPasswordException wpe) {
            throw new CommandException(MESSAGE_PASSWORD_WRONG);
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
