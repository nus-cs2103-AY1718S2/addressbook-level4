package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.progresschecker.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.ReadOnlyProgressChecker;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyProgressChecker previousProgressChecker;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#progressChecker}.
     */
    private void saveProgressCheckerSnapshot() {
        requireNonNull(model);
        this.previousProgressChecker = new ProgressChecker(model.getProgressChecker());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the ProgressChecker to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousProgressChecker);
        model.resetData(previousProgressChecker);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveProgressCheckerSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
