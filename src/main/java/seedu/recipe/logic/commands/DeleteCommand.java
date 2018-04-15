package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;

/**
 * Deletes a recipe identified using it's last displayed index from the recipe book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recipe identified by the index number used in the last recipe listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RECIPE_SUCCESS = "Deleted Recipe: %1$s";

    private final Index targetIndex;

    private Recipe recipeToDelete;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(recipeToDelete);
        try {
            model.deleteRecipe(recipeToDelete);
        } catch (RecipeNotFoundException pnfe) {
            throw new AssertionError("The target recipe cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_RECIPE_SUCCESS, recipeToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        recipeToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.recipeToDelete, ((DeleteCommand) other).recipeToDelete));
    }
}
