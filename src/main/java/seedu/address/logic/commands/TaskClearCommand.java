package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.TaskBook;

//@@author CYX28
/**
 * Clears the task book.
 */
public class TaskClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task-clear";
    public static final String COMMAND_ALIAS = "tc";
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new TaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
