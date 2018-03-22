package seedu.recipe.ui.util;

import seedu.recipe.logic.parser.CliSyntax;

public class AutoCompletionUtil {
    //@@author hoangduong1607
    public static final String[] APPLICATION_KEYWORDS = {"add", "clear", "delete", "edit", "exit", "find",
            "help", "history", "list", "redo", "select", "tag", "undo", CliSyntax.PREFIX_INGREDIENT.toString(),
            CliSyntax.PREFIX_INSTRUCTION.toString(), CliSyntax.PREFIX_NAME.toString(),
            CliSyntax.PREFIX_PREPARATION_TIME.toString(), CliSyntax.PREFIX_TAG.toString(),
            CliSyntax.PREFIX_URL.toString()};
    public static final int MAX_SUGGESTIONS = 8;
    //@@author
}
