package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyRecipeBook previousRecipeBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#recipeBook}.
     */
    private void saveRecipeBookSnapshot() {
        requireNonNull(model);
        this.previousRecipeBook = new RecipeBook(model.getRecipeBook());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the RecipeBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousRecipeBook);
        model.resetData(previousRecipeBook);
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
        saveRecipeBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
