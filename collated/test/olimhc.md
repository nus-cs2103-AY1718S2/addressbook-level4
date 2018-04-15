# olimhc
###### \java\seedu\address\logic\commands\CloseCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code CloseCommandTest}.
 */
public class CloseCommandTest {
    private static ListPanelController listPanelController = ListPanelController.getInstance();

    private Model model = new ModelManager(getTypicalAddressBookNew(), new UserPrefs());

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    /**
     * Ensure that the list is always displaying active clients.
     */
    @Before
    public void setUp() {
        listPanelController.setDefault();
    }

    /**
     * Tests if a particular tutor is closed properly.
     * @throws Exception
     */
    @Test
    public void execute_closeClientTutorFilteredList_success() throws Exception {
        CloseCommand closeCommand = prepareCommand(Index.fromZeroBased(0), tutorCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClient(expectedModel.getFilteredTutorList().get(0), tutorCategory);

        String expectedMessage = String.format(CloseCommand.MESSAGE_CLOSE_TUTOR_SUCCESS,
                model.getFilteredTutorList().get(0));

        expectedModel.addClosedTutor(model.getFilteredTutorList().get(0));
        assertCommandSuccess(closeCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests if a particular student is closed properly.
     * @throws Exception
     */
    @Test
    public void execute_closeClientStudentFilteredList_success() throws Exception {
        CloseCommand closeCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClient(expectedModel.getFilteredStudentList().get(0), studentCategory);

        String expectedMessage = String.format(CloseCommand.MESSAGE_CLOSE_STUDENT_SUCCESS,
                model.getFilteredStudentList().get(0));

        expectedModel.addClosedStudent(model.getFilteredStudentList().get(0));
        assertCommandSuccess(closeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void assertsRestoreNotAvailableInClosedList() throws Exception {
        ListPanelController listPanelController = ListPanelController.getInstance();
        listPanelController.switchDisplay();

        CloseCommand closeCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        try {
            closeCommand.execute();
        } catch (CommandNotAvailableInClosedViewException cna) {
            assertsRestoreNotAvailableInClosedList();
        }
    }

    /**
     * Returns an {@code CloseCommand} with parameters {@code index} and {@code category}
     */
    private CloseCommand prepareCommand(Index index, Category category) {
        CloseCommand closeCommand = new CloseCommand(index, category);
        closeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return closeCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RestoreCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code RestoreCommandTest}.
 */
public class RestoreCommandTest {
    private static ListPanelController listPanelController;

    private Model model = new ModelManager(getTypicalClosedClientsAddressBook(), new UserPrefs());

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    /**
     * Ensure display is displaying closed client list.
     */
    @BeforeClass
    public static void setup() {
        listPanelController = ListPanelController.getInstance();
        if (ListPanelController.isCurrentDisplayActiveList()) {
            listPanelController.switchDisplay();
        }
    }

    /**
     *Ensure display is at active client list after this class test.
     */
    @AfterClass
    public static void tearDownAfterClass() {
        listPanelController.setDefault();
    }

    /**
     * Tests if a particular tutor is restored properly.
     * @throws Exception
     */
    @Test
    public void execute_restoreClientTutorFilteredList_success() throws Exception {
        RestoreCommand restoreCommand = prepareCommand(Index.fromZeroBased(0), tutorCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClosedClient(expectedModel.getFilteredClosedTutorList().get(0), tutorCategory);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_TUTOR_SUCCESS,
                model.getFilteredClosedTutorList().get(0));

        expectedModel.addTutor(model.getFilteredClosedTutorList().get(0));
        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests if a particular student is restored properly.
     * @throws Exception
     */
    @Test
    public void execute_closeClientStudentFilteredList_success() throws Exception {
        RestoreCommand restoreCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteClosedClient(expectedModel.getFilteredClosedStudentList().get(0), studentCategory);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_STUDENT_SUCCESS,
                model.getFilteredClosedStudentList().get(0));

        expectedModel.addStudent(model.getFilteredClosedStudentList().get(0));
        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void assertsRestoreNotAvailableInActiveList() throws Exception {
        listPanelController.switchDisplay();
        RestoreCommand restoreCommand = prepareCommand(Index.fromZeroBased(0), studentCategory);
        try {
            restoreCommand.execute();
        } catch (CommandNotAvailableInActiveViewException cna) {
            assertsRestoreNotAvailableInActiveList();
        }
    }

    /**
     * Returns an {@code CloseCommand} with parameters {@code index} and {@code category}
     */
    private RestoreCommand prepareCommand(Index index, Category category) {
        RestoreCommand restoreCommand = new RestoreCommand(index, category);
        restoreCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return restoreCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SortByGradeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortByGradeCommand}.
 */
public class SortByGradeCommandTest {
    private Model model;
    private Model expectedModel;
    private SortByGradeCommand sortByGradeCommandForStudentList;
    private SortByGradeCommand sortByGradeCommandForTutorList;

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    @Before
    public void setup() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel =  new ModelManager(getSortedByGradeAddressBook(), new UserPrefs());

        sortByGradeCommandForStudentList = new SortByGradeCommand(studentCategory);
        sortByGradeCommandForStudentList.setData(model, new CommandHistory(), new UndoRedoStack());
        sortByGradeCommandForTutorList = new SortByGradeCommand(tutorCategory);
        sortByGradeCommandForTutorList.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeSortUnsortedToSortedByGrade() {
        assertCommandSuccessForStudentList(sortByGradeCommandForStudentList, (SortByGradeCommand.MESSAGE_SUCCESS_STUDENT
                + SortByGradeCommand.MESSAGE_SORT_DESC));
        assertCommandSuccessForTutorList(sortByGradeCommandForTutorList, (SortByGradeCommand.MESSAGE_SUCCESS_TUTOR
                + SortByGradeCommand.MESSAGE_SORT_DESC));
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForStudentList(SortByGradeCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForTutorList(SortByGradeCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredTutorList(), model.getFilteredTutorList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
```
###### \java\seedu\address\logic\commands\SortByLocationCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortByLocationCommand}.
 */
public class SortByLocationCommandTest {
    private Model model;
    private Model expectedModel;
    private SortByLocationCommand sortByLocationCommandForStudentList;
    private SortByLocationCommand sortByLocationCommandForTutorList;

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");


    @Before
    public void setup() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel =  new ModelManager(getSortedByLocationAddressBook(), new UserPrefs());

        sortByLocationCommandForStudentList = new SortByLocationCommand(studentCategory);
        sortByLocationCommandForStudentList.setData(model, new CommandHistory(), new UndoRedoStack());
        sortByLocationCommandForTutorList = new SortByLocationCommand(tutorCategory);
        sortByLocationCommandForTutorList.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeSortUnsortedToSortedByLocation() {
        assertCommandSuccessForStudentList(sortByLocationCommandForStudentList, (
                SortByLocationCommand.MESSAGE_SUCCESS_STUDENT + SortByLocationCommand.MESSAGE_SORT_DESC));
        assertCommandSuccessForTutorList(sortByLocationCommandForTutorList, (SortByLocationCommand.MESSAGE_SUCCESS_TUTOR
                + SortByLocationCommand.MESSAGE_SORT_DESC));
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForStudentList(SortByLocationCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForTutorList(SortByLocationCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredTutorList(), model.getFilteredTutorList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
```
###### \java\seedu\address\logic\commands\SortByNameCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortByNameCommand}.
 */
public class SortByNameCommandTest {
    private Model model;
    private Model expectedModel;
    private SortByNameCommand sortByNameCommandForStudentList;
    private SortByNameCommand sortByNameCommandForTutorList;

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    @Before
    public void setup() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel =  new ModelManager(getSortedByNameAddressBook(), new UserPrefs());

        sortByNameCommandForStudentList = new SortByNameCommand(studentCategory);
        sortByNameCommandForStudentList.setData(model, new CommandHistory(), new UndoRedoStack());
        sortByNameCommandForTutorList = new SortByNameCommand(tutorCategory);
        sortByNameCommandForTutorList.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeSortUnsortedToSortedByName() {
        assertCommandSuccessForStudentList(sortByNameCommandForStudentList, (SortByNameCommand.MESSAGE_SUCCESS_STUDENT
                        + SortByNameCommand.MESSAGE_SORT_DESC));
        assertCommandSuccessForTutorList(sortByNameCommandForTutorList, (SortByNameCommand.MESSAGE_SUCCESS_TUTOR
                        + SortByNameCommand.MESSAGE_SORT_DESC));
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForStudentList(SortByNameCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForTutorList(SortByNameCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredTutorList(), model.getFilteredTutorList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
```
###### \java\seedu\address\logic\commands\SortBySubjectCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortBySubjectCommand}.
 */
public class SortBySubjectCommandTest {
    private Model model;
    private Model expectedModel;
    private SortBySubjectCommand sortBySubjectCommandForStudentList;
    private SortBySubjectCommand sortBySubjectCommandForTutorList;

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    @Before
    public void setup() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel =  new ModelManager(getSortedBySubjectAddressBook(), new UserPrefs());

        sortBySubjectCommandForStudentList = new SortBySubjectCommand(studentCategory);
        sortBySubjectCommandForStudentList.setData(model, new CommandHistory(), new UndoRedoStack());
        sortBySubjectCommandForTutorList = new SortBySubjectCommand(tutorCategory);
        sortBySubjectCommandForTutorList.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeSortUnsortedToSortedBySubject() {
        assertCommandSuccessForStudentList(sortBySubjectCommandForStudentList, (
                SortBySubjectCommand.MESSAGE_SUCCESS_STUDENT + SortBySubjectCommand.MESSAGE_SORT_DESC));
        assertCommandSuccessForTutorList(sortBySubjectCommandForTutorList, (SortBySubjectCommand.MESSAGE_SUCCESS_TUTOR
                + SortBySubjectCommand.MESSAGE_SORT_DESC));
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForStudentList(SortBySubjectCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForTutorList(SortBySubjectCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredTutorList(), model.getFilteredTutorList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
```
###### \java\seedu\address\logic\commands\SwitchCommandTest.java
``` java
public class SwitchCommandTest {
    private static ListPanelController listPanelController;
    private Model model;
    private Model expectedModel;
    private SwitchCommand switchCommand;
    private EventsCollectorRule eventsCollectorRule;

    private final String expectedSwitchToClosedListMessage = String.format(SwitchCommand.MESSAGE_SUCCESS
            + SwitchCommand.MESSAGE_CLOSED_DISPLAY_LIST);
    private final String expectedSwitchToActiveListMessage = String.format(SwitchCommand.MESSAGE_SUCCESS
            + SwitchCommand.MESSAGE_ACTIVE_DISPLAY_LIST);

    @Before
    public void setUp() {
        eventsCollectorRule = new EventsCollectorRule();
        model = new ModelManager(getTypicalAddressBookNew(), new UserPrefs());
        expectedModel =  new ModelManager(getSortedByGradeAddressBook(), new UserPrefs());
        switchCommand = new SwitchCommand();
        switchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listPanelController = ListPanelController.getInstance();
        listPanelController.setDefault();
    }

    /**
     *Ensure display is at active client list after this class test.
     */
    @AfterClass
    public static void tearDownAfterClass() {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            listPanelController.switchDisplay();
        }
    }

    /**
     * Asserts that the list is successfully switched.
     */
    @Test
    public void execute_switch_success() {
        CommandResult commandResult = switchCommand.execute();
        assertEquals(expectedSwitchToClosedListMessage, commandResult.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ClientListSwitchEvent);

        commandResult = switchCommand.execute();
        assertEquals(expectedSwitchToActiveListMessage, commandResult.feedbackToUser);
    }
}
```
###### \java\seedu\address\logic\parser\CloseCommandParserTest.java
``` java
public class CloseCommandParserTest {

    private static final Category CATEGORY_STUDENT = new Category("s");
    private static final Category CATEGORY_TUTOR =  new Category("t");

    private CloseCommandParser parser = new CloseCommandParser();

    @Test
    public void parse_validArgs_returnsRestoreCommand() {
        assertParseSuccess(parser, "1 c/s", new CloseCommand(INDEX_FIRST_PERSON, CATEGORY_STUDENT));
        assertParseSuccess(parser, "1 c/t", new CloseCommand(INDEX_FIRST_PERSON, CATEGORY_TUTOR));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "z c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 c/z", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 w/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser,  "   c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));

        assertParseFailure(parser,  "   c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));

        //no category prefix and category
        assertParseFailure(parser,  "1 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));

        //no category specified
        assertParseFailure(parser,  "1 c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        //negative index
        assertParseFailure(parser,  "-1 c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));

        //zero index
        assertParseFailure(parser,  "0 c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CloseCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RestoreCommandParserTest.java
``` java
public class RestoreCommandParserTest {

    private static final Category CATEGORY_STUDENT = new Category("s");
    private static final Category CATEGORY_TUTOR =  new Category("t");

    private RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_validArgs_returnsRestoreCommand() {
        assertParseSuccess(parser, "1 c/s", new RestoreCommand(INDEX_FIRST_PERSON, CATEGORY_STUDENT));
        assertParseSuccess(parser, "1 c/t", new RestoreCommand(INDEX_FIRST_PERSON, CATEGORY_TUTOR));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "z c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 c/z", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 w/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser,  "   c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        assertParseFailure(parser,  "   c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        //no category prefix and category
        assertParseFailure(parser,  "1 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        //no category specified
        assertParseFailure(parser,  "1 c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        //negative index
        assertParseFailure(parser,  "-1 c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));

        //zero index
        assertParseFailure(parser,  "0 c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RestoreCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private static final Category CATEGORY_STUDENT = new Category("s");
    private static final Category CATEGORY_TUTOR =  new Category("t");

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "n c/s", new SortByNameCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "n c/t", new SortByNameCommand(CATEGORY_TUTOR));

        assertParseSuccess(parser, "g c/s", new SortByGradeCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "g c/t", new SortByGradeCommand(CATEGORY_TUTOR));

        assertParseSuccess(parser, "l c/s", new SortByLocationCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "l c/t", new SortByLocationCommand(CATEGORY_TUTOR));

        assertParseSuccess(parser, "s c/s", new SortBySubjectCommand(CATEGORY_STUDENT));
        assertParseSuccess(parser, "s c/t", new SortBySubjectCommand(CATEGORY_TUTOR));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_failure() {
        // no sort type specified
        assertParseFailure(parser,  "   c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        assertParseFailure(parser,  "   c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //no category prefix and category specified
        assertParseFailure(parser,  "n", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //no category specified
        assertParseFailure(parser,  "n c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid type of sort
        assertParseFailure(parser, "w c/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //invalid category
        assertParseFailure(parser, "w c/w", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));

        //invalid category prefix
        assertParseFailure(parser, "n z/t", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\SortedClients.java
``` java
/**
 * A utility class containing a list of {@code Clients} objects to be used in tests.
 */
public class SortedClients {
    //Students
    public static final Client ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").withLocation("north").withGrade("k1").withSubject("math").withCategory("s").build();
    public static final Client BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
            .withCategory("s").build();
    public static final Client CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withLocation("south").withGrade("j1")
            .withSubject("physics").withCategory("s").build();
    public static final Client DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withLocation("east").withGrade("primary6")
            .withSubject("english").withCategory("s").build();
    public static final Client ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withLocation("west").withGrade("p3")
            .withSubject("math").withCategory("s").build();
    public static final Client FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withLocation("north").withGrade("secondary1")
            .withSubject("physics").withCategory("s").build();
    public static final Client GEORGE = new ClientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withLocation("west").withGrade("j2")
            .withSubject("chemistry").withCategory("s").build();

    //Tutors
    public static final Client ANDREW = new ClientBuilder().withName("ANDREW LIM").withPhone("5212533")
            .withEmail("andrew@example.com").withAddress("Andrew street").withLocation("east").withGrade("primary2")
            .withSubject("english").withCategory("t").build();
    public static final Client EDISON = new ClientBuilder().withName("EDISON").withPhone("2313224")
            .withEmail("EDISON@example.com").withAddress("EDISON ave").withLocation("west").withGrade("j2")
            .withSubject("math").withCategory("t").build();
    public static final Client FLOWER = new ClientBuilder().withName("Flower").withPhone("2182427")
            .withEmail("flowerislife@example.com").withAddress("little flower").withLocation("central").withGrade("k1")
            .withSubject("physics").withCategory("t").build();
    public static final Client GERRARD = new ClientBuilder().withName("GERRARD").withPhone("8321242")
            .withEmail("liverpool@example.com").withAddress("Anfield").withLocation("west").withGrade("u4")
            .withSubject("chemistry").withCategory("t").build();

    // Manually added
    public static final Client HOON = new ClientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withLocation("north").withGrade("s1")
            .withSubject("chemistry").withCategory("s").build();
    public static final Client IDA = new ClientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withLocation("north").withGrade("k2")
            .withSubject("math").withCategory("s").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_AMY).withGrade(VALID_GRADE_AMY).withSubject(VALID_SUBJECT_AMY)
            .withCategory(VALID_CATEGORY_AMY).build();
    public static final Client BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_SUBJECT_BOB)
            .withCategory(VALID_CATEGORY_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private SortedClients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by name.
     */
    public static AddressBook getSortedByNameAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedByNameClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedByNameClients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                ANDREW, EDISON, FLOWER, GERRARD));
    }

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by grade.
     */
    public static AddressBook getSortedByGradeAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedByGradeClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedByGradeClients() {
        return new ArrayList<>(Arrays.asList(ALICE, ELLE, DANIEL, FIONA, BENSON, CARL, GEORGE,
                FLOWER, ANDREW, EDISON, GERRARD));
    }

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by subject
     */
    public static AddressBook getSortedBySubjectAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedBySubjectClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedBySubjectClients() {
        return new ArrayList<>(Arrays.asList(GEORGE, DANIEL, ALICE, ELLE, BENSON, CARL, FIONA,
                GERRARD, ANDREW, EDISON, FLOWER));
    }

    /**
     * Returns an {@code AddressBook} with all the typical clients sorted by location
     */
    public static AddressBook getSortedByLocationAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getSortedByLocationClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getSortedByLocationClients() {
        return new ArrayList<>(Arrays.asList(DANIEL, BENSON, ALICE, FIONA, CARL, ELLE, GEORGE,
                FLOWER, ANDREW, GERRARD, EDISON));
    }
}
```
###### \java\seedu\address\testutil\UnsortedClients.java
``` java
/**
 * A utility class containing a list of {@code Clients} objects to be used in tests.
 */
public class UnsortedClients {
    //Students
    public static final Client ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").withLocation("north").withGrade("k1").withSubject("math").withCategory("s").build();
    public static final Client BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withLocation("north").withGrade("s2").withSubject("physics")
            .withCategory("s").build();
    public static final Client CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withLocation("south").withGrade("j1")
            .withSubject("physics").withCategory("s").build();
    public static final Client DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withLocation("east").withGrade("primary6")
            .withSubject("english").withCategory("s").build();
    public static final Client ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withLocation("west").withGrade("p3")
            .withSubject("math").withCategory("s").build();
    public static final Client FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withLocation("north").withGrade("secondary1")
            .withSubject("physics").withCategory("s").build();
    public static final Client GEORGE = new ClientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withLocation("west").withGrade("j2")
            .withSubject("chemistry").withCategory("s").build();

    //Tutors
    public static final Client ANDREW = new ClientBuilder().withName("ANDREW LIM").withPhone("5212533")
            .withEmail("andrew@example.com").withAddress("Andrew street").withLocation("east").withGrade("primary2")
            .withSubject("english").withCategory("t").build();
    public static final Client EDISON = new ClientBuilder().withName("EDISON").withPhone("2313224")
            .withEmail("EDISON@example.com").withAddress("EDISON ave").withLocation("west").withGrade("j2")
            .withSubject("math").withCategory("t").build();
    public static final Client FLOWER = new ClientBuilder().withName("Flower").withPhone("2182427")
            .withEmail("flowerislife@example.com").withAddress("little flower").withLocation("central").withGrade("k1")
            .withSubject("physics").withCategory("t").build();
    public static final Client GERRARD = new ClientBuilder().withName("GERRARD").withPhone("8321242")
            .withEmail("liverpool@example.com").withAddress("Anfield").withLocation("west").withGrade("u4")
            .withSubject("chemistry").withCategory("t").build();

    // Manually added
    public static final Client HOON = new ClientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withLocation("north").withGrade("s1")
            .withSubject("chemistry").withCategory("s").build();
    public static final Client IDA = new ClientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withLocation("north").withGrade("k2")
            .withSubject("math").withCategory("s").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_AMY).withGrade(VALID_GRADE_AMY).withSubject(VALID_SUBJECT_AMY)
            .withCategory(VALID_CATEGORY_AMY).build();
    public static final Client BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withLocation(VALID_LOCATION_BOB).withGrade(VALID_GRADE_BOB).withSubject(VALID_SUBJECT_BOB)
            .withCategory(VALID_CATEGORY_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private UnsortedClients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical clients.
     */
    public static AddressBook getUnsortedAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getUnsortedClients()) {
            try {
                ab.addClient(client);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Client> getUnsortedClients() {
        return new ArrayList<>(Arrays.asList(BENSON, CARL, ALICE, ELLE, FIONA, GEORGE,
                DANIEL, GERRARD, EDISON, ANDREW, FLOWER));
    }
}
```
