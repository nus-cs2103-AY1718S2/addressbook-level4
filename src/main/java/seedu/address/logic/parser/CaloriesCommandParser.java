package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CaloriesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new CaloriesCommand object
 */
public class CaloriesCommandParser implements Parser<CaloriesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CaloriesCommand
     * and returns an CaloriesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CaloriesCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CaloriesCommand.MESSAGE_USAGE));
        }

        return new CaloriesCommand(index);
    }
}
