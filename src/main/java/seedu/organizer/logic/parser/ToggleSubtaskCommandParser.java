package seedu.organizer.logic.parser;

//@@author agus
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.ToggleSubtaskCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ToggleCommand object
 */
public class ToggleSubtaskCommandParser implements Parser<ToggleSubtaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ToggleCommand
     * and returns an ToggleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ToggleSubtaskCommand parse(String args) throws ParseException {
        try {
            Index[] indexs = ParserUtil.parseSubtaskIndex(args);
            return new ToggleSubtaskCommand(indexs[0], indexs[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleSubtaskCommand.MESSAGE_USAGE));
        }
    }
}
