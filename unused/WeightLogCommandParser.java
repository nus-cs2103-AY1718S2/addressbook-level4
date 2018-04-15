package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.WeightLogCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author wenhao53-unused
// Feature is working but was unable to solve the many tests that were failing.
/**
 * Parses input arguments and creates a new WeightLogCommand object
 */
public class WeightLogCommandParser implements Parser<WeightLogCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WeightLogCommand
     * and returns a WeightLogCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public WeightLogCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WeightLogCommand.MESSAGE_USAGE));
        }

        return new WeightLogCommand(index);
    }
}
