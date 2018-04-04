# Ang-YC
###### /java/seedu/address/ui/TitleBarTest.java
``` java
    @Test
    public void controlHelp() {
        titleBarHandle.openHelpWindowUsingControl();
        assertTrue(HelpWindowHandle.isWindowPresent());
    }

    @Test
    public void controlMinimize() {
        titleBarHandle.minimizeWindow();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof MinimizeAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void controlMaximize() {
        titleBarHandle.maximizeWindow();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof MaximizeAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void controlClose() {
        titleBarHandle.closeWindow();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
```
###### /java/seedu/address/ui/InfoPanelTest.java
``` java
public class InfoPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InfoPanel infoPanel;
    private InfoPanelHandle infoPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> infoPanel = new InfoPanel());
        uiPartRule.setUiPart(infoPanel);

        infoPanelHandle = new InfoPanelHandle(infoPanel.getRoot());
    }

    @Test
    public void display() {
        // Default selected person is null
        assertNull(infoPanelHandle.getLoadedPerson());

        // Post changed event
        postNow(selectionChangedEventStub);
        waitUntilInfoPanelLoaded(infoPanelHandle);
        assertEquals(ALICE, infoPanelHandle.getLoadedPerson());
    }

    @Test
    public void responsive() {
        // Select someone first
        postNow(selectionChangedEventStub);
        waitUntilInfoPanelLoaded(infoPanelHandle);
        assertEquals(ALICE, infoPanelHandle.getLoadedPerson());

        // Test responsiveness
        infoPanelHandle.setWidthAndWait(InfoPanel.SPLIT_MIN_WIDTH - 100);
        guiRobot.pauseForHuman();
        assertTrue(infoPanelHandle.isResponsiveSingle());

        infoPanelHandle.setWidthAndWait(InfoPanel.SPLIT_MIN_WIDTH + 100);
        guiRobot.pauseForHuman();
        assertTrue(infoPanelHandle.isResponsiveSplit());
    }
}
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
    /**
     * Forms the image path from image file name
     */
    private static String formImagePathFromFileName(String fileName) {
        if (isNull(fileName)) {
            return null;
        }
        return IMAGE_PATH + fileName;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code ProfileImage} of the {@code Person} that we are building.
     */
    public PersonBuilder withProfileImage(String profileImage) {
        this.profileImage = new ProfileImage(profileImage);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Person} that we are building.
     */
    public PersonBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    /**
     * Sets the {@code InterviewDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = new InterviewDate(interviewDate);
        return this;
    }
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code ProfileImage} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withProfileImage(String profileImage) {
        descriptor.setProfileImage(new ProfileImage(profileImage));
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withComment(String comment) {
        descriptor.setComment(new Comment(comment));
        return this;
    }
```
###### /java/seedu/address/model/person/CommentTest.java
``` java
public class CommentTest {

    @Test
    public void constructor_null_constructionSuccessValueNull() {
        assertNull(new Comment(null).value);
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidComment = "With\nline";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Comment(invalidComment));
    }

    @Test
    public void isValidComment() {
        Assert.assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        // All values are accepted
        assertTrue(Comment.isValidComment("")); // Empty string
        assertTrue(Comment.isValidComment(" ")); // Whitespace only
        assertTrue(Comment.isValidComment("He is cool!")); // Normal string

        // Except new line
        assertFalse(Comment.isValidComment("\n")); // Disallow new line
    }
}
```
###### /java/seedu/address/model/person/ProfileImageTest.java
``` java
public class ProfileImageTest {
    private static final String TEST_DATA_FOLDER = "src/test/data/ProfileImageTest/";

    @Test
    public void constructor_null_constructionSuccessValueEmptyString() {
        assertNull(new ProfileImage(null).value);
    }

    @Test
    public void constructor_invalidProfileImage_throwsIllegalArgumentException() {
        String invalidProfileImage = formFilePath("");
        assertThrows(IllegalArgumentException.class, () -> new ProfileImage(invalidProfileImage));
    }

    @Test
    public void constructor_validProfileImage_constructionSuccess() {
        String validProfileImage = formFilePath("gates.jpg");
        assertEquals(validProfileImage, new ProfileImage(validProfileImage).value);
    }

    private String formFilePath(String fileName) {
        return TEST_DATA_FOLDER + fileName;
    }

    @Test
    public void isValidFile() {
        // Null profile image
        assertThrows(NullPointerException.class, () -> ProfileImage.isValidFile(null));

        // Invalid image file name
        assertFalse(ProfileImage.isValidFile(formFilePath(""))); // empty string
        assertFalse(ProfileImage.isValidFile(formFilePath(" "))); // spaces only
        assertFalse(ProfileImage.isValidFile(formFilePath("fileNotFound.jpg"))); // not a existing image
        assertFalse(ProfileImage.isValidFile(formFilePath("largeDora.pdf"))); // greater than 1MB

        // Valid image file name
        assertTrue(ProfileImage.isValidFile(formFilePath("doraemon cute.jpg"))); // spaces within fileName
        assertTrue(ProfileImage.isValidFile(formFilePath("elon.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("gates.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("jobs.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("larry.jpg")));
        assertTrue(ProfileImage.isValidFile(formFilePath("mark.jpg")));

    }

}
```
###### /java/seedu/address/model/person/InterviewDateTest.java
``` java
public class InterviewDateTest {

    private LocalDateTime timeNow = LocalDateTime.now().withNano(0);
    private long timeNowInEpoch = timeNow.toEpochSecond(ZoneOffset.UTC);

    private final InterviewDate validInterviewDate = new InterviewDate(timeNow);
    private final InterviewDate nullInterviewDate = new InterviewDate();

    @Test
    public void setup_sameTimeNow_returnsTrue() {
        LocalDateTime convertedTime = LocalDateTime.ofEpochSecond(timeNowInEpoch, 0, ZoneOffset.UTC);
        assertTrue(convertedTime.equals(timeNow));
    }

    @Test
    public void constructor_sameInterviewDate_returnsTrue() {
        InterviewDate validInterviewDateInEpoch = new InterviewDate(timeNowInEpoch);
        assertTrue(validInterviewDate.equals(validInterviewDateInEpoch));
    }

    @Test
    public void constructor_unscheduledInterviewDate_returnsTrue() {
        assertNotNull(nullInterviewDate);
        assertNull(nullInterviewDate.getDateTime());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(validInterviewDate.equals(validInterviewDate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        InterviewDate validInterviewDateCopy = new InterviewDate(validInterviewDate.getDateTime());
        assertTrue(validInterviewDate.equals(validInterviewDateCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(validInterviewDate.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(validInterviewDate.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        assertFalse(validInterviewDate.equals(nullInterviewDate));
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidInterviewDate_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_UNIVERSITY,
                        VALID_EXPECTED_GRADUATION_YEAR, VALID_MAJOR,
                        VALID_GRADE_POINT_AVERAGE, VALID_JOB_APPLIED, VALID_TECHNICAL_SKILLS_SCORE,
                        VALID_COMMUNICATION_SKILLS_SCORE, VALID_PROBLEM_SOLVING_SKILLS_SCORE,
                        VALID_EXPERIENCE_SCORE, VALID_RESUME, VALID_PROFILE_IMAGE, VALID_COMMENT,
                        INVALID_INTERVIEW_DATE, VALID_STATUS, VALID_TAGS);
        String expectedMessage = InterviewDate.MESSAGE_INTERVIEW_DATE_XML_ERROR;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
```
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    @Test
    public void equals_sameObject_returnsTrue() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertTrue(person.equals(person));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        XmlAdaptedPerson alice = new XmlAdaptedPerson(ALICE);
        XmlAdaptedPerson benson = new XmlAdaptedPerson(BENSON);
        assertFalse(alice.equals(benson));
    }
