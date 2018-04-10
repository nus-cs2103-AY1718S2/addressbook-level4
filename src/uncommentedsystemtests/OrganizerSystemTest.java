package systemtests;

//import static guitests.guihandles.WebViewUtil.waitUntilCalendarLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.organizer.ui.CalendarPanel.DEFAULT_PAGE;
//import static seedu.organizer.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.ui.StatusBarFooter.CURRENT_USER_STATUS_UPDATED;
import static seedu.organizer.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
//import static seedu.organizer.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.organizer.ui.testutil.GuiTestAssert.assertListMatching;

//import java.net.MalformedURLException;
//import java.net.URL;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CalendarPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import guitests.guihandles.TaskListPanelHandle;
//import seedu.organizer.MainApp;
import seedu.organizer.TestApp;
import seedu.organizer.commons.core.EventsCenter;
import seedu.organizer.logic.commands.ClearCommand;
import seedu.organizer.logic.commands.FindNameCommand;
import seedu.organizer.logic.commands.ListCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.Organizer;
import seedu.organizer.testutil.TypicalTasks;
//import seedu.organizer.ui.CalendarPanel;
import seedu.organizer.ui.CommandBox;

/**
 * A system test class for Organizer, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class OrganizerSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();
        testApp.loginAdmin();

        assertApplicationStartingStateIsCorrect();

    }

    @After
    public void tearDown() throws Exception {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected Organizer getInitialData() {
        return TypicalTasks.getTypicalOrganizer();
    }

    /**
     * Returns the directory of the data file.
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public TaskListPanelHandle getTaskListPanel() {
        return mainWindowHandle.getTaskListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public CalendarPanelHandle getCalendarPanel() {
        return mainWindowHandle.getCalendarPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Displays all tasks in the organizer.
     */
    protected void showAllTasks() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getOrganizer().getTaskList().size(), getModel().getFilteredTaskList().size());
    }

    /**
     * Displays all tasks with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showTasksWithName(String keyword) {
        executeCommand(FindNameCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredTaskList().size() < getModel().getOrganizer().getTaskList().size());
    }

    /**
     * Deletes all tasks in the organizer.
     */
    protected void deleteAllTasks() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getOrganizer().getTaskList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the model and storage contains the same task objects as {@code expectedModel}
     * and the task list panel displays the tasks in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedModel, getModel());
        assertEquals(expectedModel.getOrganizer(), testApp.readStorageOrganizer());
        assertListMatching(getTaskListPanel(), expectedModel.getFilteredTaskList());
    }

    /**
     * Calls {@code TaskListPanelHandle} and {@code StatusBarFooterHandle} to remember their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberCurrentUserStatus();
        statusBarFooterHandle.rememberSyncStatus();
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isCurrentUserStatusChanged());
        //Does not apply as login occurs first
        //assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location and the total task
     + list remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        //Does not apply due to login
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isCurrentUserStatusChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        try {
            assertEquals("", getCommandBox().getInput());
            assertEquals("", getResultDisplay().getText());
            assertListMatching(getTaskListPanel(), getModel().getFilteredTaskList());
            //assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getCalendarPanel().getLoadedUrl
            //        ());
            assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());
            //Does not apply as login occurs first
            //assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
            assertEquals(String.format(CURRENT_USER_STATUS_UPDATED, ADMIN_USER.username),
                getStatusBarFooter().getCurrentUserStatus());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }

    /**
     * Asserts that the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, and total tasks was changed to match the total
     * number of tasks in the organizer, while the save location remains the same.
     */
    protected void assertStatusBarChangedExceptSaveLocation() {
        StatusBarFooterHandle handle = getStatusBarFooter();

        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());

        final String currentUsername = ADMIN_USER.username;
        assertEquals(String.format(CURRENT_USER_STATUS_UPDATED, currentUsername), handle.getCurrentUserStatus());

        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
