package seedu.address.logic.parser;

import java.io.IOException;

import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class to parse the import_contacts command
 */
public class ImportContactsCommandParser implements Parser<ImportContactsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportContactsCommand
     * and returns an ImportContactsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportContactsCommand parse(String args) throws ParseException {
        try {
            return new ImportContactsCommand(args);
        } catch (IOException ioe) {
            throw new ParseException(ioe.getMessage(), ioe);
        }
    }
}
