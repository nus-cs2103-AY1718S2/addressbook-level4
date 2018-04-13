//@@author amad-person
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ChangeThemeCommand.MESSAGE_INVALID_THEME;
import static seedu.address.logic.commands.ChangeThemeCommand.MESSAGE_THEME_CHANGED_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.theme.Theme.DARK_THEME_KEYWORD;
import static seedu.address.model.theme.Theme.LIGHT_THEME_KEYWORD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ChangeThemeCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UserPrefs userPrefs;
    private GuiSettings guiSettings;
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());
        userPrefs = new UserPrefs();
        guiSettings = userPrefs.getGuiSettings();
    }

    @Test
    public void constructor_nullTheme_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeThemeCommand(null);
    }

    @Test
    public void execute_themeAccepted_guiSettingsChanged() {
        ChangeThemeCommand changeThemeToLightCommand = prepareCommand(LIGHT_THEME_KEYWORD);

        String expectedMessage = String.format(MESSAGE_THEME_CHANGED_SUCCESS, LIGHT_THEME_KEYWORD);

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                LIGHT_THEME_KEYWORD
        );

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new CalendarManager(), expectedUserPrefs);
        assertCommandSuccess(changeThemeToLightCommand, model, expectedMessage, expectedModel);

        ChangeThemeCommand changeThemeToDarkCommand = prepareCommand(DARK_THEME_KEYWORD);

        expectedMessage = String.format(MESSAGE_THEME_CHANGED_SUCCESS, DARK_THEME_KEYWORD);

        expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                DARK_THEME_KEYWORD
        );

        expectedModel = new ModelManager(getTypicalAddressBook(), new CalendarManager(), expectedUserPrefs);
        assertCommandSuccess(changeThemeToDarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTheme_throwsCommandException() {
        ChangeThemeCommand invalidChangeThemeCommand = prepareCommand(INVALID_THEME);

        String expectedMessage = String.format(MESSAGE_INVALID_THEME, INVALID_THEME);

        assertCommandFailure(invalidChangeThemeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeThemeCommand darkThemeCommand = new ChangeThemeCommand(DARK_THEME_KEYWORD);
        ChangeThemeCommand lightThemeCommand = new ChangeThemeCommand(LIGHT_THEME_KEYWORD);

        // same object -> true
        assertTrue(darkThemeCommand.equals(darkThemeCommand));

        // same value -> true
        ChangeThemeCommand darkThemeCommandCopy = new ChangeThemeCommand(DARK_THEME_KEYWORD);
        assertTrue(darkThemeCommand.equals(darkThemeCommandCopy));

        // different value -> false
        assertFalse(darkThemeCommand.equals(lightThemeCommand));

        // different type -> false
        assertFalse(darkThemeCommand.equals(1));

        // null -> false
        assertFalse(darkThemeCommand.equals(null));

    }

    private ChangeThemeCommand prepareCommand(String theme) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(theme);
        changeThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeThemeCommand;
    }
}
