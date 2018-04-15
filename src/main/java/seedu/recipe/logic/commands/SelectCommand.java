package seedu.recipe.logic.commands;

import java.util.List;

import seedu.recipe.commons.core.EventsCenter;
import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.ui.JumpToListRequestEvent;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.recipe.Recipe;

/**
 * Selects a recipe identified using it's last displayed index from the recipe book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the recipe identified by the index number used in the last recipe listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RECIPE_SUCCESS = "Selected Recipe:\n%s";

    private final Index targetIndex;
    private Recipe selectedRecipe = null;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        selectedRecipe = model.getFilteredRecipeList().get(targetIndex.getZeroBased());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_RECIPE_SUCCESS,
                selectedRecipe.getTextFormattedRecipe()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
