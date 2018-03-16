package seedu.address.model.theme;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME_PINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_PATH_DARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_PATH_LIGHT;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ThemeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Theme(null));
    }

    @Test
    public void constructor_invalidThemeName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Theme(INVALID_THEME_PINK));
    }

    @Test
    public void isValidThemeName() {
        // null theme name
        Assert.assertThrows(NullPointerException.class, () -> Theme.isValidThemeName(null));
    }

    @Test
    public void getThemePath() {

        //Valid themes (case insensitive)
        assertEquals(VALID_THEME_PATH_DARK, new Theme("dark").getThemePath());
        assertEquals(VALID_THEME_PATH_DARK, new Theme("dArK").getThemePath());
        assertEquals(VALID_THEME_PATH_LIGHT, new Theme("light").getThemePath());
        assertEquals(VALID_THEME_PATH_LIGHT, new Theme("liGHT").getThemePath());
    }
}
