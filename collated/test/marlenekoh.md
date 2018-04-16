# marlenekoh
###### /java/seedu/address/logic/commands/ShowCalendarCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ShowCalendarCommand}.
 */
public class ShowCalendarCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model withPartnerModel;
    private Model noPartnerModel;

    @Before
    public void setUp() {
        withPartnerModel = new ModelManager(getTypicalPerson(), getTypicalJournal(), new UserPrefs());
        noPartnerModel = new ModelManager(null, getTypicalJournal(), new UserPrefs());
    }

    @Test
    public void equals() {
        ShowCalendarCommand showCalendarCommand = new ShowCalendarCommand();

        // different types -> returns false
        assertFalse(showCalendarCommand.equals(1));

        // null -> returns false
        assertFalse(showCalendarCommand.equals(null));
    }

    @Test
    public void execute_success() throws IllegalArgumentException {
        ShowCalendarCommand showCalendarCommand = prepareCommand(withPartnerModel);

        try {
            CommandResult commandResult = showCalendarCommand.execute();
            assertEquals(showCalendarCommand.MESSAGE_DESELECT_PERSON_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof HideTimetableRequestEvent);
    }

    @Test
    public void execute_failure() throws IllegalArgumentException {
        ShowCalendarCommand showCalendarCommand = prepareCommand(noPartnerModel);

        try {
            showCalendarCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(showCalendarCommand.MESSAGE_DESELECT_PERSON_FAILURE, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ShowTimetableCommand} with new CommandHistory and new UndoRedoStack.
     */
    private ShowCalendarCommand prepareCommand(Model myModel) {
        ShowCalendarCommand showCalendarCommand = new ShowCalendarCommand();
        showCalendarCommand.setData(myModel, new CommandHistory(), new UndoRedoStack());
        return showCalendarCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ShowTimetableCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ShowTimetableCommand}.
 */
public class ShowTimetableCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model withPartnerModel;
    private Model noPartnerModel;

    @Before
    public void setUp() {
        withPartnerModel = new ModelManager(getTypicalPerson(), getTypicalJournal(), new UserPrefs());
        noPartnerModel = new ModelManager(null, getTypicalJournal(), new UserPrefs());
    }

    @Test
    public void equals() {
        ShowTimetableCommand showTimetableCommand = new ShowTimetableCommand();

        // different types -> returns false
        assertFalse(showTimetableCommand.equals(1));

        // null -> returns false
        assertFalse(showTimetableCommand.equals(null));
    }

    @Test
    public void execute_success() throws IllegalArgumentException {
        ShowTimetableCommand showTimetableCommand = prepareCommand(withPartnerModel);

        try {
            CommandResult commandResult = showTimetableCommand.execute();
            assertEquals(ShowTimetableCommand.MESSAGE_SELECT_PERSON_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTimetableRequestEvent);
    }

    @Test
    public void execute_failure() throws IllegalArgumentException {
        ShowTimetableCommand showTimetableCommand = prepareCommand(noPartnerModel);

        try {
            showTimetableCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(MESSAGE_INVALID_PERSON, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ShowTimetableCommand} with new CommandHistory and new UndoRedoStack.
     */
    private ShowTimetableCommand prepareCommand(Model myModel) {
        ShowTimetableCommand showTimetableCommand = new ShowTimetableCommand();
        showTimetableCommand.setData(myModel, new CommandHistory(), new UndoRedoStack());
        return showTimetableCommand;
    }
}
```
###### /java/seedu/address/logic/commands/CompareTimetableCommandTest.java
``` java
public class CompareTimetableCommandTest {

    private static final String VALID_SHORT_URL = "http://modsn.us/wNuIW";

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model withPartnerModel;
    private Model noPartnerModel;

    @Before
    public void setUp() {
        withPartnerModel = new ModelManager(getTypicalPerson(), getTypicalJournal(), new UserPrefs());
        noPartnerModel = new ModelManager(null, getTypicalJournal(), new UserPrefs());
    }

    @Test
    public void execute_success() throws IllegalArgumentException {
        Timetable timetable = new Timetable(VALID_SHORT_URL);
        CompareTimetableCommand compareTimetableCommand = prepareCommand(withPartnerModel, timetable);

        try {
            CommandResult result = compareTimetableCommand.execute();
            assertEquals(MESSAGE_TIMETABLE_COMPARE_SUCCESS, result.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTimetableRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 2);
    }

    @Test
    public void execute_failure() throws IllegalArgumentException {
        Timetable timetable = new Timetable(VALID_SHORT_URL);
        assertExecutionFailure(noPartnerModel, timetable, MESSAGE_INVALID_PERSON);
        assertExecutionFailure(noPartnerModel, null, MESSAGE_INVALID_PERSON);
        assertExecutionFailure(withPartnerModel, null, MESSAGE_TIMETABLE_COMPARE_FAILURE);
    }

    @Test
    public void equals() {
        Timetable timetable = new Timetable(VALID_SHORT_URL);
        CompareTimetableCommand compareTimetableCommand = new CompareTimetableCommand(timetable);

        // different types -> returns false
        assertFalse(compareTimetableCommand.equals(1));

        // null -> returns false
        assertFalse(compareTimetableCommand.equals(null));
    }

    /**
     * Executes a {@code CompareTimetableCommand} with the given {@code model} and {@code timetable},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Model model, Timetable timetable, String expectedMessage) {
        CompareTimetableCommand compareTimetableCommand = prepareCommand(model, timetable);

        try {
            compareTimetableCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code CompareTimetableCommand} with new CommandHistory and new UndoRedoStack.
     * @param myModel either contains null partner or valid partner
     * @param timetable the other person's timetable to compare with
     */
    private CompareTimetableCommand prepareCommand(Model myModel, Timetable timetable) {
        CompareTimetableCommand compareTimetableCommand = new CompareTimetableCommand(timetable);
        compareTimetableCommand.setData(myModel, new CommandHistory(), new UndoRedoStack());
        return compareTimetableCommand;
    }
}
```
###### /java/seedu/address/storage/FileTimetableStorageTest.java
``` java
public class FileTimetableStorageTest {

    private static final String TEST_PATH = getFilePathInSandboxFolder("testReadingWriting");

    private static final String EXPECTED_TIMETABLE_PAGE_HTML_PATH = "TimetableTest/expectedTimetablePage.html";
    private static final String EXPECTED_TIMETABLE_DISPLAY_INFO_FILE_PATH =
            "TimetableTest/expectedTimetableDisplayInfoView";
    private static final String TIMETABLE_PAGE_HTML_PATH = getFilePathInSandboxFolder("TimetablePage.html");
    private static final String TIMETABLE_PAGE_CSS_PATH = getFilePathInSandboxFolder("TimetableStyle.css");
    private static final String TIMETABLE_DISPLAY_INFO_FILE_PATH =
            getFilePathInSandboxFolder("timetableDisplayInfo");
    private static String expectedTimetableDisplayInfoContents;
    private static String expectedTimetableHtmlContents;
    private FileTimetableStorage fileTimetableStorage;

    @Before
    public void initialize() throws IOException {
        fileTimetableStorage = new FileTimetableStorage(TIMETABLE_PAGE_HTML_PATH, TIMETABLE_PAGE_CSS_PATH,
                TIMETABLE_DISPLAY_INFO_FILE_PATH);
        File testFile = new File(TEST_PATH);
        File file1 = new File(TIMETABLE_PAGE_HTML_PATH);
        File file2 = new File(TIMETABLE_PAGE_CSS_PATH);
        File file3 = new File(TIMETABLE_DISPLAY_INFO_FILE_PATH);

        PrintWriter writer = new PrintWriter(testFile);
        writer.print("hello 123\n");
        writer.close();

        writer = new PrintWriter(file1);
        writer.print("");
        writer.close();

        writer = new PrintWriter(file2);
        writer.print("");
        writer.close();

        writer = new PrintWriter(file3);
        writer.print("");
        writer.close();

        URL timetableDisplayInfoViewUrl = getTestFileUrl(EXPECTED_TIMETABLE_DISPLAY_INFO_FILE_PATH);
        expectedTimetableDisplayInfoContents = Resources.toString(timetableDisplayInfoViewUrl, Charsets.UTF_8);

        URL timetablePageUrl = getTestFileUrl(EXPECTED_TIMETABLE_PAGE_HTML_PATH);
        expectedTimetableHtmlContents = Resources.toString(timetablePageUrl, Charsets.UTF_8);

    }

    @Test
    public void setUpTimetableDisplayFiles() throws IOException {
        fileTimetableStorage.setUpTimetableDisplayFiles(expectedTimetableDisplayInfoContents);
        assertEquals(expectedTimetableHtmlContents,
                FileUtil.readFromFile(new File(TIMETABLE_PAGE_HTML_PATH)));
        assertEquals(expectedTimetableDisplayInfoContents,
                FileUtil.readFromFile(new File(TIMETABLE_DISPLAY_INFO_FILE_PATH)));
        assertEquals(SampleDataUtil.getDefaultTimetablePageCss(),
                FileUtil.readFromFile(new File(TIMETABLE_PAGE_CSS_PATH)));
    }

    @Test
    public void writeToFile() throws IOException {
        String toWrite = "hello 123\n";
        fileTimetableStorage.writeToFile(toWrite, TEST_PATH);
        File testFile = new File(TEST_PATH);
        assertDoesNotThrow(() -> FileUtil.readFromFile(testFile));
        assertEquals(toWrite, FileUtil.readFromFile(testFile));
    }

    @Test
    public void getFileContents() throws FileNotFoundException {
        String expected = "hello 123\n";
        String actual = fileTimetableStorage.getFileContents(TEST_PATH);
        assertDoesNotThrow(() -> fileTimetableStorage.getFileContents(TEST_PATH));
        assertEquals(expected, actual);
    }

    @Test
    public void replaceLineExcludingStartEnd() {
        String contents = "This is a story about the weather.\n"
                + "Today it rained and the ground became muddy.\n"
                + "After the rain stopped, the sun came out.\n";
        String replace = "Yesterday it didn't rain at all and";
        String start = "Today";
        String end = ",";

        String expected = "This is a story about the weather.\n"
                + "Yesterday it didn't rain at all and the sun came out.\n";
        String result = fileTimetableStorage.replaceLineExcludingStartEnd(contents, replace, start, end);
        assertEquals(expected, result);
    }


    /**
     * Returns an url to the test resource
     * @param testFilePath path of the test resource
     */
    private URL getTestFileUrl(String testFilePath) {
        String testFilePathInView = "/view/" + testFilePath;
        URL testFileUrl = MainApp.class.getResource(testFilePathInView);
        assertNotNull(testFilePathInView + " does not exist.", testFileUrl);
        return testFileUrl;
    }
}
```
###### /java/seedu/address/model/person/timetable/TimetableUtilTest.java
``` java
public class TimetableUtilTest {

    private static final String VALID_LONG_URL = "https://nusmods.com/timetable/sem-2/"
            + "share?CS2101=SEC:C01&CS2103T=TUT:C01&CS3230=LEC:1,TUT:4&"
            + "CS3241=LAB:3,LEC:1,TUT:3&CS3247=LAB:1,LEC:1&GES1021=LEC:SL1";
    private static final String VALID_SHORT_URL = "http://modsn.us/wNuIW";
    private static final String INVALID_SHORT_URL = "http://modsn.us/123";
    private static final String TIMETABLE_DISPLAY_INFO_VIEW_FILE_PATH =
            "TimetableTest/expectedTimetableDisplayInfoView";
    private static final String TIMETABLE_DISPLAY_INFO_COMPARE_FILE_PATH =
            "TimetableTest/expectedTimetableDisplayInfoCompare";
    private static final int CURRENT_SEMESTER = 2;
    private static String expectedTimetableDisplayInfoView;
    private static String expectedTimetableDisplayInfoCompare;

    private static HashMap<String, TimetableModule> expectedListOfModules;

    @Before
    public void initialize() throws IOException {
        // set up expectedListOfModules
        expectedListOfModules = new HashMap<String, TimetableModule>();
        HashMap<String, String> tempLessonPair;

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Sectional Teaching", "C01");
        expectedListOfModules.put("CS2101", new TimetableModule("CS2101",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Tutorial", "C01");
        expectedListOfModules.put("CS2103T", new TimetableModule("CS2103T",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Lecture", "1");
        tempLessonPair.put("Tutorial", "4");
        expectedListOfModules.put("CS3230", new TimetableModule("CS3230",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Laboratory", "3");
        tempLessonPair.put("Lecture", "1");
        tempLessonPair.put("Tutorial", "3");
        expectedListOfModules.put("CS3241", new TimetableModule("CS3241",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Laboratory", "1");
        tempLessonPair.put("Lecture", "1");
        expectedListOfModules.put("CS3247", new TimetableModule("CS3247",
                tempLessonPair));

        tempLessonPair = new HashMap<String, String>();
        tempLessonPair.put("Lecture", "SL1");
        expectedListOfModules.put("GES1021", new TimetableModule("GES1021",
                tempLessonPair));

        //set up expectedTimetableDisplayInfo Strings
        URL timetableDisplayInfoViewUrl = getTestFileUrl(TIMETABLE_DISPLAY_INFO_VIEW_FILE_PATH);
        expectedTimetableDisplayInfoView = Resources.toString(timetableDisplayInfoViewUrl, Charsets.UTF_8);

        URL timetableDisplayInfoCompareUrl = getTestFileUrl(TIMETABLE_DISPLAY_INFO_COMPARE_FILE_PATH);
        expectedTimetableDisplayInfoCompare = Resources.toString(timetableDisplayInfoCompareUrl, Charsets.UTF_8);
    }

    @Test
    public void expandShortTimetableUrl_invalidShortUrl_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setExpandedTimetableUrl(
                        new Timetable(("")))); // empty string
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setExpandedTimetableUrl(
                        new Timetable(("www.google.com")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setExpandedTimetableUrl(
                        new Timetable(("http://www.facebook.com")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setExpandedTimetableUrl(
                        new Timetable(("http://www.modsn.us/")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setExpandedTimetableUrl(
                        new Timetable(("http://www.modsn.us/q7cLP")))); // invalid host
        Assert.assertThrows(IllegalArgumentException.class, () ->
                TimetableUtil.setExpandedTimetableUrl(
                        new Timetable(("http://www.modsn.us/")))); // code-part needs at least 1 character
    }

    @Test
    public void expandShortTimetableUrl_validUrl() throws ParseException {
        Timetable timetable = new Timetable(VALID_SHORT_URL);
        TimetableUtil.setExpandedTimetableUrl(timetable);
        assertEquals(timetable.getExpandedUrl(), VALID_LONG_URL);
    }

    @Test
    public void expandShortTimetableUrl_invalidUrl_throwsParseException() {
        Assert.assertThrows(ParseException.class, () ->
                TimetableUtil.setExpandedTimetableUrl(new Timetable(INVALID_SHORT_URL)));
    }

    @Test
    public void setUpTimetableInfo() {
        Timetable actualTimetable = new Timetable(VALID_SHORT_URL);

        assertDoesNotThrow(() -> TimetableUtil.setExpandedTimetableUrl(actualTimetable));
        assertEquals(VALID_LONG_URL, actualTimetable.getExpandedUrl());
        assertEquals(expectedListOfModules, actualTimetable.getModuleCodeToTimetableModule());
        assertEquals(CURRENT_SEMESTER, actualTimetable.getCurrentSemester());
        assertEquals(expectedTimetableDisplayInfoView.trim(), actualTimetable.getTimetableDisplayInfo().trim());
    }

    @Test
    public void setUpTimetableInfoView() {
        Timetable actualTimetable = new Timetable(VALID_SHORT_URL);
        actualTimetable = TimetableUtil.setUpTimetableInfoView(actualTimetable);
        assertEquals(expectedTimetableDisplayInfoView.trim(), actualTimetable.getTimetableDisplayInfo().trim());
    }

    @Test
    public void setUpTimetableInfoCompare() {
        Timetable partnerTimetable = new Timetable(VALID_SHORT_URL);
        Timetable otherTimetable = new Timetable(VALID_TIMETABLE_BOB);
        Timetable actualTimetable = TimetableUtil.setUpTimetableInfoCompare(partnerTimetable, otherTimetable);
        assertEquals(expectedTimetableDisplayInfoCompare.trim(), actualTimetable.getTimetableDisplayInfo().trim());
    }

    /**
     * Returns an url to the test resource
     * @param testFilePath path of the test resource
     */
    private URL getTestFileUrl(String testFilePath) {
        String testFilePathInView = "/view/" + testFilePath;
        URL testFileUrl = MainApp.class.getResource(testFilePathInView);
        assertNotNull(testFilePathInView + " does not exist.", testFileUrl);
        return testFileUrl;
    }
}
```
###### /java/seedu/address/model/person/timetable/TimetableTest.java
``` java
public class TimetableTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Timetable(null));
    }

    @Test
    public void constructor_invalidTimetable_throwsIllegalArgumentException() {
        String invalidTimetable = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Timetable(invalidTimetable));
    }

    @Test
    public void isValidTimetable() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Timetable.isValidTimetable(null));

        // invalid timetables
        assertFalse(Timetable.isValidTimetable("")); // empty string
        assertFalse(Timetable.isValidTimetable(" ")); // spaces only
        assertFalse(Timetable.isValidTimetable("www.google.com")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://www.facebook.com")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://www.modsn.us/")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://www.modsn.us/q7cLP")); // invalid host
        assertFalse(Timetable.isValidTimetable("http://modsn.us/")); // code-part needs at least 1 character

        // valid timetables
        assertTrue(Timetable.isValidTimetable("http://modsn.us/wNuIW"));
        assertTrue(Timetable.isValidTimetable("http://modsn.us/q7cLP")); // code-part can be alphanumeric
    }

    @Test
    public void  equals_assertsTrue() {
        Timetable timetable = new Timetable("http://modsn.us/wNuIW");
        Timetable timetableCopy = new Timetable("http://modsn.us/wNuIW");

        // same short NUSMods URL -> returns true
        assertTrue(timetable.equals(timetableCopy));

        // same object -> returns true
        assertTrue(timetable.equals(timetable));

        // different attributes other than value -> returns true
        // different timetableDisplayInfo, different listOfDays
        timetableCopy = TimetableUtil.setUpTimetableInfoCompare(timetable, timetableCopy);
        assertTrue(timetable.equals(timetableCopy));
    }

    @Test
    public void  equals_assertsFalse() {
        Timetable timetable = new Timetable("http://modsn.us/wNuIW");
        Timetable differentTimetable = new Timetable("http://modsn.us/q7cLP");

        // null -> returns false
        assertFalse(timetable.equals(null));

        // different short NUSMods URL -> returns false
        assertFalse(timetable.equals(differentTimetable));
    }

    @Test
    public void toString_assertEquals() {
        Timetable timetable = new Timetable("http://modsn.us/wNuIW");
        assertEquals("http://modsn.us/wNuIW", timetable.value);

    }
}
```
###### /java/seedu/address/testutil/Assert.java
``` java
    /**
     * Asserts that the {@code callable} does not throw any exception.
     */
    public static void assertDoesNotThrow(VoidCallable callable) {
        try {
            callable.call();
        } catch (Throwable unexpectedException) {
            String errorMessage = String.format("Expected nothing thrown, however %s thrown",
                    unexpectedException.getMessage());
            throw new AssertionFailedError(errorMessage);
        }
    }
}
```
