package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.storage.XmlDeskBoardStorage;

import java.io.IOException;

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

    public static final String MESSAGE_DUPLICATE_ACTIVITY = "The following entry already exists in the desk board: %s";
    public static final String MESSAGE_FILE_NOT_FOUND = "Desk board file not found";
    public static final String MESSAGE_ILLEGAL_VALUES_IN_FILE = "Illegal values found in file: %s";
    public static final String MESSAGE_SUCCESS = "Data imported from: %1$s";

    private final String filePath;
    private ReadOnlyDeskBoard importedDeskBoard;

    /**
     * Creates an ImportCommand to import data from the specified {@code filePath}.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            importedDeskBoard = new XmlDeskBoardStorage(filePath).readDeskBoard().orElse(new DeskBoard());
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (DataConversionException dce) {
            throw new CommandException(String.format(MESSAGE_ILLEGAL_VALUES_IN_FILE, dce.getMessage()));
        }
        for (Activity activity : importedDeskBoard.getActivityList()) {
            try {
                model.addActivity(activity);
            } catch (DuplicateActivityException dae) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_ACTIVITY, activity));
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filePath.equals(((ImportCommand) other).filePath));
    }
}
