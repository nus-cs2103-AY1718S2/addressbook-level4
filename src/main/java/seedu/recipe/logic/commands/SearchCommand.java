//@@author kokonguyen191
package seedu.recipe.logic.commands;

import seedu.recipe.commons.core.EventsCenter;
import seedu.recipe.commons.events.ui.InternetSearchRequestEvent;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.commands.util.WikiaQueryHandler;

/**
 * Selects a recipe identified using it's last displayed index from the recipe book.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search the recipe on recipes.wikia.com.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " chicken rice";
    public static final String MESSAGE_SUCCESS = "Found %1$s recipe(s). Please wait...";

    private final String recipeToSearch;
    private final WikiaQueryHandler wikiaQueryHandlerImplementation;

    public SearchCommand(String recipeToSearch) {
        this.recipeToSearch = recipeToSearch;
        this.wikiaQueryHandlerImplementation = new WikiaQueryHandler(recipeToSearch);
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new InternetSearchRequestEvent(wikiaQueryHandlerImplementation));
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, wikiaQueryHandlerImplementation.getQueryNumberOfResults()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.recipeToSearch.equals(((SearchCommand) other).recipeToSearch)); // state check
    }
}

