package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;

/**
 * Adds a to-do to the address book.
 */
public class AddToDoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addToDo";
    public static final String COMMAND_ALIAS = "aTD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a to-do to the address book. "
            + "Parameters: "
            + "TO-DO DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + "Organize a meeting";

    public static final String MESSAGE_SUCCESS = "New to-do added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This to-do already exists in the address book";

    private final ToDo toAdd;

    /**
     * Creates an AddToDoCommand to add the specified {@code ToDo}
     */
    public AddToDoCommand(ToDo todo) {
        requireNonNull(todo);
        toAdd = todo;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addToDo(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateToDoException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddToDoCommand // instanceof handles nulls
                && toAdd.equals(((AddToDoCommand) other).toAdd));
    }
}
