package seedu.address.logic.parser;

import java.io.IOException;
import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Class to parse the import_contacts command
 */
public class ImportContactsCommandParser implements Parser<ImportContactsCommand> {
    /**
     * Self explanitory.
     */
    public static final String FAILED_TO_PARSE =
            "Failed to parse import_contacts command";

    /**
     * Parses the given {@code String} of arguments in the context of the ImportContactsCommand
     * and returns an ImportContactsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportContactsCommand parse(String args) throws ParseException {
        try {
            requireNonNull(args);
            //only 1 argument supported now
            return new ImportContactsCommand(args);
        } catch (Exception e) {
            throw new ParseException(FAILED_TO_PARSE, e);
        }
    }
}
