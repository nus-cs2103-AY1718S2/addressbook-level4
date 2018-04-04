# yamgent-reused
###### \src\main\java\seedu\address\ui\MainWindow.java
``` java
        //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getPatientVisitingQueue().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PatientListPanel getPatientListPanel() {
        return this.patientListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
    @Subscribe
    private void handleShowPatientAppointment(ShowPatientAppointmentRequestEvent event) throws ParseException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleShowPatientAppointment(event.data.getPastAppointmentList(),
                event.data.getUpcomingAppointmentList());
    }

    private void handleShowPatientAppointment(ObservableList<Appointment> pastAppointments,
                                              ObservableList<Appointment> upcomingAppointment) {

        patientAppointmentPanel = new PatientAppointmentPanel(pastAppointments, upcomingAppointment);
        browserPlaceholder.getChildren().add(patientAppointmentPanel.getRoot());
    }

    @Subscribe
    private void handleShowCalendarAppointment(ShowCalendarViewRequestEvent scvre) {
        logger.info(LogsCenter.getEventHandlingLogMessage(scvre));
        calendarPanel = new CalendarPanel(scvre.appointmentEntries);
        browserPlaceholder.getChildren().add(calendarPanel.getRoot());
    }
}
```
###### \src\test\java\guitests\guihandles\PatientCardHandle.java
``` java
    //Reused from https://github.com/se-edu/addressbook-level4/pull/798/files
    public List<String> getTagColors(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
}
```
###### \src\test\java\guitests\guihandles\ResultDisplayHandle.java
``` java
    //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
    /**
     * Returns a list of style classes in the result display
     */
    public List<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
```
###### \src\test\java\seedu\address\ui\ResultDisplayTest.java
``` java
        //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
        messageDefaultStyleClasses = new ArrayList<>(resultDisplayHandle.getStyleClass());
        messageErrorStyleClasses = new ArrayList<>(messageDefaultStyleClasses);
        messageErrorStyleClasses.add(ResultDisplay.ERROR_STYLE_CLASS);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());
        assertEquals(messageDefaultStyleClasses, resultDisplayHandle.getStyleClass());

        //new results received
        assertResultDisplay(NEW_RESULT_EVENT_SUCCESS);
        assertResultDisplay(NEW_RESULT_EVENT_ERROR);

    }

```
###### \src\test\java\seedu\address\ui\ResultDisplayTest.java
``` java
    //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
    /**
     * check if the event message and message text color is same as the expected one
     * @param event
     */
    private void assertResultDisplay(NewResultAvailableEvent event) {
        postNow(event);
        guiRobot.pauseForHuman();

        List<String> expectedStyleClass = event.isError ? messageErrorStyleClasses : messageDefaultStyleClasses;

        assertEquals(event.message, resultDisplayHandle.getText());
        assertEquals(expectedStyleClass, resultDisplayHandle.getStyleClass());
    }
}
```
###### \src\test\java\seedu\address\ui\StatusBarFooterTest.java
``` java
    //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus,
                                        String expectedTotalRecordStatus) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedTotalRecordStatus, statusBarFooterHandle.getRecordNumber());
        guiRobot.pauseForHuman();
    }

}
```
###### \src\test\java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
        //Reused from https://github.com/se-edu/addressbook-level4/pull/798/files
        expectedCard.getTags().forEach(tag ->
            assertEquals(expectedCard.getTagColors(tag), actualCard.getTagColors(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPatient}.
     */
    public static void assertCardDisplaysPerson(Patient expectedPatient, PatientCardHandle actualCard) {
        assertEquals(expectedPatient.getName().fullName, actualCard.getName());
        assertEquals(expectedPatient.getNric().value, actualCard.getNric());
        assertEquals(expectedPatient.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPatient.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPatient.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPatient.getDob().value, actualCard.getDob());
        assertEquals(expectedPatient.getBloodType().value, actualCard.getBloodType());
        assertEquals(expectedPatient.getRemark().value, actualCard.getRemark());
        assertEquals(expectedPatient.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, Patient... patients) {
        for (int i = 0; i < patients.length; i++) {
            assertCardDisplaysPerson(patients[i], patientListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, List<Patient> patients) {
        assertListMatching(patientListPanelHandle, patients.toArray(new Patient[0]));
    }

    /**
     * Asserts the size of the list in {@code patientListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PatientListPanelHandle patientListPanelHandle, int size) {
        int numberOfPeople = patientListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
```
###### \src\test\java\systemtests\ImdbSystemTest.java
``` java
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
        //executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        //assertEquals(index.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
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
        assertListMatching(getPersonListPanel(), expectedModel.getVisitingQueue());
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
            assertListMatching(getPersonListPanel(), getModel().getVisitingQueue());
            assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
            assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
            assertEquals(String.format(RECORD_NUMBER_STATUS, getModel().getImdb().getUniquePatientQueue().size()),
                    getStatusBarFooter().getRecordNumber());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }

```
###### \src\test\java\systemtests\ImdbSystemTest.java
``` java
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

        final int totalRecords = testApp.getModel().getImdb().getUniquePatientQueue().size();
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
```
