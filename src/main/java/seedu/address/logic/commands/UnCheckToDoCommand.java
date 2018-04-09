//@@author nhatquang3112
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddToDoCommand.MESSAGE_DUPLICATE_TODO;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * Uncheck an existing ToDo in the address book as done.
 */
public class UnCheckToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "uncheck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unchecks the ToDo identified as done"
            + "by the index number used in the last ToDo listing. "
            + "Status of the ToDo will be overwritten as done.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNCHECK_TODO_SUCCESS = "Unchecked ToDo: %1$s";
    public static final String MESSAGE_NOT_UNCHECKED = "Unchecked ToDo failed.";

    private final Index index;

    private ToDo toDoToUnCheck;
    private ToDo unCheckedToDo;

    /**
     * @param index of the ToDo in the filtered ToDo list to uncheck
     */
    public UnCheckToDoCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateToDo(toDoToUnCheck, unCheckedToDo);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("The target ToDo cannot be missing");
        } catch (DuplicateToDoException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }
        model.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        return new CommandResult(String.format(MESSAGE_UNCHECK_TODO_SUCCESS, unCheckedToDo));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<ToDo> lastShownList = model.getFilteredToDoList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        toDoToUnCheck = lastShownList.get(index.getZeroBased());
        unCheckedToDo = createUnCheckedToDo(toDoToUnCheck);
    }

    /**
     * Creates and returns a {@code ToDo} with the content of {@code toDoToUnCheck}
     * unchecked as done.
     */
    private static ToDo createUnCheckedToDo(ToDo toDoToUnCheck) {
        assert toDoToUnCheck != null;

        Content updatedContent = toDoToUnCheck.getContent();
        Status updatedStatus = new Status("undone");

        return new ToDo(updatedContent, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnCheckToDoCommand)) {
            return false;
        }

        // state check
        UnCheckToDoCommand e = (UnCheckToDoCommand) other;
        return index.equals(e.index)
                && Objects.equals(toDoToUnCheck, e.toDoToUnCheck);
    }
}
