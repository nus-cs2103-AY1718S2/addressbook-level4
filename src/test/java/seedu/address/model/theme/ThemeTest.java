package seedu.address.model.theme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ThemeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Theme(null));
    }

    @Test
    public void constructor_invalidTheme_throwsIllegalArgumentException() {
        String invalidTheme = "39dhks";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Theme(invalidTheme));
    }

    @Test
    public void equals() {
        Theme firstTheme = new Theme("dark");

        // same theme object -> true
        assertTrue(firstTheme.equals(firstTheme));

        // same theme value -> true
        Theme firstThemeCopy = new Theme("dark");
        assertTrue(firstTheme.equals(firstThemeCopy));

        // different type -> false
        assertFalse(firstTheme.equals(1));

        // null -> false
        assertFalse(firstTheme.equals(null));
    }

    @Test
    public void isValidTheme() {
        // null theme
        Assert.assertThrows(NullPointerException.class, () -> Theme.isValidTheme(null));

        // invalid theme
        assertFalse(Theme.isValidTheme("")); // empty string
        assertFalse(Theme.isValidTheme(" ")); // spaces only
        assertFalse(Theme.isValidTheme("djwe398")); // invalid keywords for theme

        // valid theme
        assertTrue(Theme.isValidTheme("dark")); // dark theme
        assertTrue(Theme.isValidTheme("light")); // light theme
    }

    @Test
    public void setTheme_switchBetweenThemes() {
        // change to 'dark' -> changed successfully
        String theme = "dark";
        Theme.setCurrentTheme(theme);
        assertEquals(Theme.getCurrentTheme(), theme);

        // change to 'light' -> changed successfully
        theme = "light";
        Theme.setCurrentTheme(theme);
        assertEquals(Theme.getCurrentTheme(), theme);

        // change to 'day' (doesn't exist) -> not changed from previous theme
        theme = "day";
        Theme.setCurrentTheme(theme);
        assertFalse(Theme.getCurrentTheme().equals(theme));
        assertEquals(Theme.getCurrentTheme(), "light");
    }
}
