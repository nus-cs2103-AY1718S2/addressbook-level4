package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_TASK_FILTER;

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
            int week = ParserUtil.parseTaskWeek(args);
            return new ViewTaskListCommand(week);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TASK_FILTER, ViewTaskListCommand.MESSAGE_USAGE));
        }
    }
}
