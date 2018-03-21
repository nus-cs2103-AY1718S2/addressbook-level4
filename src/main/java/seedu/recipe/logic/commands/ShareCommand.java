package seedu.recipe.logic.commands;

import java.util.List;

import seedu.recipe.commons.core.EventsCenter;
import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.ui.JumpToListRequestEvent;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.recipe.Recipe;

//@@author RyanAngJY
/**
 * Shares on Facebook a recipe identified using it's last displayed index from the recipe book.
 */
public class ShareCommand extends Command {

    public static final String COMMAND_WORD = "share";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shares on Facebook the recipe identified by the index number used in the last recipe listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RECIPE_SUCCESS = "Shared Recipe: %1$s";

    private final Index targetIndex;

    private Recipe recipeToShare;

    public ShareCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        // EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        recipeToShare = lastShownList.get(targetIndex.getZeroBased());
        System.out.println(recipeToShare.getIngredient());

        return new CommandResult(String.format(MESSAGE_SELECT_RECIPE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShareCommand // instanceof handles nulls
                && this.targetIndex.equals(((ShareCommand) other).targetIndex)); // state check
    }
}
//@@author
