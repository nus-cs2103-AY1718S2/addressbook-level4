package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TaskAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;

/**
 * Parses input arguments and creates a new TaskAddCommand object
 */
public class TaskAddCommandParser implements Parser<TaskAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskAddCommand
     * and returns an TaskAddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TaskAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_PRIORITY,
                PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DUE_DATE, PREFIX_TASK_STATUS, PREFIX_TASK_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_TASK_PRIORITY, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_DUE_DATE, PREFIX_TASK_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE));
        }

        try {
            TaskName taskName =
                    ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME)).get();
            TaskPriority taskPriority =
                    ParserUtil.parseTaskPriority(argMultimap.getValue(PREFIX_TASK_PRIORITY)).get();
            TaskDescription taskDescription =
                    ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX_TASK_DESCRIPTION)).get();
            TaskDueDate taskDueDate =
                    ParserUtil.parseTaskDueDate(argMultimap.getValue(PREFIX_TASK_DUE_DATE)).get();
            TaskStatus taskStatus =
                    ParserUtil.parseTaskStatus(argMultimap.getValue(PREFIX_TASK_STATUS)).get();
            Set<TaskCategory> taskCategoryList =
                    ParserUtil.parseTaskCategories(argMultimap.getAllValues(PREFIX_TASK_CATEGORY));
            Task task = new Task(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, taskCategoryList);

            return new TaskAddCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
