//@@author kokonguyen191
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.recipe.TestApp.APP_TITLE;
import static seedu.recipe.ui.MainWindow.DARK_THEME_CSS;
import static seedu.recipe.ui.MainWindow.LIGHT_THEME_CSS;
import static seedu.recipe.ui.UiPart.FXML_FILE_FOLDER;

import org.junit.Test;

import guitests.GuiRobot;
import seedu.recipe.MainApp;
import seedu.recipe.logic.commands.ChangeThemeCommand;

public class ChangeThemeSystemTest extends RecipeBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void changeTheme() {

        // Use accelerator
        getCommandBox().click();
        getMainMenu().changeThemeUsingAccelerator();
        assertDarkTheme();

        getResultDisplay().click();
        getMainMenu().changeThemeUsingAccelerator();
        assertLightTheme();

        getResultDisplay().click();
        getMainMenu().changeThemeUsingAccelerator();
        assertDarkTheme();

        getCommandBox().click();
        getMainMenu().changeThemeUsingAccelerator();
        assertLightTheme();

        // Use menu button
        getMainMenu().changeThemeUsingMenu();
        assertDarkTheme();

        getMainMenu().changeThemeUsingMenu();
        assertLightTheme();

        // Change theme with command
        executeCommand(ChangeThemeCommand.COMMAND_WORD);
        assertDarkTheme();

        executeCommand(ChangeThemeCommand.COMMAND_WORD);
        assertLightTheme();
    }

    /**
     * Asserts that the current theme color is dark
     */
    private void assertDarkTheme() {
        assertTrue(ERROR_MESSAGE, guiRobot.getStage(APP_TITLE).getScene().getStylesheets().get(0)
                .equals(MainApp.class.getResource(FXML_FILE_FOLDER + DARK_THEME_CSS).toExternalForm()));
        guiRobot.pauseForHuman();
    }

    /**
     * Asserts that the current theme color is light
     */
    private void assertLightTheme() {
        assertTrue(ERROR_MESSAGE, guiRobot.getStage(APP_TITLE).getScene().getStylesheets().get(0)
                .equals(MainApp.class.getResource(FXML_FILE_FOLDER + LIGHT_THEME_CSS).toExternalForm()));
        guiRobot.pauseForHuman();
    }
}
