package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX_OR_FORMAT;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.CompleteTaskCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

//@@author EdwardKSG
/**
 * Parses input arguments and creates a new CompleteTaskCommand object
 */
public class CompleteTaskCommandParser implements Parser<CompleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteTaskCommand
     * and returns an CompleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteTaskCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTaskIndex(args);
            return new CompleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_INDEX_OR_FORMAT, CompleteTaskCommand.MESSAGE_USAGE));
        }
    }

}
