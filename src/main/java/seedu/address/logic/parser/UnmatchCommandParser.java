package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnmatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author alexawangzi
/**
 * Parses input arguments and creates a new UnmatchCommand object
 */
public class UnmatchCommandParser implements Parser<UnmatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmatchCommand
     * and returns an UnmatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmatchCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnmatchCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE));
        }
    }

}
