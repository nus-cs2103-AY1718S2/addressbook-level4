package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@author Kyomian
/**
 * Adds a task to the desk board.
 */
public class TaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the desk board. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATETIME + "DATETIME "
            + "[" + PREFIX_REMARK + "REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Software Engineering Milestone 1 "
            + PREFIX_DATETIME + "01/08/2018 17:00 "
            + PREFIX_REMARK + "Enhance major component";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the desk board";

    private final Task toAdd;

    /**
     * Creates a TaskCommand to add the specified {@code Task}
     */
    public TaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addActivity(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateActivityException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskCommand // instanceof handles nulls
                && toAdd.equals(((TaskCommand) other).toAdd));
    }
}
