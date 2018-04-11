package seedu.progresschecker.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.progresschecker.testutil.TypicalThemes.DAY_THEME;
import static seedu.progresschecker.testutil.TypicalThemes.NIGHT_THEME;

import org.junit.Test;

//@@author Livian1107
/**
 * Contains assertion tests for {@code ThemeCommand}.
 */
public class ThemeCommandTest {
    @Test
    public void equals() {
        ThemeCommand dayTheme = new ThemeCommand(DAY_THEME);
        ThemeCommand nightTheme = new ThemeCommand(NIGHT_THEME);

        // same object -> returns true
        assertTrue(dayTheme.equals(dayTheme));

        // same values -> returns true
        ThemeCommand dayThemeCopy = new ThemeCommand(DAY_THEME);
        assertTrue(dayTheme.equals(dayThemeCopy));

        // different types -> returns false
        assertFalse(dayTheme.equals(1));

        // null -> returns false
        assertFalse(dayTheme.equals(null));

        // different type -> returns false
        assertFalse(dayTheme.equals(nightTheme));
    }
}