```
###### /java/seedu/address/logic/commands/InterviewCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for InterviewCommand.
 */
public class InterviewCommandTest {

    public static final LocalDateTime VALID_DATETIME =
            LocalDateTime.ofEpochSecond(1540814400, 0, ZoneOffset.UTC);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validDateUnfilteredList_success() throws Exception {
        Person firstPerson = model.getAddressBook().getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(firstPerson).withInterviewDate(VALID_DATETIME).build();

        InterviewCommand interviewCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        String expectedMessage = String.format(InterviewCommand.MESSAGE_INTERVIEW_PERSON_SUCCESS,
                scheduledPerson.getName(), scheduledPerson.getInterviewDate().getDateTime().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, scheduledPerson);

        assertCommandSuccess(interviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InterviewCommand interviewCommand = prepareCommand(outOfBoundIndex, VALID_DATETIME);
        assertCommandFailure(interviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        InterviewCommand interviewCommand = prepareCommand(outOfBoundIndex, VALID_DATETIME);
        assertCommandFailure(interviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToInterview = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(personToInterview).withInterviewDate(VALID_DATETIME).build();
        InterviewCommand interviewCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // interview -> first person interview date scheduled
        interviewCommand.execute();
        undoRedoStack.push(interviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person scheduled again
        expectedModel.updatePerson(personToInterview, scheduledPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        InterviewCommand interviewCommand = prepareCommand(outOfBoundIndex, VALID_DATETIME);

        // execution failed -> interviewCommand not pushed into undoRedoStack
        assertCommandFailure(interviewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonScheduled() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        InterviewCommand interviewCommand = prepareCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToInterview = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person scheduledPerson = new PersonBuilder(personToInterview).withInterviewDate(VALID_DATETIME).build();
        // interview -> schedule interview for second person in unfiltered person list /
        //              first person in filtered person list
        interviewCommand.execute();
        undoRedoStack.push(interviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToInterview, scheduledPerson);
        assertNotEquals(personToInterview, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        // redo -> same second person scheduled again
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final InterviewCommand standardCommand = new InterviewCommand(INDEX_FIRST_PERSON, VALID_DATETIME);

        // same values -> returns true
        InterviewCommand commandWithSameValues = new InterviewCommand(INDEX_FIRST_PERSON, VALID_DATETIME);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new InterviewCommand(INDEX_SECOND_PERSON, VALID_DATETIME)));

        // different date time -> returns false
        assertFalse(standardCommand.equals(new InterviewCommand(INDEX_FIRST_PERSON, LocalDateTime.MIN)));
    }

    /**
     * Returns an {@code InterviewCommand}.
     */
    private InterviewCommand prepareCommand(Index index, LocalDateTime dateTime) {
        InterviewCommand interviewCommand = new InterviewCommand(index, dateTime);
        interviewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return interviewCommand;
    }
}
```
###### /java/seedu/address/logic/parser/InterviewCommandParserTest.java
``` java
public class InterviewCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE);
    private static final String VALID_DATE = "14th Mar 2pm";
    private static final String INVALID_DATE = "My Birthday";

    private InterviewCommandParser parser = new InterviewCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // No index specified
        assertParseFailure(parser, VALID_DATE, MESSAGE_INVALID_FORMAT);

        // No date specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // No index and no date specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        assertParseFailure(parser, "1 " + INVALID_DATE,
                String.format(InterviewCommandParser.MESSAGE_DATETIME_PARSE_FAIL, INVALID_DATE));
    }

    @Test
    public void parse_validValue_returnsInterviewCommand() {
        LocalDateTime dateTime = LocalDateTime.of(2018, 3, 14, 14, 0, 0);
        InterviewCommand expectedInterviewCommand =
                new InterviewCommand(Index.fromOneBased(2), dateTime);

        assertParseSuccess(parser, "2 " + VALID_DATE, expectedInterviewCommand);
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_interview() throws Exception {
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(1521036000, 0, ZoneOffset.UTC);

        InterviewCommand command = (InterviewCommand) parser.parseCommand(InterviewCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " Mar 14 2018 2pm");
        assertEquals(new InterviewCommand(INDEX_FIRST_PERSON, dateTime), command);
    }
```
###### /java/systemtests/AddressBookSystemTest.java
``` java
    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommandWaitForUi(String command) {
        getInfoPanel().resetLoadedStatus();
        executeCommand(command);
        waitUntilInfoPanelLoaded(getInfoPanel());
    }
```
###### /java/systemtests/WindowGuiTest.java
``` java
public class WindowGuiTest extends AddressBookSystemTest {

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void testDraggableTitleBar() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Point2D originalPosition = mainWindowHandle.getWindowPosition();
        Point2D expectedDelta = new Point2D(50, 50);
        Point2D dragFrom = mainWindowHandle.getTitleBarPosition();
        Point2D dragTo = dragFrom.add(expectedDelta);

        // Drag it!
        guiRobot.moveTo(dragFrom);
        guiRobot.press(MouseButton.PRIMARY);
        guiRobot.drag(dragTo);
        guiRobot.release(MouseButton.PRIMARY);
        guiRobot.pauseForHuman();

        Point2D newPosition = mainWindowHandle.getWindowPosition();
        Point2D delta = newPosition.subtract(originalPosition);

        assertTrue(delta.equals(expectedDelta));
    }

    @Test
    public void testDoubleClickMaximize() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Point2D clickPosition = mainWindowHandle.getTitleBarPosition();

        // Double click it
        guiRobot.doubleClickOn(clickPosition, MouseButton.PRIMARY);
        guiRobot.pauseForHuman();

        assertWindowMaximized();
    }

    @Test
    public void testUnMaximize() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Rectangle2D originalBound = mainWindowHandle.getWindowBound();

        // Maximize
        EventsUtil.postNow(new MaximizeAppRequestEvent());
        assertWindowMaximized();

        // UnMaximize
        EventsUtil.postNow(new MaximizeAppRequestEvent());

        Rectangle2D newBound = mainWindowHandle.getWindowBound();
        assertTrue(originalBound.equals(newBound));
    }

    @Test
    public void testResizableWindow() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Rectangle2D originalBound = mainWindowHandle.getWindowBound();
        Point2D expectedDelta = new Point2D(50, 50);
        Point2D dragFrom = mainWindowHandle.getResizablePosition();
        Point2D dragTo = dragFrom.add(expectedDelta);

        guiRobot.moveTo(dragFrom);
        guiRobot.press(MouseButton.PRIMARY);
        guiRobot.drag(dragTo);
        guiRobot.release(MouseButton.PRIMARY);
        guiRobot.pauseForHuman();

        Rectangle2D newBound = mainWindowHandle.getWindowBound();
        Point2D delta = new Point2D(newBound.getWidth() - originalBound.getWidth(),
                newBound.getHeight() - originalBound.getHeight());

        assertTrue(delta.equals(expectedDelta));
    }

    @Test
    public void testSplitPaneResponsive() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        double originalWidth = mainWindowHandle.getListPaneWidth();

        // Maximize
        EventsUtil.postNow(new MaximizeAppRequestEvent());
        assertWindowMaximized();
        guiRobot.pauseForHuman();

        // Allow floating point error
        assertTrue(Math.abs(mainWindowHandle.getListPaneWidth() - originalWidth) <= 1);

        // Reset size and position
        mainWindowHandle.setWindowDefaultPositionAndSize();
        guiRobot.pauseForHuman();

        // Allow floating point error
        assertTrue(Math.abs(mainWindowHandle.getListPaneWidth() - originalWidth) <= 1);
    }

    /**
     * Asserts that the window is maximized
     */
    private void assertWindowMaximized() {
        MainWindowHandle mainWindowHandle = getMainWindowHandle();

        Rectangle2D windowBound = mainWindowHandle.getWindowBound();
        Rectangle2D screenBound = mainWindowHandle.getSceenBound();

        assertTrue(windowBound.equals(screenBound));
    }
}
```
###### /java/guitests/guihandles/InfoPanelUtil.java
``` java
/**
 * Helper methods for dealing with {@code InfoPanel}.
 */
public class InfoPanelUtil {
    /**
     * If the {@code infoPanelHandle}'s {@code Panel} is loading, sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilInfoPanelLoaded(InfoPanelHandle infoPanelHandle) {
        new GuiRobot().waitForEvent(infoPanelHandle::isLoaded);
    }
}
```
###### /java/guitests/guihandles/TitleBarHandle.java
``` java
    public TitleBarHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        this.syncStatusNode = getChildNode(SYNC_STATUS_ID);
        this.saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
        this.controlHelp = getChildNode(CONTROL_HELP_ID);
        this.controlMinimize = getChildNode(CONTROL_MINIMIZE_ID);
        this.controlMaximize = getChildNode(CONTROL_MAXIMIZE_ID);
        this.controlClose = getChildNode(CONTROL_CLOSE_ID);
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    /**
     * Opens the {@code HelpWindow} using the button in {@code TitleBar}.
     */
    public void openHelpWindowUsingControl() {
        new GuiRobot().clickOn(controlHelp, MouseButton.PRIMARY);
    }

    /**
     * Minimize the application using the button in {@code TitleBar}.
     */
    public void minimizeWindow() {
        new GuiRobot().clickOn(controlMinimize, MouseButton.PRIMARY);
    }

    /**
     * Maximize the application using the button in {@code TitleBar}.
     */
    public void maximizeWindow() {
        new GuiRobot().clickOn(controlMaximize, MouseButton.PRIMARY);
    }

    /**
     * Close the application using the button in {@code TitleBar}.
     */
    public void closeWindow() {
        new GuiRobot().clickOn(controlClose, MouseButton.PRIMARY);
    }
```
###### /java/guitests/guihandles/InfoPanelHandle.java
``` java
/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class InfoPanelHandle extends NodeHandle<Node> {

    public static final String INFO_ID = "#infoPaneWrapper";

    private static final String SPLIT_PANE_ID = "#infoSplitPane";
    private static final String MAIN_PANE_ID = "#infoMainPane";
    private static final String SPLIT_MAIN_PANE_ID = "#infoSplitMainPane";
    private static final String MAIN_ID = "#infoMain";
    private static final String MAIN_RATINGS_ID = "#infoMainRatings";
    private static final String SPLIT_SIDE_PANE_ID = "#infoSplitSidePane";
    private static final String SPLIT_RATINGS_ID = "#infoSplitRatings";

    private boolean isInfoPanelLoaded = true;
    private boolean isWidthChanged = false;
    private Person lastRememberedPerson;

    private AnchorPane infoPaneWrapper;
    private SplitPane infoSplitPane;
    private ScrollPane infoMainPane;
    private ScrollPane infoSplitMainPane;
    private VBox infoMain;
    private AnchorPane infoMainRatings;
    private AnchorPane infoSplitSidePane;
    private VBox infoSplitRatings;

    public InfoPanelHandle(Node infoPanel) {
        super(infoPanel);

        this.infoPaneWrapper   = getChildNode(INFO_ID);
        this.infoSplitPane     = getChildNode(SPLIT_PANE_ID);
        this.infoMainPane      = getChildNode(MAIN_PANE_ID);
        this.infoSplitMainPane = getChildNode(SPLIT_MAIN_PANE_ID);
        this.infoMain          = getChildNode(MAIN_ID);
        this.infoMainRatings   = getChildNode(MAIN_RATINGS_ID);
        this.infoSplitSidePane = getChildNode(SPLIT_SIDE_PANE_ID);
        this.infoSplitRatings  = getChildNode(SPLIT_RATINGS_ID);

        new GuiRobot().interact(() -> this.infoPaneWrapper.addEventHandler(
            InfoPanelChangedEvent.INFO_PANEL_EVENT,
            event -> isInfoPanelLoaded = true
        ));
    }

    /**
     * Returns the {@code Person} of the currently previewed person.
     */
    public Person getLoadedPerson() {
        return (Person) infoPaneWrapper.getUserData();
    }

    /**
     * Remembers the {@code Person} of the currently previewed person.
     */
    public void rememberPerson() {
        lastRememberedPerson = getLoadedPerson();
    }

    /**
     * Returns true if the current {@code Person} is different from the value remembered by the most recent
     * {@code rememberPerson()} call.
     */
    public boolean isPersonChanged() {
        return !Objects.equals(lastRememberedPerson, getLoadedPerson());
    }

    /**
     * Returns true if the panel is done loading a person, with current person retrievable via {@code getUserData}
     */
    public boolean isLoaded() {
        return isInfoPanelLoaded;
    }

    /**
     * Reset the loaded status to wait for next loaded event
     */
    public void resetLoadedStatus() {
        isInfoPanelLoaded = false;
    }

    /**
     * Returns true if the panel is done changing the width
     */
    public boolean isWidthChanged() {
        return isWidthChanged;
    }

    /**
     * Reset the width changed status to wait for next resize event
     */
    public void resetWidthStatus() {
        isWidthChanged = false;
    }

    /**
     * Set the width of info panel and wait until its width changed
     * @param width of the InfoPanel
     */
    public void setWidthAndWait(int width) {
        resetWidthStatus();

        infoPaneWrapper.widthProperty().addListener((observable, oldValue, newValue) -> {
            isWidthChanged = true;
        });

        infoPaneWrapper.getScene().getWindow().setWidth(width);
        new GuiRobot().waitForEvent(this::isWidthChanged);
    }

    /**
     * Returns true if it is split due to responsive, false otherwise
     */
    public boolean isResponsiveSplit() {
        return infoSplitPane.isVisible()
                && !infoMainPane.isVisible()
                && !infoMainRatings.getChildren().contains(infoSplitRatings)
                && infoSplitSidePane.getChildren().contains(infoSplitRatings)
                && infoMainPane.getContent() == null
                && infoSplitMainPane.getContent().equals(infoMain);
    }

    /**
     * Returns true if it is single scroll pane due to responsive, false otherwise
     */
    public boolean isResponsiveSingle() {
        return !infoSplitPane.isVisible()
                && infoMainPane.isVisible()
                && infoMainRatings.getChildren().contains(infoSplitRatings)
                && !infoSplitSidePane.getChildren().contains(infoSplitRatings)
                && infoMainPane.getContent().equals(infoMain)
                && infoSplitMainPane.getContent() == null;
    }
}
```
###### /java/guitests/guihandles/MainWindowHandle.java
``` java
    public Point2D getTitleBarPosition() {
        Bounds bounds = topPane.localToScreen(topPane.getBoundsInLocal());
        return new Point2D((bounds.getMinX() + bounds.getMaxX()) / 2, (
                bounds.getMinY() + bounds.getMaxY()) / 2 - bounds.getHeight() / 4);
    }

    public Point2D getResizablePosition() {
        return new Point2D(stage.getWidth() + stage.getX() - MainWindow.WINDOW_CORNER_SIZE + 1,
                stage.getHeight() + stage.getY() - MainWindow.WINDOW_CORNER_SIZE + 1);
    }

    public Point2D getWindowPosition() {
        return new Point2D(stage.getX(), stage.getY());
    }

    public Rectangle2D getWindowBound() {
        return new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
    }

    public void setWindowDefaultPositionAndSize() {
        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
        stage.setWidth(MainWindow.MIN_WINDOW_WIDTH);
        stage.setHeight(MainWindow.MIN_WINDOW_HEIGHT);
        stage.setX(screenBound.getMinX());
        stage.setY(screenBound.getMinY());
    }

    public double getListPaneWidth() {
        return bottomListPane.getWidth();
    }

    public Rectangle2D getSceenBound() {
        return Screen.getPrimary().getVisualBounds();
    }
}
```
