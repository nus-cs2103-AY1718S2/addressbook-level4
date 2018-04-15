package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.ViewCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author iNekox3
/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        try {
            String[] typeArray = ParserUtil.parseTabType(args);
            String type = typeArray[0];
            int weekNumber = -1;
            boolean isToggleExerciseByWeek = false;
            if (typeArray.length > 1) {
                weekNumber = Integer.parseInt(typeArray[1]);
                isToggleExerciseByWeek = true;
            }
            return new ViewCommand(type, weekNumber, isToggleExerciseByWeek);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }
    }
}
