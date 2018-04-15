package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Parses input arguments and creates a new SelectPersonCommand object
 */
public class SelectCommandParser implements Parser<SelectPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectPersonCommand
     * and returns an SelectPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectPersonCommand parse(String args, Model model) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectPersonCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectPersonCommand.MESSAGE_USAGE));
        }
    }
}
