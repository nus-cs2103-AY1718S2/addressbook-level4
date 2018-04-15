# karenfrilya97
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Generates file to be imported containing activities in the {@code activityList}
     * and in the directory {@code filePath}.
     */
    public static void createXmlFile(List<Activity> activityList, String filePath) throws IOException {
        if (new File(filePath).exists()) {
            new File(filePath).delete();
        }

        DeskBoard deskBoard = new DeskBoard();
        deskBoard.addActivities(activityList);

        Storage storage = new StorageManager(new XmlDeskBoardStorage(""),
                new JsonUserPrefsStorage(""));
        storage.saveDeskBoard(deskBoard, filePath);
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    private Storage storage = new StorageManager(new XmlDeskBoardStorage(""), new JsonUserPrefsStorage(""));

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ExportCommand(null);
    }

    @Test
    public void execute_validFilePath_success() throws Exception {
        ExportCommand exportCommand = getExportCommandForGivenFilePath(EXPORT_FILE_PATH, model, storage);

        CommandResult commandResult = exportCommand.execute();
        ReadOnlyDeskBoard actualDeskBoard = new XmlDeskBoardStorage(EXPORT_FILE_PATH).readDeskBoard()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_FILE_NOT_FOUND, EXPORT_FILE_PATH)));

        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, EXPORT_FILE_PATH), commandResult.feedbackToUser);
        assertEquals(getTypicalDeskBoard(), actualDeskBoard);
        new File(EXPORT_FILE_PATH).deleteOnExit();
    }

    @Test
    public void execute_existingFile_throwsCommandException() throws Throwable {
        String expectedMessage = String.format(MESSAGE_FILE_EXISTS, EXISTING_FILE_PATH);

        createXmlFile(Collections.emptyList(), EXISTING_FILE_PATH);
        ExportCommand exportCommand = getExportCommandForGivenFilePath(EXISTING_FILE_PATH, model, storage);

        assertCommandFailure(exportCommand, model, expectedMessage);
    }

    /**
     * Generates a new ExportCommand with the given file path.
     */
    private ExportCommand getExportCommandForGivenFilePath(String filePathString, Model model, Storage storage) {
        ExportCommand command = new ExportCommand(new FilePath(filePathString));
        command.setData(model, storage, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
public class ImportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model actualModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    private Storage storage = new StorageManager(new XmlDeskBoardStorage(""), new JsonUserPrefsStorage(""));

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute_validFilePath_success() throws Throwable {
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, ASSIGNMENT3_DEMO1_FILE_PATH);
        Model expectedModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        expectedModel.addActivity(ASSIGNMENT3);
        expectedModel.addActivity(DEMO1);

        createXmlFile(Arrays.asList(ASSIGNMENT3, DEMO1), ASSIGNMENT3_DEMO1_FILE_PATH);
        ImportCommand importCommand =
                getImportCommandForGivenFilePath(ASSIGNMENT3_DEMO1_FILE_PATH, actualModel, storage);

        assertCommandSuccess(importCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentFilePath_throwsCommandException() {
        String expectedMessage = String.format(MESSAGE_FILE_NOT_FOUND, MISSING_FILE_PATH);

        ImportCommand importCommand = getImportCommandForGivenFilePath(MISSING_FILE_PATH, actualModel, storage);

        assertCommandFailure(importCommand, expectedMessage);
    }

    /**
     * Test
     */
    public void execute_illegalValuesInFile_throwsCommandException() throws Throwable {
        String expectedMessage = String.format(MESSAGE_ILLEGAL_VALUES_IN_FILE, ILLEGAL_VALUES_FILE_PATH);

        ImportCommand importCommand = getImportCommandForGivenFilePath(ILLEGAL_VALUES_FILE_PATH, actualModel, storage);

        assertCommandFailure(importCommand, expectedMessage);
    }

    /**
     * The file in {@code DUPLICATE_ACTIVITY_FILE_PATH} contains {@code ASSIGNMENT3}, {@code DEMO1} and some activities
     * already in Desk Board. Only {@code ASSIGNMENT3} and {@code DEMO1} should be added into Desk Board, while
     * the existing activities are ignored.
     */
    @Test
    public void execute_fileContainsExistingActivity_addsAllExceptExistingActivity() throws Throwable {
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, DUPLICATE_ACTIVITY_FILE_PATH);
        Model expectedModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        expectedModel.addActivity(ASSIGNMENT3);
        expectedModel.addActivity(DEMO1);

        createXmlFile(Arrays.asList(ASSIGNMENT3, DEMO1, ASSIGNMENT1, CIP), DUPLICATE_ACTIVITY_FILE_PATH);
        ImportCommand importCommand =
                getImportCommandForGivenFilePath(DUPLICATE_ACTIVITY_FILE_PATH, actualModel, storage);

        assertCommandSuccess(importCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        FilePath filePath = new FilePath(IMPORT_TEST_DATA_FOLDER + "deskBoard.xml");
        FilePath differentFilePath = new FilePath(IMPORT_TEST_DATA_FOLDER + "differentDeskBoard.xml");

        ImportCommand importCommand = new ImportCommand(filePath);
        ImportCommand differentImportCommand = new ImportCommand(differentFilePath);

        // same object -> returns true
        assertTrue(importCommand.equals(importCommand));

        // same values -> returns true
        ImportCommand importAssignmentCommandCopy = new ImportCommand(filePath);
        assertTrue(importCommand.equals(importAssignmentCommandCopy));

        // null -> returns false
        assertFalse(importCommand.equals(null));

        // different types -> returns false
        assertFalse(importCommand.equals(1));

        // different file path -> returns false
        assertFalse(importCommand.equals(differentImportCommand));
    }

    /**
     * Generates a new ImportCommand with the given file path.
     */
    private ImportCommand getImportCommandForGivenFilePath(String filePathString, Model model, Storage storage) {
        ImportCommand command = new ImportCommand(new FilePath(filePathString));
        command.setData(model, storage, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\logictestutil\ImportExportTestConstants.java
``` java
/**
 * Constants to test import and export commands.
 */
public class ImportExportTestConstants {

    // ============================= IMPORT ============================================
    public static final String IMPORT_TEST_DATA_FOLDER = "src\\test\\data\\ImportCommandTest\\";
    public static final String ASSIGNMENT3_DEMO1_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "validImportFile.xml";
    public static final String MISSING_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "missing.xml";
    public static final String ILLEGAL_VALUES_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "illegalValues.xml";
    public static final String DUPLICATE_ACTIVITY_FILE_PATH = IMPORT_TEST_DATA_FOLDER + "duplicateActivity.xml";
    public static final String FILE_PATH_DESC_IMPORT = " " + PREFIX_FILE_PATH + ASSIGNMENT3_DEMO1_FILE_PATH;
    public static final String FILE_PATH_DESC_DUPLICATE = " " + PREFIX_FILE_PATH + DUPLICATE_ACTIVITY_FILE_PATH;
    public static final String INVALID_FILE_PATH_DESC = " " + PREFIX_FILE_PATH + "no.xmlAtTheEnd";

    // ============================= EXPORT ============================================
    public static final String EXPORT_TEST_DATA_FOLDER = "src\\test\\data\\ExportCommandTest\\";
    public static final String EXPORT_FILE_PATH = EXPORT_TEST_DATA_FOLDER + "validExportFile.xml";
    public static final String EXISTING_FILE_PATH = EXPORT_TEST_DATA_FOLDER + "existingFile.xml";
    public static final String FILE_PATH_DESC_EXPORT = " " + PREFIX_FILE_PATH + EXPORT_FILE_PATH;
}
```
###### \java\seedu\address\logic\parser\DeskBoardParserTest.java
``` java
    @Test
    public void parseCommand_import() throws Exception {
        FilePath filePath = new FilePath(ASSIGNMENT3_DEMO1_FILE_PATH);
        ImportCommand command = (ImportCommand) parser.parseCommand(ImportCommand.COMMAND_WORD + FILE_PATH_DESC_IMPORT);
        assertEquals(new ImportCommand(filePath), command);
    }

    @Test
    public void parseCommand_export() throws Exception {
        FilePath filePath = new FilePath(EXPORT_FILE_PATH);
        ExportCommand command = (ExportCommand) parser.parseCommand(ExportCommand.COMMAND_WORD + FILE_PATH_DESC_EXPORT);
        assertEquals(new ExportCommand(filePath), command);
    }

```
###### \java\seedu\address\logic\parser\ExportCommandParserTest.java
``` java
public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validFilePath_success() {
        FilePath filePath = new FilePath(EXPORT_FILE_PATH);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILE_PATH_DESC_EXPORT, new ExportCommand(filePath));

        // multiple file paths - last file path accepted
        assertParseSuccess(parser, FILE_PATH_DESC_DUPLICATE + FILE_PATH_DESC_EXPORT, new ExportCommand(filePath));

        // multiple file paths, the first one being invalid - last file path accepted with no exception thrown
        assertParseSuccess(parser, INVALID_FILE_PATH_DESC + FILE_PATH_DESC_EXPORT, new ExportCommand(filePath));
    }

    @Test
    public void parse_filePathMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        // missing file path prefix
        assertParseFailure(parser, EXPORT_FILE_PATH, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid file path
        assertParseFailure(parser, INVALID_FILE_PATH_DESC, FilePath.MESSAGE_FILE_PATH_CONSTRAINTS);

        // multiple file paths, the last one being invalid
        assertParseFailure(parser, FILE_PATH_DESC_EXPORT + INVALID_FILE_PATH_DESC,
                FilePath.MESSAGE_FILE_PATH_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FILE_PATH_DESC_EXPORT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validFilePath_success() {
        FilePath filePath = new FilePath(ASSIGNMENT3_DEMO1_FILE_PATH);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILE_PATH_DESC_IMPORT, new ImportCommand(filePath));

        // multiple file paths - last file path accepted
        assertParseSuccess(parser, FILE_PATH_DESC_DUPLICATE + FILE_PATH_DESC_IMPORT, new ImportCommand(filePath));

        // multiple file paths, the first one being invalid - last file path accepted with no exception thrown
        assertParseSuccess(parser, INVALID_FILE_PATH_DESC + FILE_PATH_DESC_IMPORT, new ImportCommand(filePath));
    }

    @Test
    public void parse_filePathMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);

        // missing file path prefix
        assertParseFailure(parser, ASSIGNMENT3_DEMO1_FILE_PATH, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid file path
        assertParseFailure(parser, INVALID_FILE_PATH_DESC, FilePath.MESSAGE_FILE_PATH_CONSTRAINTS);

        // multiple file paths, the last one being invalid
        assertParseFailure(parser, FILE_PATH_DESC_IMPORT + INVALID_FILE_PATH_DESC,
                FilePath.MESSAGE_FILE_PATH_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + FILE_PATH_DESC_IMPORT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\DeskBoardTest.java
``` java
    @Test
    public void addActivities_withDuplicateActivities_ignoresDuplicate() {
        DeskBoard modifiedDeskBoard = getTypicalDeskBoard();
        modifiedDeskBoard.addActivities(Collections.singletonList(ASSIGNMENT1));
        assertEquals(getTypicalDeskBoard(), modifiedDeskBoard);
    }

```
###### \java\seedu\address\model\UniqueActivityListTest.java
``` java
    @Test
    public void add_taskWithEarlierDateTimeThanExisting_sortsListAutomatically() throws DuplicateActivityException {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        Task earlierTask = ASSIGNMENT1;
        Task laterTask = ASSIGNMENT2;
        uniqueActivityList.add(laterTask);
        uniqueActivityList.add(earlierTask);
        Activity firstActivityOnTheList = uniqueActivityList.internalListAsObservable().get(0);
        assertEquals(firstActivityOnTheList, earlierTask);
    }

    @Test
    public void add_eventWithEarlierStartDateTimeThanExisting_sortsListAutomatically()
            throws DuplicateActivityException {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        Event earlierEvent = CCA;
        Event laterEvent = CIP;
        uniqueActivityList.add(laterEvent);
        uniqueActivityList.add(earlierEvent);
        Activity firstActivityOnTheList = uniqueActivityList.internalListAsObservable().get(0);
        assertEquals(firstActivityOnTheList, earlierEvent);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedEventTest.java
``` java
public class XmlAdaptedEventTest {

    private static final Event CIP_EVENT = CIP;

    private static final String INVALID_NAME = "Rachel's Bday";
    private static final String INVALID_DATE_TIME = "23 April 2018";
    private static final String INVALID_LOCATION = "";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = CIP_EVENT.getName().toString();
    private static final String VALID_START_DATE_TIME = CIP_EVENT.getStartDateTime().toString();
    private static final String VALID_END_DATE_TIME = CIP_EVENT.getEndDateTime().toString();
    private static final String VALID_LOCATION = CIP_EVENT.getLocation().toString();
    private static final String VALID_REMARK = CIP_EVENT.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = CIP_EVENT.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(CIP_EVENT);
        assertEquals(CIP_EVENT, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(INVALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, INVALID_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, null, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "start date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, INVALID_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, null,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "end date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                INVALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedTaskTest.java
``` java
public class XmlAdaptedTaskTest {

    private static final Task ASSIGNMENT2_TASK = (Task) ASSIGNMENT2;

    private static final String INVALID_NAME = "Rachel's Bday";
    private static final String INVALID_DATE_TIME = "23 April 2018";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = ASSIGNMENT2_TASK.getName().toString();
    private static final String VALID_DATE_TIME = ASSIGNMENT2_TASK.getDueDateTime().toString();
    private static final String VALID_REMARK = ASSIGNMENT2_TASK.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ASSIGNMENT2_TASK.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(ASSIGNMENT2_TASK);
        assertEquals(ASSIGNMENT2_TASK, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(INVALID_NAME, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, task.getActivityType(), "name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, INVALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, null, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, task.getActivityType(), "due date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_DATE_TIME, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

}
```
