//@@author kokonguyen191
package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.recipe.logic.CommandHistory;
import seedu.recipe.logic.UndoRedoStack;
import seedu.recipe.model.Model;
import seedu.recipe.model.recipe.Recipe;

/**
 * Parse the current page loaded in the BrowserPanel.
 */
public class ParseCommand extends Command {

    public static final String COMMAND_WORD = "parse";
    public static final String MESSAGE_SUCCESS = "Recipe parsed.";
    public static final String MESSAGE_NO_PAGE_LOADED = "No page loaded in the BrowserPanel.";
    public static final String MESSAGE_CANNOT_PARSE = "Cannot parse recipe from webpage.";

//    private final Recipe parsedRecipe;

    @Override
    public CommandResult execute() {
//        List<String> previousCommands = history.getHistory();
//
//        if (previousCommands.isEmpty()) {
//            return new CommandResult(MESSAGE_NO_HISTORY);
//        }
//
//        Collections.reverse(previousCommands);
//        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
        throw new AssertionError("DAH");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ParseCommand); // instanceof handles nulls
//                && this.parsedRecipe.equals(((ParseCommand) other).parsedRecipe)); // state check
    }
}
