package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TaskEditCommand;
import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.TaskCategory;

//@@author CYX28
/**
 * Parses input arguments and creates a new TaskEditCommand object
 */
public class TaskEditCommandParser implements Parser<TaskEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskEditCommand
     * and returns a TaskEditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public TaskEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_PRIORITY,
                PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DUE_DATE, PREFIX_TASK_STATUS, PREFIX_TASK_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskEditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME))
                    .ifPresent(editTaskDescriptor::setTaskName);
            ParserUtil.parseTaskPriority(argMultimap.getValue(PREFIX_TASK_PRIORITY))
                    .ifPresent(editTaskDescriptor::setTaskPriority);
            ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX_TASK_DESCRIPTION))
                    .ifPresent(editTaskDescriptor::setTaskDescription);
            ParserUtil.parseTaskDueDate(argMultimap.getValue(PREFIX_TASK_DUE_DATE))
                    .ifPresent(editTaskDescriptor::setTaskDueDate);
            ParserUtil.parseTaskStatus(argMultimap.getValue(PREFIX_TASK_STATUS))
                    .ifPresent(editTaskDescriptor::setTaskStatus);
            parseTaskCategoriesForEdit(argMultimap.getAllValues(PREFIX_TASK_CATEGORY))
                    .ifPresent(editTaskDescriptor::setTaskCategories);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TaskEditCommand.MESSAGE_NOT_EDITED);
        }

        return new TaskEditCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> taskCategories} into a {@code Set<TaskCategory>}
     * if {@code taskCategories} is non-empty.
     * If {@code taskCategories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<TaskCategory>} containing zero taskCategories.
     */
    private Optional<Set<TaskCategory>> parseTaskCategoriesForEdit(Collection<String> taskCategories)
            throws IllegalValueException {
        assert taskCategories != null;

        if (taskCategories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> taskCategorySet =
                taskCategories.size() == 1 && taskCategories.contains("") ? Collections.emptySet() : taskCategories;
        return Optional.of(ParserUtil.parseTaskCategories(taskCategorySet));
    }

}
