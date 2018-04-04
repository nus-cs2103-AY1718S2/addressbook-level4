package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CINEMAS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.MoviePlanner;
import seedu.address.model.ReadOnlyMoviePlanner;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyMoviePlanner previousMoviePlanner;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#moviePlanner}.
     */
    private void saveMoviePlannerSnapshot() {
        requireNonNull(model);
        this.previousMoviePlanner = new MoviePlanner(model.getMoviePlanner());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the MoviePlanner to the state before this command
     * was executed and updates the filtered cinema list to
     * show all cinemas.
     */
    protected final void undo() {
        requireAllNonNull(model, previousMoviePlanner);
        model.resetData(previousMoviePlanner);
        model.updateFilteredCinemaList(PREDICATE_SHOW_ALL_CINEMAS);
    }

    /**
     * Executes the command and updates the filtered cinema
     * list to show all cinemas.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredCinemaList(PREDICATE_SHOW_ALL_CINEMAS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveMoviePlannerSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
