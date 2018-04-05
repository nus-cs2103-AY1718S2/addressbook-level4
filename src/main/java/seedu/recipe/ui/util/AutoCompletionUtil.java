package seedu.recipe.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import seedu.recipe.logic.parser.CliSyntax;

//@@author hoangduong1607

/**
 * Contains constants and functions needed for auto-completion
 */
public class AutoCompletionUtil {
    public static final ArrayList<String> APPLICATION_COMMANDS = new ArrayList<>(Arrays.asList("add", "clear", "delete",
            "edit", "exit", "find", "group", "help", "history", "list", "redo", "select", "share", "tag", "undo",
            "upload", "view_group"));
    public static final ArrayList<String> APPLICATION_KEYWORDS = new ArrayList<>(Arrays.asList(
            CliSyntax.PREFIX_NAME.toString(), CliSyntax.PREFIX_INGREDIENT.toString(),
            CliSyntax.PREFIX_INSTRUCTION.toString(), CliSyntax.PREFIX_PREPARATION_TIME.toString(),
            CliSyntax.PREFIX_TAG.toString(), CliSyntax.PREFIX_URL.toString(), CliSyntax.PREFIX_INDEX.toString(),
            CliSyntax.PREFIX_GROUP_NAME.toString()));
    public static final int MAX_SUGGESTIONS = 8;
    public static final char LF = '\n';
    public static final char WHITESPACE = ' ';
    public static final char END_FIELD = '/';

    private HashMap<String, ArrayList<String>> prefixesForCommand;

    public AutoCompletionUtil() {
        prefixesForCommand = new HashMap<>();

        ArrayList<String> addPrefixes = new ArrayList<>(Arrays.asList(CliSyntax.PREFIX_NAME.toString(),
                CliSyntax.PREFIX_INGREDIENT.toString(), CliSyntax.PREFIX_INSTRUCTION.toString(),
                CliSyntax.PREFIX_PREPARATION_TIME.toString(), CliSyntax.PREFIX_TAG.toString(),
                CliSyntax.PREFIX_URL.toString()));
        prefixesForCommand.put("add", addPrefixes);

        ArrayList<String> editPrefixes = new ArrayList<>(Arrays.asList(CliSyntax.PREFIX_NAME.toString(),
                CliSyntax.PREFIX_INGREDIENT.toString(), CliSyntax.PREFIX_INSTRUCTION.toString(),
                CliSyntax.PREFIX_PREPARATION_TIME.toString(), CliSyntax.PREFIX_TAG.toString(),
                CliSyntax.PREFIX_URL.toString()));
        prefixesForCommand.put("edit", editPrefixes);

        ArrayList<String> groupPrefixes = new ArrayList<>(Arrays.asList(CliSyntax.PREFIX_GROUP_NAME.toString(),
                CliSyntax.PREFIX_INDEX.toString()));
        prefixesForCommand.put("group", groupPrefixes);

        System.out.println(prefixesForCommand.get("add").size());

        for (String command : APPLICATION_COMMANDS) {
            System.out.println(command + " " + prefixesForCommand.containsKey(command));
            if (!prefixesForCommand.containsKey(command)) {
                prefixesForCommand.put(command, new ArrayList<>());
            }
        }

        System.out.println(prefixesForCommand.get("add").size());
    }

    /**
     * Checks whether {@code text} is a command keyword
     */
    public boolean isCommandKeyWord(String text) {
        return prefixesForCommand.containsKey(text);
    }

    /**
     * Generates auto-completed command
     */
    public String getAutoCompletionText(String command) {
        String autoCompletionText = command;

        for (String prefix : prefixesForCommand.get(command)) {
            autoCompletionText = autoCompletionText + WHITESPACE + LF + prefix;
        }

        return autoCompletionText;
    }

    /**
     * Finds position of next field.
     * Returns current position of caret if no field is found
     */
    public int getNextFieldPosition(String inputText, int currentCaretPosition) {
        int nextFieldCaretPosition = currentCaretPosition;

        for (int i = 0; i < inputText.length(); i++) {
            int wrapAroundPosition = (i + currentCaretPosition) % inputText.length();

            if (inputText.charAt(wrapAroundPosition) == END_FIELD) {
                nextFieldCaretPosition = wrapAroundPosition + 1;
                break;
            }
        }

        return nextFieldCaretPosition;
    }
}
