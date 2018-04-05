package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.ResetTaskCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author EdwardKSG
/**
 * Parses input arguments and creates a new ResetTaskCommand object
 */
public class ResetTaskCommandParser implements Parser<ResetTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ResetTaskCommand
     * and returns an ResetTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ResetTaskCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTaskIndex(args);
            return new ResetTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetTaskCommand.MESSAGE_USAGE));
        }
    }

}
