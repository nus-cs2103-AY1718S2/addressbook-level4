package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;

/** @@author Codee */
public class ChangeThemeCommandTest {

    private String[] listThemes = { "Light", "Dark" };

    @Test
    public void execute_validTheme_success() {
        assertExecutionSuccess(listThemes[0]);
    }

    @Test
    public void execute_invalidTheme_failure() {
        assertExecutionFailure("FakeTheme", Messages.MESSAGE_INVALID_THEME);
    }

    @Test
    public void equals() {
        ChangeThemeCommand[] listThemeCommands = new ChangeThemeCommand[2];
        for (int i = 0; i < 2; i++) {
            listThemeCommands[i] = new ChangeThemeCommand(listThemes[i]);
        }

        // same object -> returns true
        for (int i = 0; i < 2; i++) {
            assertTrue(listThemeCommands[i].equals(new ChangeThemeCommand(listThemes[i])));
        }

        // different types -> returns false
        for (int i = 0; i < 2; i++) {
            assertFalse(listThemeCommands[i].equals(1));
        }

        // null -> returns false
        for (int i = 0; i < 2; i++) {
            assertFalse(listThemeCommands[i].equals(null));
        }

        // different theme -> returns false
        int j = 1;

        for (int i = 0; i < 2; i++) {
            if (i != j) {
                assertFalse(listThemeCommands[i].equals(listThemeCommands[j]));
            }
            j--;
        }
    }

    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code theme}.
     */
    private void assertExecutionSuccess(String theme) {
        ChangeThemeCommand changethemeCommand = new ChangeThemeCommand(theme);
        try {
            CommandResult commandResult = changethemeCommand.execute();
            assertEquals(String.format(ChangeThemeCommand.MESSAGE_THEME_SUCCESS, theme),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code theme}.
     */
    private void assertExecutionFailure(String theme, String expectedMessage) {
        ChangeThemeCommand changethemeCommand = new ChangeThemeCommand(theme);

        try {
            changethemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }
}
