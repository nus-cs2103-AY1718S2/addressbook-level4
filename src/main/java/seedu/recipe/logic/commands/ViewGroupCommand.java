package seedu.recipe.logic.commands;

import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.ArrayList;

import seedu.recipe.model.recipe.GroupName;
import seedu.recipe.model.recipe.Recipe;

//@@author hoangduong1607

/**
 * Lists all recipes in a group to the user.
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "view_group";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views recipes in a group.\n"
            + "Parameters: GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " Best";
    public static final String MESSAGE_FAILURE = "Recipe group not found!";
    public static final String MESSAGE_SUCCESS = "Listed all recipes in [%s]";

    private GroupName groupName;

    public ViewGroupCommand(GroupName groupName) {
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute() {
        ArrayList<Recipe> newFilteredRecipeList = new ArrayList<>();

        for (Recipe recipe : model.getRecipeBook().getRecipeList()) {
            if (recipe.getGroupNames().contains(groupName)) {
                newFilteredRecipeList.add(recipe);
                System.out.println(recipe);
            }
        }

        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewGroupCommand // instanceof handles nulls
                && this.groupName.equals(((ViewGroupCommand) other).groupName)); // state check
    }
}
