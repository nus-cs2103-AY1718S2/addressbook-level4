package seedu.address.logic.parser;

import java.util.NoSuchElementException;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommandParser object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String userInput) throws ParseException {
        try {
            return new ImportCommand(userInput);
        } catch (NoSuchElementException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }
}
