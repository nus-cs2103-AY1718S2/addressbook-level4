package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.StatusBarFooter.RECORD_NUMBER_STATUS;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.MainApp;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Imdb;
import seedu.address.model.Model;
import seedu.address.testutil.TypicalPatients;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.CommandBox;
import seedu.address.ui.ResultDisplay;

/**
 * A system test class for Imdb, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class ImdbSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private List<String> resultDisplayDefaultStyle;
    private List<String> resultDisplayErrorStyle;

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

        //@@author Kyholmes-reused
        //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
        resultDisplayDefaultStyle = mainWindowHandle.getResultDisplay().getStyleClass();
        resultDisplayErrorStyle = mainWindowHandle.getResultDisplay().getStyleClass();
        resultDisplayErrorStyle.add(ResultDisplay.ERROR_STYLE_CLASS);

        waitUntilBrowserLoaded(getBrowserPanel());
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
    protected Imdb getInitialData() {
        return TypicalPatients.getTypicalAddressBook();
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

    public PatientListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
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

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all persons in the address book.
     */
    protected void showAllPersons() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getImdb().getPersonList().size(), getModel().getFilteredPersonList().size());
    }

    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredPersonList().size() < getModel().getImdb().getPersonList().size());
    }

    /**
     * Selects the patient at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }
    /**
     * Deletes all persons in the address book.
     */
    protected void deleteAllPersons() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getImdb().getPersonList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the model and storage contains the same patient objects as {@code expectedModel}
     * and the patient list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedModel, getModel());
        assertEquals(expectedModel.getImdb(), testApp.readStorageAddressBook());
        assertListMatching(getPersonListPanel(), expectedModel.getFilteredPersonList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PatientListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        statusBarFooterHandle.rememberRecordNumber();
        getPersonListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected patient.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getPersonListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the patient in the patient list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PatientListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        String selectedCardName = getPersonListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.");
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the patient list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PatientListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the command box and result display shows the default style.
     */
    protected void assertCommandBoxAndResultDisplayShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
        assertEquals(resultDisplayDefaultStyle, getResultDisplay().getStyleClass());
    }

    /**
     * Asserts that the command box and result display shows the error style.
     */
    protected void assertCommandBoxAndResultDisplayShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
        assertEquals(resultDisplayErrorStyle, getResultDisplay().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
        assertFalse(handle.isRecordNumberChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location and record number remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isRecordNumberChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        try {
            assertEquals("", getCommandBox().getInput());
            assertEquals("", getResultDisplay().getText());
            assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
            assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
            assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
            assertEquals(String.format(RECORD_NUMBER_STATUS, getModel().getImdb().getPersonList().size()),
                    getStatusBarFooter().getRecordNumber());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }

    //@@author Kyholmes-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
    /**
     * Asserts that the timing of sync status was changed and record number was changed to match total number of
     * records in the address book
     */
    protected void assertStatusBarChangedExceptSaveLocation() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();

        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());

        final int totalRecords = testApp.getModel().getImdb().getPersonList().size();
        assertEquals(String.format(RECORD_NUMBER_STATUS, totalRecords), statusBarFooterHandle.getRecordNumber());

        assertFalse(statusBarFooterHandle.isSaveLocationChanged());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
