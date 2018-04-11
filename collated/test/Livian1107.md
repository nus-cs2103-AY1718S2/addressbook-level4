# Livian1107
###### \java\seedu\progresschecker\logic\commands\ThemeCommandTest.java
``` java
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
```
###### \java\seedu\progresschecker\logic\commands\UploadCommandTest.java
``` java
public class UploadCommandTest {
    @Test
    public void isValidLocalPath() {

        // valid photo path
        assertTrue(UploadCommand.isValidLocalPath("C:\\Users\\Livian\\desktop\\1.png"));

        // empty path
        assertFalse(UploadCommand.isValidLocalPath("")); // empty string
        assertFalse(UploadCommand.isValidLocalPath(" ")); // spaces only

        // invalid extension
        assertFalse(UploadCommand.isValidLocalPath("C:\\photo.gif"));
        assertFalse(UploadCommand.isValidLocalPath("D:\\photo.bmp"));

        // invalid path format
        assertFalse(UploadCommand.isValidLocalPath("C:\\\\1.jpg")); // too many backslashes
        assertFalse(UploadCommand.isValidLocalPath("C:\\")); // no file name
    }
}
```
###### \java\seedu\progresschecker\testutil\TypicalThemes.java
``` java
/**
 * A utility class containing a list of {@code String} objects to be used in tests.
 */
public class TypicalThemes {
    public static final String DAY_THEME = "day";
    public static final String NIGHT_THEME = "night";
}
```
