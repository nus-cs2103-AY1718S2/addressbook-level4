package seedu.recipe.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import seedu.recipe.logic.parser.CliSyntax;

//@@author hoangduong1607
/**
 * Contains constants needed for auto-completion
 */
public class AutoCompletionUtil {
    public static final String[] APPLICATION_KEYWORDS = {"add", "clear", "delete", "edit", "exit", "find",
        "help", "history", "list", "redo", "select", "tag", "undo"};
    public static HashMap<String, ArrayList<String>> PREFIXES_FOR_COMMAND;
    public static final int MAX_SUGGESTIONS = 8;

    public void initialize() {
        ArrayList<String> addPrefixes = new ArrayList<>(Arrays.asList(CliSyntax.PREFIX_NAME.toString(),
                CliSyntax.PREFIX_INGREDIENT.toString(),
                CliSyntax.PREFIX_INSTRUCTION.toString(),
                CliSyntax.PREFIX_PREPARATION_TIME.toString(),
                CliSyntax.PREFIX_TAG.toString(),
                CliSyntax.PREFIX_URL.toString()
                ));
        PREFIXES_FOR_COMMAND.put("add", addPrefixes);
    }
}
