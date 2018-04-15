package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ThemeCommand.DARK_THEME;
import static seedu.address.logic.commands.ThemeCommand.LIGHT_THEME;

import org.junit.Test;

//@@author tiny767
public class ThemeCommandTest {
    @Test
    public void equals() {
        final ThemeCommand darkThemeCommand = new ThemeCommand(DARK_THEME);
        final ThemeCommand lightThemeCommand = new ThemeCommand(LIGHT_THEME);

        // same object -> true
        assertTrue(darkThemeCommand.equals(darkThemeCommand));

        // same value -> true
        ThemeCommand anotherLightThemeCommand = new ThemeCommand(LIGHT_THEME);
        assertTrue(lightThemeCommand.equals(anotherLightThemeCommand));

        // same value -> true
        ThemeCommand anotherDarkThemeCommand = new ThemeCommand(DARK_THEME);
        assertTrue(darkThemeCommand.equals(anotherDarkThemeCommand));

        // different value -> false
        assertFalse(darkThemeCommand.equals(lightThemeCommand));

        // different type -> false
        assertFalse(darkThemeCommand.equals(1));

        // null -> false
        assertFalse(darkThemeCommand.equals(null));

    }
}
