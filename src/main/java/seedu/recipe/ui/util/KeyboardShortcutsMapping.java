package seedu.recipe.ui.util;

import javafx.scene.input.KeyCodeCombination;

//@@author {yourGithubUsername}
/**
 * Contains all mappings for keyboard shortcuts
 */
public class KeyboardShortcutsMapping {
    public static final KeyCodeCombination COMMAND_SUBMISSION = (KeyCodeCombination) KeyCodeCombination.valueOf("Enter");
    public static final KeyCodeCombination LAST_COMMAND = (KeyCodeCombination) KeyCodeCombination.valueOf("Up");
    public static final KeyCodeCombination NEXT_COMMAND = (KeyCodeCombination) KeyCodeCombination.valueOf("Down");
    public static final KeyCodeCombination NEW_LINE_IN_COMMAND = (KeyCodeCombination) KeyCodeCombination.valueOf("Shift+Enter");
    public static final KeyCodeCombination SHOW_SUGGESTIONS_COMMAND = (KeyCodeCombination) KeyCodeCombination.valueOf("Ctrl+Space");
}
