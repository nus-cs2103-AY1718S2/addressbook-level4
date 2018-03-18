package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recipe.model.RecipeBook;

/**
 * Clears the recipe book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Instruction book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new RecipeBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
