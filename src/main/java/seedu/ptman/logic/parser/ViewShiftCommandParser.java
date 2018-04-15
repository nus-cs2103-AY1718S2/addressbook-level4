package seedu.ptman.logic.parser;

import static seedu.ptman.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ptman.commons.core.index.Index;
import seedu.ptman.commons.exceptions.IllegalValueException;
import seedu.ptman.logic.commands.ViewShiftCommand;
import seedu.ptman.logic.parser.exceptions.ParseException;

//@@author hzxcaryn
/**
 * Parses input arguments and creates a new ViewShiftCommand object
 */
public class ViewShiftCommandParser implements Parser<ViewShiftCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewShiftCommand
     * and returns an ViewShiftCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewShiftCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewShiftCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewShiftCommand.MESSAGE_USAGE));
        }
    }
}
