package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.DeleteRecurredTasksCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

//@@author natania-d
/**
 * Parses input arguments and creates a new DeleteRecurredTasksCommand object
 */
public class DeleteRecurredTasksCommandParser implements Parser<DeleteRecurredTasksCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRecurredTasksCommand
     * and returns a DeleteRecurredTasksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRecurredTasksCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRecurredTasksCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecurredTasksCommand.MESSAGE_USAGE));
        }
    }

}
