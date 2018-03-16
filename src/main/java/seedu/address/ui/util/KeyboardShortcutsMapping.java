package seedu.address.ui.util;

import javafx.scene.input.KeyCombination;

/**
 * Contains all mappings for keyboard shortcuts
 */
public class KeyboardShortcutsMapping {
    public static final KeyCombination COMMAND_SUBMISSION = KeyCombination.valueOf("Enter");
    public static final KeyCombination LAST_COMMAND = KeyCombination.valueOf("Up");
    public static final KeyCombination NEXT_COMMAND = KeyCombination.valueOf("Down");
    public static final KeyCombination NEW_LINE_IN_COMMAND = KeyCombination.valueOf("Shift+Enter");
    public static final KeyCombination SHOW_SUGGESTIONS_COMMAND = KeyCombination.valueOf("Ctrl+Space");
}
