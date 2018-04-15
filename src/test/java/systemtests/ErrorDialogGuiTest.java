//@@author ifalluphill-reused
//{Based on HelpCommandSystemTest}

package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.CalendarWindowHandle;
import guitests.guihandles.ErrorLogHandle;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ErrorLogCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.StatusBarFooter;


/**
 * A system test class for the calendar window, which contains interaction with other UI components.
 */
public class ErrorDialogGuiTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void showErrorDialogs() {
        //use menu button
        getMainMenu().openErrorLogUsingMenu();
        assertErrorLogOpen();

        //use command box
        executeCommand(ErrorLogCommand.COMMAND_WORD);
        assertErrorLogOpen();

        // open error log and give it focus
        executeCommand(ErrorLogCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        // assert that while the error log is open the UI updates correctly for a command execution
        executeCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertNotEquals(ErrorLogCommand.MESSAGE_SHOWING_ERRORLOG, getResultDisplay().getText());
        assertNotEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());

        // assert that the status bar too is updated correctly while the error log window is open
        // note: the select command tested above does not update the status bar
        executeCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Asserts that the error log is open, and closes it after checking.
     */
    private void assertErrorLogOpen() {
        assertTrue(ERROR_MESSAGE, ErrorLogHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new CalendarWindowHandle(guiRobot.getStage(ErrorLogHandle.ERROR_LOG_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the error log isn't open.
     */
    private void assertErrorLogNotOpen() {
        assertFalse(ERROR_MESSAGE, ErrorLogHandle.isWindowPresent());
    }

}

//@@author
