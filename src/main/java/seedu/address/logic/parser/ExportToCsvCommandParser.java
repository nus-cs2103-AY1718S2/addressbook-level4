package seedu.address.logic.parser;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ExportToCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportToCsvCommand object
 */
public class ExportToCsvCommandParser implements Parser<ExportToCsvCommand> {

    public static final String MESSAGE_FILENAME_CONSTRAINTS = "1. The FILENAME should only contain "
            + "characters from digits 0-9 and alphabets a-z or A-Z\n"
            + "2. The nameOfFile should consist of at least 1 character and at most 30 characters.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportToCsvCommand
     * and returns an ExportToCsvCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportToCsvCommand parse(String args) throws ParseException {
        try {
            String filename = ParserUtil.parseFilename(args);

            // block for  creating file
            File outputFile = new File("data/exportedFile/" + filename + ".csv");

            int fExistedNameChanged = 0;
            while (outputFile.exists()) {
                fExistedNameChanged = 1;
                filename += "(1)";
                //filename changed
                outputFile = new File("data/exportedFile/" + filename + ".csv");
            }
            //

            return new ExportToCsvCommand(outputFile.toString(), fExistedNameChanged);
        } catch (IllegalValueException e) {
            throw new ParseException("Invalid filename format!\n"
                    + ExportToCsvCommand.MESSAGE_USAGE + "\n"
                    + MESSAGE_FILENAME_CONSTRAINTS);
        }
    }

}
