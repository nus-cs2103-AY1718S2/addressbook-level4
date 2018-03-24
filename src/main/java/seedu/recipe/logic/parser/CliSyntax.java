package seedu.recipe.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("Recipe Name:");
    public static final Prefix PREFIX_INGREDIENT = new Prefix("Ingredients:");
    public static final Prefix PREFIX_INSTRUCTION = new Prefix("Instruction:");
    public static final Prefix PREFIX_COOKING_TIME = new Prefix("Cooking Time:");
    public static final Prefix PREFIX_PREPARATION_TIME = new Prefix("Preparation Time:");
    public static final Prefix PREFIX_CALORIES = new Prefix("Calories:");
    public static final Prefix PREFIX_SERVINGS = new Prefix("Servings:");
    public static final Prefix PREFIX_TAG = new Prefix("#");
    public static final Prefix PREFIX_URL = new Prefix("URL:");

}
