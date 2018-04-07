package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyOrganizer previousOrganizer;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#organizer}.
     */
    private void saveOrganizerSnapshot() {
        requireNonNull(model);
        this.previousOrganizer = new Organizer(model.getOrganizer());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the Organizer to the state before this command
     * was executed and updates the filtered task list to
     * show all tasks.
     */
    protected final void undo() {
        requireAllNonNull(model, previousOrganizer);
        model.resetData(previousOrganizer);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    /**
     * Executes the command and updates the filtered task
     * list to show all tasks.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveOrganizerSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
