package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the address book.
 */
public class TaskAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task-add";
    public static final String COMMAND_ALIAS = "ta";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: "
            + PREFIX_TASK_NAME + "NAME "
            + PREFIX_TASK_PRIORITY + "PRIORITY "
            + PREFIX_TASK_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TASK_DUE_DATE + "DUE DATE "
            + PREFIX_TASK_STATUS + "STATUS "
            + "[" + PREFIX_TASK_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_NAME + "Agenda for meeting "
            + PREFIX_TASK_PRIORITY + "High "
            + PREFIX_TASK_DESCRIPTION + "Discuss proposal details "
            + PREFIX_TASK_DUE_DATE + "2018-04-29 "
            + PREFIX_TASK_STATUS + "Undone "
            + PREFIX_TASK_CATEGORY + "Meeting";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Creates a TaskAddCommand to add the specified {@code Task}
     */
    public TaskAddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskAddCommand
                && toAdd.equals(((TaskAddCommand) other).toAdd));
    }

}
