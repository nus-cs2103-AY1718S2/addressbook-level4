//@@author A0143487X
package seedu.address.logic.parser;

import seedu.address.commons.exceptions.FileExistsException;
import seedu.address.commons.exceptions.IllegalFilenameException;
import seedu.address.logic.commands.ExportListedOrdersCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportListedOrdersCommand object
 */
public class ExportListedOrdersCommandParser implements Parser<ExportListedOrdersCommand> {
    public static final String MESSAGE_FILENAME_CONSTRAINTS = "FILENAME should be of the format nameOfFile\n"
            + "and adhere to the following constraints:\n"
            + "1. The FILENAME should only contain characters from digits 0-9 and alphabets a-z or A-Z\n"
            + "2. The FILENAME should be 30 characters or less.\n";
    public static final String MESSAGE_FILE_ALREADY_EXISTS = "File already exists, choose another filename.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportListedOrdersCommand
     * and returns an ExportListedOrdersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportListedOrdersCommand parse(String args) throws ParseException {
        try {
            String filename = ParserUtil.parseFilename(args);
            filename = filename + ".csv";

            return new ExportListedOrdersCommand(filename);
        } catch (IllegalFilenameException e) {
            throw new ParseException(MESSAGE_FILENAME_CONSTRAINTS);
        } catch (FileExistsException e) {
            throw new ParseException(MESSAGE_FILE_ALREADY_EXISTS);
        }
    }

}
