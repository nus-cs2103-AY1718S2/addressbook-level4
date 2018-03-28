package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.exceptions.ExistingFileException;
import seedu.address.commons.exceptions.InvalidFileException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.Storage;

/**
 * Exports the address book to a user-defined location {@code filePath}
 */
public class ExportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "exp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports current data into defined file path. "
            + "Parameters: FILEPATH (must end with an extension of .xml)\n"
            + "Example: " + COMMAND_WORD + " "
            + " C:\\Users\\John Doe\\Documents\\addressbook.xml\n";

    public static final String MESSAGE_EXPORT_SUCCESS = "Addressbook data exported to: %1$s";
    public static final String MESSAGE_NOT_XML_FILE = "Filepath does not lead to an XML file.";
    public static final String MESSAGE_ERROR = "Addressbook data not exported successfully.";
    public static final String MESSAGE_EXISTING_XML = "XML file name already exists. Choose a different name.";

    private Storage storage;
    private final String filePath;

    /**
     * Creates an ExportCommand to add the specified {@code String}
     */
    public ExportCommand(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * @param storage
     */
    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            storage.exportAddressBook(model.getAddressBook(), filePath);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (InvalidFileException e) {
            throw new CommandException(MESSAGE_NOT_XML_FILE);
        } catch (ExistingFileException e) {
            throw new CommandException(MESSAGE_EXISTING_XML);
        }
        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
    }


}

