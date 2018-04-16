package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.TaskFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

//@@author CYX28
/**
 * Parses input arguments and creates a new TaskFindCommand object
 */
public class TaskFindCommandParser implements Parser<TaskFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskFindCommand
     * and returns a TaskFindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TaskFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskFindCommand.MESSAGE_USAGE));
        }

        String[] taskNameKeywords = trimmedArgs.split("\\s+");

        return new TaskFindCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList(taskNameKeywords)));
    }

}
