package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.NotificationsWindowHandle;
import seedu.address.logic.commands.ListNotifsCommand;
import seedu.address.logic.commands.SyncCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.ui.BrowserPanel;

/**
 * A system test class for the notification system, which contains interaction with other UI components.
 */
public class NotifyCommandSystemTest extends CoinBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openNotificationWindow() {
        assertNotificationWindowNotOpen();

        //use command box
        executeCommand(ListNotifsCommand.COMMAND_WORD);
        assertNotificationWindowOpen();

        executeCommand(SyncCommand.COMMAND_WORD);

        // open notification window and give it focus
        executeCommand(ListNotifsCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        // assert that while the notification window is open the UI updates correctly for a command execution
        executeCommand(ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_COIN.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertNotEquals(ListNotifsCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
        assertNotEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertListMatching(getCoinListPanel(), getModel().getFilteredCoinList());
    }

    /**
     * Asserts that the notification window is open, and closes it after checking.
     */
    private void assertNotificationWindowOpen() {
        assertTrue(ERROR_MESSAGE, NotificationsWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new NotificationsWindowHandle(guiRobot.getStage(NotificationsWindowHandle.NOTIFICATIONS_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the notification window isn't open.
     */
    private void assertNotificationWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, NotificationsWindowHandle.isWindowPresent());
    }

}
