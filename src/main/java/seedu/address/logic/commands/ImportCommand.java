package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Imports data from a xml file and overwrites the current data stored
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports data from an external xml data file with "
            + "the provided path and overwrites the current data stored.\n"
            + "Parameters: FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " ~/DOWNLOADS/NewDataSet.xml";

    public static final String MESSAGE_SUCCESS = "Data imported successfully from file: %1$s";
    public static final String MESSAGE_INVALID_PATH = "File not found";
    public static final String MESSAGE_INVALID_FILE = "Data configuration failed";

    private final String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        try {
            XmlAddressBookStorage newDataSet = new XmlAddressBookStorage(filePath);
            model.resetData(newDataSet.readAddressBook().orElseThrow(() -> new IOException(MESSAGE_INVALID_FILE)));
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_INVALID_PATH);
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_INVALID_FILE);
        }
    }
}
