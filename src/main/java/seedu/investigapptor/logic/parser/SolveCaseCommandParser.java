//@@author pkaijun
package seedu.investigapptor.logic.parser;

import static seedu.investigapptor.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.investigapptor.commons.core.index.Index;
import seedu.investigapptor.commons.exceptions.IllegalValueException;
import seedu.investigapptor.logic.commands.SolveCaseCommand;
import seedu.investigapptor.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SolveCaseCommandParser object
 */
public class SolveCaseCommandParser implements Parser<SolveCaseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SolveCaseCommand
     * and returns an SolveCaseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SolveCaseCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SolveCaseCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SolveCaseCommand.MESSAGE_USAGE));
        }
    }
}
