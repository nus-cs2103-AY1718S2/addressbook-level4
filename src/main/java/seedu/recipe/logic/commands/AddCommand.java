package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_COOKING_TIME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_IMG;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_PREPARATION_TIME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_SERVINGS;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_URL;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;

/**
 * Adds a recipe to the recipe book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe to the recipe book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_INGREDIENT + "INGREDIENT "
            + PREFIX_INSTRUCTION + "INSTRUCTION "
            + "[" + PREFIX_COOKING_TIME + "COOKING_TIME] "
            + "[" + PREFIX_PREPARATION_TIME + "PREPARATION_TIME] "
            + "[" + PREFIX_CALORIES + "CALORIES] "
            + "[" + PREFIX_SERVINGS + "SERVINGS] "
            + "[" + PREFIX_URL + "URL]\n"
            + "[" + PREFIX_IMG + "IMAGE_PATH_NAME]\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example:\n" + COMMAND_WORD + "\n"
            + PREFIX_NAME + " Chicken Rice\n"
            + PREFIX_INGREDIENT + " chicken, rice\n"
            + PREFIX_INSTRUCTION + "\nCook rice\nCook chicken\nEnjoy\n"
            + PREFIX_COOKING_TIME + " 15 mins\n"
            + PREFIX_PREPARATION_TIME + " 5 mins\n"
            + PREFIX_CALORIES + " 500\n"
            + PREFIX_SERVINGS + " 2\n"
            + PREFIX_URL + " http://recipes.wikia.com/wiki/Hainanese_Chicken_Rice\n"
            + PREFIX_IMG + " /Users/administrator/Desktop/myImage.jpg (for macOS) OR "
            + PREFIX_IMG + " C:\\Users\\administrator\\myImage.jpg (for Windows)\n"
            + PREFIX_TAG + "yummy "
            + PREFIX_TAG + "best";

    public static final String MESSAGE_SUCCESS = "New recipe added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the recipe book";

    private final Recipe toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Recipe}
     */
    public AddCommand(Recipe recipe) {
        requireNonNull(recipe);
        toAdd = recipe;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addRecipe(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateRecipeException e) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
