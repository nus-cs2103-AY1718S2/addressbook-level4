# ifalluphill-reused
###### /java/systemtests/CalendarCommandSystemTest.java
``` java
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
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for the calendar window, which contains interaction with other UI components.
 */
public class CalendarCommandSystemTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openCalendarWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openCalendarWindowUsingAccelerator();
        assertCalendarWindowOpen();

        getResultDisplay().click();
        getMainMenu().openCalendarWindowUsingAccelerator();
        assertCalendarWindowOpen();

        getPersonListPanel().click();
        getMainMenu().openCalendarWindowUsingAccelerator();
        assertCalendarWindowOpen();

        //use menu button
        getMainMenu().openCalendarWindowUsingMenu();
        assertCalendarWindowOpen();

        //use command box
        executeCommand(CalendarCommand.COMMAND_WORD);
        assertCalendarWindowOpen();

        // open calendar window and give it focus
        executeCommand(CalendarCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        // assert that while the calendar window is open the UI updates correctly for a command execution
        executeCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertNotEquals(CalendarCommand.MESSAGE_SHOWING_CALENDAR, getResultDisplay().getText());
        assertNotEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());

        // assert that the status bar too is updated correctly while the calendar window is open
        // note: the select command tested above does not update the status bar
        executeCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Asserts that the calendar window is open, and closes it after checking.
     */
    private void assertCalendarWindowOpen() {
        assertTrue(ERROR_MESSAGE, CalendarWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new CalendarWindowHandle(guiRobot.getStage(CalendarWindowHandle.CALENDAR_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the calendar window isn't open.
     */
    private void assertCalendarWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, CalendarWindowHandle.isWindowPresent());
    }

}

```
###### /java/systemtests/ErrorDialogGuiTest.java
``` java
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

```
###### /java/seedu/address/logic/commands/CalendarCommandTest.java
``` java
//{Based on HelpCommandTest}
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CalendarCommand.MESSAGE_SHOWING_CALENDAR;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowCalendarRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class CalendarCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new CalendarCommand().execute();
        assertEquals(MESSAGE_SHOWING_CALENDAR, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowCalendarRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}

```
###### /java/guitests/guihandles/CalendarWindowHandle.java
``` java
//{Based on HelpWindowHandle}

package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code Calendar} of the application.
 */
public class CalendarWindowHandle extends StageHandle {

    public static final String CALENDAR_WINDOW_TITLE = "Calendar";

    private static final String CALENDAR_WINDOW_BROWSER_ID = "#browser";

    public CalendarWindowHandle(Stage calendarWindowStage) {
        super(calendarWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(CALENDAR_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(CALENDAR_WINDOW_BROWSER_ID));
    }
}

```
###### /java/guitests/guihandles/ErrorLogHandle.java
``` java
//{Based on HelpWindowHandle}

package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code ErrorLog} of the application.
 */
public class ErrorLogHandle extends StageHandle {

    public static final String ERROR_LOG_TITLE = "Error Log";

    private static final String ERROR_LOG_BROWSER_ID = "#browser";

    public ErrorLogHandle(Stage errorLogStage) {
        super(errorLogStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(ERROR_LOG_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(ERROR_LOG_BROWSER_ID));
    }
}

```
