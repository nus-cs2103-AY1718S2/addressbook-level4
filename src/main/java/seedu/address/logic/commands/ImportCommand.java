package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DeskBoard;
import seedu.address.model.FilePath;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.storage.XmlDeskBoardStorage;

//@@author karenfrilya97
/**
 * Imports desk board data from a given xml file.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports desk board data from a given xml file. "
            + "Parameters: "
            + PREFIX_FILE_PATH + "FILE PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE_PATH + "C:\\Users\\Karen\\IdeaProjects\\main\\data\\deskboard.xml";

    public static final String MESSAGE_FILE_NOT_FOUND = "Desk board file %s not found";
    public static final String MESSAGE_ILLEGAL_VALUES_IN_FILE = "Illegal values found in file: %s";
    public static final String MESSAGE_SUCCESS = "Data imported from: %1$s";

    private final FilePath filePath;

    /**
     * Creates an ImportCommand to import data from the specified {@code filePath}.
     */
    public ImportCommand(FilePath filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            ReadOnlyDeskBoard toImport = new XmlDeskBoardStorage(filePath.value).readDeskBoard()
                    .orElseThrow(() -> new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, filePath)));

            model.addActivities(toImport);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, filePath));
        } catch (DataConversionException dce) {
            throw new CommandException(String.format(MESSAGE_ILLEGAL_VALUES_IN_FILE, dce.getMessage()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filePath.equals(((ImportCommand) other).filePath));
    }
}
