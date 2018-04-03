package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * Deletes a to-do identified using it's last displayed index from the address book.
 */
public class DeleteToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteToDo";
    public static final String COMMAND_ALIAS = "dTD";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the to-do identified by the index number used in the last to-do listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TODO_SUCCESS = "Deleted To-do: %1$s";

    private final Index targetIndex;

    private ToDo toDoToDelete;

    public DeleteToDoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(toDoToDelete);
        try {
            model.deleteToDo(toDoToDelete);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("The target to-do cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TODO_SUCCESS, toDoToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<ToDo> lastShownList = model.getFilteredToDoList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        toDoToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteToDoCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteToDoCommand) other).targetIndex) // state check
                && Objects.equals(this.toDoToDelete, ((DeleteToDoCommand) other).toDoToDelete));
    }
}
