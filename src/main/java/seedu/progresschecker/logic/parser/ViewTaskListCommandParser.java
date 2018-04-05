package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.ViewTaskListCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author EdwardKSG
/**
 * Parses input arguments and creates a new ViewTaskListCommand object
 */
public class ViewTaskListCommandParser implements Parser<ViewTaskListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewTaskListCommand
     * and returns an ViewTaskListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTaskListCommand parse(String args) throws ParseException {
        try {
            String title = ParserUtil.parseTaskTitle(args);
            return new ViewTaskListCommand(); // title);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTaskListCommand.MESSAGE_USAGE));
        }
    }
}
