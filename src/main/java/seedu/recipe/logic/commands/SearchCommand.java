//@@Author kokonguyen191
package seedu.recipe.logic.commands;

import seedu.recipe.logic.commands.exceptions.CommandException;

/**
 * Selects a recipe identified using it's last displayed index from the recipe book.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search the recipe on recipes.wikia.com.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " chicken rice";
    public static final String MESSAGE_SUCCESS = "Successfully loaded: %1$s";

    private final String recipeToSearch;

    public SearchCommand(String recipeToSearch) {
        this.recipeToSearch = recipeToSearch;
    }

    @Override
    public CommandResult execute() throws CommandException {

        throw new CommandException("DUH");

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.recipeToSearch.equals(((SearchCommand) other).recipeToSearch)); // state check
    }
}

