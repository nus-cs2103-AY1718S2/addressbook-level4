package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yong-jie
/**
 * Parses input arguments and creates a new SelectCardCommand object
 */
public class SelectCardCommandParser implements Parser<SelectCardCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCardCommand
     * and returns an SelectCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCardCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));
        }
    }
}
