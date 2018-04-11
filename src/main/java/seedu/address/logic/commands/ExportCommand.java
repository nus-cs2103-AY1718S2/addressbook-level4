package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilePath;

//@@author karenfrilya97
/**
 * Exports Desk Board data into an xml file in desired directory.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports desk board data into an xml file in desired directory. "
            + "Parameters: "
            + PREFIX_FILE_PATH + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE_PATH + "C:\\Users\\Karen\\Desktop\\deskboard.xml";

    public static final String MESSAGE_SUCCESS = "Data exported to: %1$s";
    public static final String MESSAGE_FILE_EXISTS = "File %s already exists";

    private final FilePath filePath;

    /**
     * Creates an ExportCommandParser to import data from the specified {@code filePath}.
     */
    public ExportCommand(FilePath filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireNonNull(storage);

        if (FileUtil.isFileExists(new File(filePath.value))) {
            throw new CommandException(String.format(MESSAGE_FILE_EXISTS, filePath.value));
        }

        try {

            storage.exportDeskBoard(model.getDeskBoard(), filePath.value);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath.value));
        } catch (IOException ioe) {
            throw new CommandException(ioe.getMessage());
        }
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.filePath.equals(((ExportCommand) other).filePath)); // state check
    }
}
