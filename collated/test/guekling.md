# guekling
###### /java/guitests/guihandles/CalendarPanelHandle.java
``` java
/**
 * A handler for the {@code CalendarPanel} of the UI.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {

    public static final String CALENDAR_ID = "#calendarPane";

    private final StackPane calendarPane;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);

        this.calendarPane = getChildNode(CALENDAR_ID);
    }
}
```
###### /java/guitests/guihandles/EntryCardHandle.java
``` java
/**
 * Provides a handle to a {@code EntryCard} in the calendar.
 */
public class EntryCardHandle extends NodeHandle<Node> {
    private static final String ENTRY_CARD_ID = "#entryCard";

    private final Label entryCardLabel;

    public EntryCardHandle(Node cardNode) {
        super(cardNode);

        this.entryCardLabel = getChildNode(ENTRY_CARD_ID);
    }

    public String getEntryCardText() {
        return entryCardLabel.getText();
    }
}
```
###### /java/guitests/guihandles/MonthViewHandle.java
``` java
/**
 * Provides a handle for {@code MonthView}.
 */
public class MonthViewHandle extends NodeHandle<Node> {
    private static final String CALENDAR_TITLE_ID = "#calendarTitle";
    private static final String TASK_CALENDAR_ID = "#taskCalendar";

    private final Text calendarTitleText;
    private final GridPane taskCalendarGrid;

    public MonthViewHandle(Node monthViewNode) {
        super(monthViewNode);

        this.calendarTitleText = getChildNode(CALENDAR_TITLE_ID);
        this.taskCalendarGrid = getChildNode(TASK_CALENDAR_ID);
    }

    public String getCalendarTitleText() {
        return calendarTitleText.getText();
    }

    /**
     * Returns the node of a {@code date}.
     */
    public Node getPrintedDateNode(int date) {
        return taskCalendarGrid.lookup("#date" + String.valueOf(date));
    }

    /**
     * Returns the node of a {@code ListView} containing {@code EntryCard}.
     */
    public Node getListViewEntriesNode(int row, int column) {
        return taskCalendarGrid.lookup("#entry" + String.valueOf(row) + String.valueOf(column));
    }

    /**
     * Returns the row index of the {@code node}.
     */
    public int getRowIndex(Node node) {
        return taskCalendarGrid.getRowIndex(node);
    }

    /**
     * Returns the column index of the {@code node}.
     */
    public int getColumnIndex(Node node) {
        return taskCalendarGrid.getColumnIndex(node);
    }

    /**
     * Checks if the grid lines are visible on the {@code taskCalendar}.
     */
    public boolean isGridLinesVisible() {
        return taskCalendarGrid.isGridLinesVisible();
    }
}
```
###### /java/seedu/organizer/logic/commands/CurrentMonthCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the UI) and unit tests for CurrentMonthCommand.
 */
public class CurrentMonthCommandTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
            (getTypicalExecutedCommands());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
            (getTypicalExecutedCommands());

    private static final YearMonth currentYearMonth = YearMonth.now();

    private MonthView monthView;
    private MonthView expectedMonthView;
    private CurrentMonthCommand currentMonthCommand;

    @Before
    public void setUp() {
        monthView = new MonthView(TYPICAL_TASKS, TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);

        currentMonthCommand = new CurrentMonthCommand();
    }

    @Test
    public void execute_commandWord() {
        addCommandToExecutedCommandsList(CURRENT_MONTH_COMMAND_WORD);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth);
        guiRobot.pause();
        assertCommandSuccess(currentMonthCommand, monthView, currentMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    @Test
    public void execute_commandAlias() {
        addCommandToExecutedCommandsList(CURRENT_MONTH_COMMAND_ALIAS);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth);
        guiRobot.pause();
        assertCommandSuccess(currentMonthCommand, monthView, currentMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    /**
     * Adds a new {@code command} to the {@code TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS} observable list.
     */
    private void addCommandToExecutedCommandsList(String command) {
        TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS.add(command);
    }
}
```
###### /java/seedu/organizer/logic/commands/FindCommandTest.java
``` java
/**
 * Represents a find command with hidden internal logic and the ability to be executed for a {@code Command} of
 * type {@code T}.
 */
public abstract class FindCommandTest<T extends Command> {
    protected Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<Task>} is equal to {@code expectedList}<br>
     * - the {@code Organizer} in model remains the same after executing the {@code command}
     *
     * @throws CommandException If an error occurs during command execution.
     */
    protected void assertCommandSuccess(T command, String expectedMessage, List<Task> expectedList)
            throws CommandException {
        Organizer expectedOrganizer = new Organizer(model.getOrganizer());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredTaskList());
        assertEquals(expectedOrganizer, model.getOrganizer());
    }
}
```
###### /java/seedu/organizer/logic/commands/FindDeadlineCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindDeadlineCommand}.
 */
public class FindDeadlineCommandTest extends FindCommandTest<FindDeadlineCommand> {

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals() {
        DeadlineContainsKeywordsPredicate firstPredicate =
                new DeadlineContainsKeywordsPredicate(Collections.singletonList("2018-08-09"));
        DeadlineContainsKeywordsPredicate secondPredicate =
                new DeadlineContainsKeywordsPredicate(Collections.singletonList("2018-03-02"));

        FindDeadlineCommand findFirstCommand = new FindDeadlineCommand(firstPredicate);
        FindDeadlineCommand findSecondCommand = new FindDeadlineCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDeadlineCommand findFirstCommandCopy = new FindDeadlineCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        FindDeadlineCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        FindDeadlineCommand command = prepareCommand("2019-04-05 2019-09-14 2019-11-12");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(REVISION, PROJECT, PREPAREBREAKFAST));
    }

    /**
     * Parses {@code userInput} into a {@code FindDeadlineCommand}.
     */
    private FindDeadlineCommand prepareCommand(String userInput) {
        FindDeadlineCommand command =
                new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/organizer/logic/commands/FindDescriptionCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindDescriptionCommand}.
 */
public class FindDescriptionCommandTest extends FindCommandTest<FindDescriptionCommand> {

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("cs2101"));
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("CS2010"));

        FindDescriptionCommand findFirstCommand = new FindDescriptionCommand(firstPredicate);
        FindDescriptionCommand findSecondCommand = new FindDescriptionCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDescriptionCommand findFirstCommandCopy = new FindDescriptionCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void equals_notCaseSensitive() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        FindDescriptionCommand command = prepareCommand("op1");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(PROJECT));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        FindDescriptionCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        FindDescriptionCommand command = prepareCommand("coffee OP1 midterms");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(REVISION, PROJECT, PREPAREBREAKFAST));
    }

    /**
     * Parses {@code userInput} into a {@code FindDescriptionCommand}.
     */
    private FindDescriptionCommand prepareCommand(String userInput) {
        FindDescriptionCommand command =
            new FindDescriptionCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split
            ("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/organizer/logic/commands/FindMultipleFieldsCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindMultipleFieldsCommand}.
 */
public class FindMultipleFieldsCommandTest extends FindCommandTest<FindMultipleFieldsCommand> {

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equals() {
        MultipleFieldsContainsKeywordsPredicate firstPredicate =
                new MultipleFieldsContainsKeywordsPredicate(Collections.singletonList("first"));
        MultipleFieldsContainsKeywordsPredicate secondPredicate =
                new MultipleFieldsContainsKeywordsPredicate(Collections.singletonList("second"));

        FindMultipleFieldsCommand findFirstCommand = new FindMultipleFieldsCommand(firstPredicate);
        FindMultipleFieldsCommand findSecondCommand = new FindMultipleFieldsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindMultipleFieldsCommand findFirstCommandCopy = new FindMultipleFieldsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTaskFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        FindMultipleFieldsCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleTasksFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        FindMultipleFieldsCommand command = prepareCommand("Toast Project 2019-04-05");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(REVISION, PROJECT, PREPAREBREAKFAST));
    }

    /**
     * Parses {@code userInput} into a {@code FindMultipleFieldsCommand}.
     */
    private FindMultipleFieldsCommand prepareCommand(String userInput) {
        FindMultipleFieldsCommand command =
            new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(Arrays.asList(userInput.split
            ("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/organizer/logic/commands/NextMonthCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the UI) and unit tests for NextMonthCommand.
 */
public class NextMonthCommandTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());

    private static final YearMonth currentYearMonth = YearMonth.now();

    private MonthView monthView;
    private MonthView expectedMonthView;
    private NextMonthCommand nextMonthCommand;

    @Before
    public void setUp() {
        monthView = new MonthView(TYPICAL_TASKS, TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);

        nextMonthCommand = new NextMonthCommand();
    }

    @Test
    public void execute_commandWord() {
        // execute command with command word
        addCommandToExecutedCommandsList(NEXT_MONTH_COMMAND_WORD);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth.plusMonths(1));
        guiRobot.pause();
        assertCommandSuccess(nextMonthCommand, monthView, nextMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    @Test
    public void execute_commandAlias() {
        addCommandToExecutedCommandsList(NEXT_MONTH_COMMAND_ALIAS);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth.plusMonths(1));
        guiRobot.pause();
        assertCommandSuccess(nextMonthCommand, monthView, nextMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    /**
     * Adds a new {@code command} to the {@code TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS} observable list.
     */
    private void addCommandToExecutedCommandsList(String command) {
        TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS.add(command);
    }
}
```
###### /java/seedu/organizer/logic/commands/PreviousMonthCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the UI) and unit tests for PreviousMonthCommand.
 */
public class PreviousMonthCommandTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());

    private static final YearMonth currentYearMonth = YearMonth.now();

    private MonthView monthView;
    private MonthView expectedMonthView;
    private PreviousMonthCommand previousMonthCommand;

    @Before
    public void setUp() {
        monthView = new MonthView(TYPICAL_TASKS, TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);

        previousMonthCommand = new PreviousMonthCommand();
    }

    @Test
    public void execute_commandWord() {
        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_WORD);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth.minusMonths(1));
        guiRobot.pause();
        assertCommandSuccess(previousMonthCommand, monthView, previousMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    @Test
    public void execute_commandAlias() {
        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_ALIAS);
        com.sun.javafx.application.PlatformImpl.startup(()->{}); // initialising JavaFX toolkit explicitly
        expectedMonthView.getMonthView(currentYearMonth.minusMonths(1));
        guiRobot.pause();
        assertCommandSuccess(previousMonthCommand, monthView, previousMonthCommand.MESSAGE_SUCCESS, expectedMonthView);
    }

    /**
     * Adds a new {@code command} to the {@code TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS} observable list.
     */
    private void addCommandToExecutedCommandsList(String command) {
        TO_BE_UPDATED_TYPICAL_EXECUTED_COMMANDS.add(command);
    }
}
```
###### /java/seedu/organizer/logic/parser/FindDeadlineCommandParserTest.java
``` java
public class FindDeadlineCommandParserTest {

    private FindDeadlineCommandParser parser = new FindDeadlineCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindDeadlineCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindDeadlineCommand() {
        // no leading and trailing whitespaces
        FindDeadlineCommand expectedFindDeadlineCommand =
            new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(Arrays.asList("2018-09-09",
            "2018-01-01")));
        assertParseSuccess(parser, "2018-09-09 2018-01-01", expectedFindDeadlineCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 2018-09-09 \n \t 2018-01-01 \t", expectedFindDeadlineCommand);
    }
}
```
###### /java/seedu/organizer/logic/parser/FindDescriptionCommandParserTest.java
``` java
public class FindDescriptionCommandParserTest {

    private FindDescriptionCommandParser parser = new FindDescriptionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindDescriptionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindDescriptionCommand() {
        // no leading and trailing whitespaces
        FindDescriptionCommand expectedFindDescriptionCommand =
                new FindDescriptionCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList("cs2103", "CS2102")));
        assertParseSuccess(parser, "cs2103 CS2102", expectedFindDescriptionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n cs2103 \n \t CS2102  \t", expectedFindDescriptionCommand);
    }
}
```
###### /java/seedu/organizer/logic/parser/FindMultipleFieldsCommandParserTest.java
``` java
public class FindMultipleFieldsCommandParserTest {

    private FindMultipleFieldsCommandParser parser = new FindMultipleFieldsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindMultipleFieldsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindMultipleFieldsCommand() {
        // no leading and trailing whitespaces
        FindMultipleFieldsCommand expectedFindMultipleFieldsCommand =
            new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2102",
            "script", "2018-03-17")));
        assertParseSuccess(parser, "CS2102 script 2018-03-17", expectedFindMultipleFieldsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n CS2102 \n \t script \n \t 2018-03-17 \t", expectedFindMultipleFieldsCommand);
    }
}
```
###### /java/seedu/organizer/logic/parser/OrganizerParserTest.java
``` java
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("Study", "es2660", "update");
        FindMultipleFieldsCommand command = (FindMultipleFieldsCommand) parser.parseCommand(
                FindMultipleFieldsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindMultipleFieldsCommand commandAlias = (FindMultipleFieldsCommand) parser.parseCommand(
                FindMultipleFieldsCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(keywords)),
            commandAlias);
    }
```
###### /java/seedu/organizer/logic/parser/OrganizerParserTest.java
``` java
    @Test
    public void parseCommand_findDescription() throws Exception {
        List<String> keywords = Arrays.asList("cs2103", "cs2101", "CS2010");
        FindDescriptionCommand command = (FindDescriptionCommand) parser.parseCommand(
            FindDescriptionCommand.COMMAND_WORD + " " + keywords.stream()
            .collect(Collectors.joining(" ")));
        FindDescriptionCommand commandAlias = (FindDescriptionCommand) parser.parseCommand(
            FindDescriptionCommand.COMMAND_ALIAS + " " + keywords.stream()
            .collect(Collectors.joining(" ")));
        assertEquals(new FindDescriptionCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindDescriptionCommand(new DescriptionContainsKeywordsPredicate(keywords)), commandAlias);
    }

    @Test
    public void parseCommand_findDeadline() throws Exception {
        List<String> keywords = Arrays.asList("2018-04-03", "2019-01-01", "2018-03-17");
        FindDeadlineCommand command = (FindDeadlineCommand) parser.parseCommand(
                FindDeadlineCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        FindDeadlineCommand commandAlias = (FindDeadlineCommand) parser.parseCommand(
                FindDeadlineCommand.COMMAND_ALIAS + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(keywords)), commandAlias);
    }
```
###### /java/seedu/organizer/logic/parser/OrganizerParserTest.java
``` java
    @Test
    public void parseCommand_previousMonthCommand() throws Exception {
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_WORD) instanceof PreviousMonthCommand);
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_WORD + " 3")
                instanceof PreviousMonthCommand);
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_ALIAS) instanceof PreviousMonthCommand);
        assertTrue(parser.parseCommand(PreviousMonthCommand.COMMAND_ALIAS + " 3")
                instanceof PreviousMonthCommand);
    }

    @Test
    public void parseCommand_nextMonthCommand() throws Exception {
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_WORD) instanceof NextMonthCommand);
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_WORD + " 3")
                instanceof NextMonthCommand);
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_ALIAS) instanceof NextMonthCommand);
        assertTrue(parser.parseCommand(NextMonthCommand.COMMAND_ALIAS + " 3")
                instanceof NextMonthCommand);
    }

    @Test
    public void parseCommand_currentMonthCommand() throws Exception {
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_WORD) instanceof CurrentMonthCommand);
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_WORD + " 3")
                instanceof CurrentMonthCommand);
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_ALIAS) instanceof CurrentMonthCommand);
        assertTrue(parser.parseCommand(CurrentMonthCommand.COMMAND_ALIAS + " 3")
                instanceof CurrentMonthCommand);
    }
```
###### /java/seedu/organizer/model/task/DeadlineContainsKeywordsPredicateTest.java
``` java
public class DeadlineContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DeadlineContainsKeywordsPredicate firstPredicate = new DeadlineContainsKeywordsPredicate(
            firstPredicateKeywordList);
        DeadlineContainsKeywordsPredicate secondPredicate = new DeadlineContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeadlineContainsKeywordsPredicate firstPredicateCopy = new DeadlineContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_deadlineContainsKeywords_returnsTrue() {
        // One keyword
        DeadlineContainsKeywordsPredicate predicate = new DeadlineContainsKeywordsPredicate(Collections.singletonList
                ("2018-03-17"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Multiple keywords
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Only one matching keyword
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));
    }

    @Test
    public void test_deadlineDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DeadlineContainsKeywordsPredicate predicate = new DeadlineContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Non-matching keyword
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2018-03-17"));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Keywords match name, priority and description, but does not match deadline
        predicate = new DeadlineContainsKeywordsPredicate(Arrays.asList("2", "Study", "Chapter"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
            .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }
}
```
###### /java/seedu/organizer/model/task/DeadlineTest.java
``` java
public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline = "2018";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        Assert.assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // blank deadline
        assertTrue(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only

        // missing parts
        assertFalse(Deadline.isValidDeadline("2018-02")); // missing date
        assertFalse(Deadline.isValidDeadline("12-02")); // missing year
        assertFalse(Deadline.isValidDeadline("2019")); // missing month and date
        assertFalse(Deadline.isValidDeadline("12")); // missing year and date

        // invalid parts
        assertFalse(Deadline.isValidDeadline("17-12-12")); // invalid year
        assertFalse(Deadline.isValidDeadline("2019-20-09")); // invalid month
        assertFalse(Deadline.isValidDeadline("2016-02-40")); // invalid date
        assertFalse(Deadline.isValidDeadline("2017-2-09")); // single numbered months should be declared '0x'
        assertFalse(Deadline.isValidDeadline("2017-02-9")); // single numbered dates should be declared '0x'
        assertFalse(Deadline.isValidDeadline("12-30-2017")); // wrong format of MM-DD-YYYY
        assertFalse(Deadline.isValidDeadline("30-12-2017")); // wrong format of DD-MM-YYYY
        assertFalse(Deadline.isValidDeadline(" 2017-08-09")); // leading space
        assertFalse(Deadline.isValidDeadline("2017-08-09 ")); // trailing space
        assertFalse(Deadline.isValidDeadline("2017/09/09")); // wrong symbol

        // valid deadline
        assertTrue(Deadline.isValidDeadline("2018-03-11"));
        assertTrue(Deadline.isValidDeadline("2017-02-31"));  // dates that have already passed
        assertTrue(Deadline.isValidDeadline("3000-03-23"));   // dates in the far future
    }

    @Test
    public void hashCode_equals() {
        Deadline testDeadline = new Deadline("2018-09-09");
        LocalDate testDeadlineValue = LocalDate.parse("2018-09-09");
        assertEquals(testDeadline.hashCode(), testDeadlineValue.hashCode());
    }
}
```
###### /java/seedu/organizer/model/task/DescriptionContainsKeywordsPredicateTest.java
``` java
public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("CS2101");
        List<String> secondPredicateKeywordList = Arrays.asList("CS2101", "CS2103");

        DescriptionContainsKeywordsPredicate firstPredicate = new DescriptionContainsKeywordsPredicate(
            firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondPredicate = new DescriptionContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstPredicateCopy = new DescriptionContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate(
            Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Study for CS2103T Exam").build()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("CS2101", "CS2103"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103").build()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("CS2101", "ES2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("cs2101", "eS2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101").build()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("CS2103"));
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103T").build()));

        // Keywords match name, priority, deadline, but does not match description
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Study", "2", "2018-11-11"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
            .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }
}
```
###### /java/seedu/organizer/model/task/DescriptionTest.java
``` java
public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void isValidDescription() {
        // null organizer
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // blank descriptions
        assertTrue(Description.isValidDescription("")); // empty string
        assertTrue(Description.isValidDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription("Practice MA1101R past year questions"));
        assertTrue(Description.isValidDescription("!")); // one character
        assertTrue(Description.isValidDescription("Add new sort feature / Update README.md / Refactor Address to "
            + "Email")); // long description
    }

    @Test
    public void hashCode_equals() {
        Description testDescription = new Description("CS2103T Testing");
        String testDescriptionValue = "CS2103T Testing";
        assertEquals(testDescription.hashCode(), testDescriptionValue.hashCode());
    }
}
```
###### /java/seedu/organizer/model/task/MultipleFieldsContainsKeywordsPredicateTest.java
``` java
public class MultipleFieldsContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MultipleFieldsContainsKeywordsPredicate firstPredicate = new MultipleFieldsContainsKeywordsPredicate(
            firstPredicateKeywordList);
        MultipleFieldsContainsKeywordsPredicate secondPredicate = new MultipleFieldsContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MultipleFieldsContainsKeywordsPredicate firstPredicateCopy = new MultipleFieldsContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(Collections
                .singletonList("Study"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Exam").build()));

        // Multiple keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("Study", "Exam"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Exam").build()));

        // Only one matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("Exam", "Grocery"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Grocery").build()));

        // Mixed-case keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("sTuDy", "eXAM"));
        assertTrue(predicate.test(new TaskBuilder().withName("Study Exam").build()));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
                Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("Study for CS2103T Exam").build()));

        // Multiple keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2101", "CS2103"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103").build()));

        // Only one matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2101", "ES2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));

        // Mixed-case keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("cs2101", "eS2660"));
        assertTrue(predicate.test(new TaskBuilder().withDescription("CS2101 ES2660").build()));
    }

    @Test
    public void test_deadlineContainsKeywords_returnsTrue() {
        // One keyword
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
            Collections.singletonList("2018-03-17"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Multiple keywords
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Only one matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2018-09-09", "2019-01-01"));
        assertTrue(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(Collections
                .emptyList());
        assertFalse(predicate.test(new TaskBuilder().withName("Study").build()));

        // Non-matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("Grocery"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study Exam").build()));

        // Keywords match priority, but does not match name, description and deadline
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
                .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101").build()));

        // Non-matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("CS2103"));
        assertFalse(predicate.test(new TaskBuilder().withDescription("CS2101 CS2103T").build()));

        // Keywords match priority, but does not match name, description and deadline
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
                .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }

    @Test
    public void test_deadlineDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MultipleFieldsContainsKeywordsPredicate predicate = new MultipleFieldsContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-03-17").build()));

        // Non-matching keyword
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2018-03-17"));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2018-09-09").build()));
        assertFalse(predicate.test(new TaskBuilder().withDeadline("2019-01-01").build()));

        // Keywords match priority, but does not match name, description and deadline
        predicate = new MultipleFieldsContainsKeywordsPredicate(Arrays.asList("2"));
        assertFalse(predicate.test(new TaskBuilder().withName("Study").withPriority("2")
                .withDeadline("2018-11-11").withDescription("Chapter 1").build()));
    }

}
```
###### /java/seedu/organizer/storage/XmlAdaptedTaskTest.java
``` java
    @Test
    public void equalsTrue_sameTask() {
        XmlAdaptedTask task = new XmlAdaptedTask(SPRINGCLEAN);
        assertTrue(task.equals(task));
    }

    @Test
    public void equalsFalse() {
        XmlAdaptedTask task = new XmlAdaptedTask(SPRINGCLEAN);
        assertFalse(task.equals(new Integer(1)));
    }
```
###### /java/seedu/organizer/ui/CalendarPanelTest.java
``` java
public class CalendarPanelTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());

    private static final int SUNDAY = 7;
    private static final int FIRST_ROW = 0;
    private static final int MAX_NUM_OF_DAYS = 35;
    private static final double DAYS_IN_WEEK = 7.0;

    private CalendarPanel calendarPanel;
    private CalendarPanelHandle calendarPanelHandle;
    private MonthViewHandle monthViewHandle;
    private YearMonth currentYearMonth;

    @Before
    public void setUp() {
        calendarPanel = new CalendarPanel(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        uiPartRule.setUiPart(calendarPanel);

        calendarPanelHandle = new CalendarPanelHandle(calendarPanel.getRoot());
        monthViewHandle = new MonthViewHandle(calendarPanel.getMonthView().getRoot());

        currentYearMonth = YearMonth.now();
    }

    @Test
    public void display() {
        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear();
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);
        int expectedStartDateColumn = getExpectedDateColumn(currentYearMonth, 1);

        assertEquals(FIRST_ROW, startDateRow);
        assertEquals(expectedStartDateColumn, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        int lastDate = currentYearMonth.lengthOfMonth();
        Node lastDateNode = monthViewHandle.getPrintedDateNode(lastDate);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);
        int expectedLastDateColumn = getExpectedDateColumn(currentYearMonth, lastDate);
        int expectedLastDateRow = getExpectedDateRow(currentYearMonth, lastDate);

        assertEquals(expectedLastDateColumn, lastDateColumn);
        assertEquals(expectedLastDateRow, lastDateRow);
    }

    /**
     * Retrieves the expected column index of a {@code date}.
     */
    private int getExpectedDateColumn(YearMonth yearMonth, int date) {
        int expectedDateColumn = yearMonth.atDay(date).getDayOfWeek().getValue();

        if (expectedDateColumn == SUNDAY) {
            expectedDateColumn = 0;
        }

        return expectedDateColumn;
    }

    /**
     * Retrieves the expected row index of a {@code date}.
     */
    private int getExpectedDateRow(YearMonth yearMonth, int date) {
        int startDay = yearMonth.atDay(1).getDayOfWeek().getValue();

        if (startDay == SUNDAY) {
            startDay = 0;
        }

        int totalDays = startDay + date;

        if (totalDays <= MAX_NUM_OF_DAYS) {
            return (int) (date / DAYS_IN_WEEK);
        } else {
            return FIRST_ROW;
        }
    }
}
```
###### /java/seedu/organizer/ui/EntryCardTest.java
``` java
public class EntryCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Task task = new TaskBuilder().build();
        EntryCard entryCard = new EntryCard(task);
        uiPartRule.setUiPart(entryCard);
        assertCardDisplay(entryCard, task);
    }

    @Test
    public void getTask() {
        Task task = new TaskBuilder().build();
        EntryCard entryCard = new EntryCard(task);
        assertTaskEquals(task, entryCard.getTask());
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        EntryCard entryCard = new EntryCard(task);

        // same task, same index -> returns true
        EntryCard copy = new EntryCard(task);
        assertTrue(entryCard.equals(copy));

        // same object -> returns true
        assertTrue(entryCard.equals(entryCard));

        // null -> returns false
        assertFalse(entryCard.equals(null));

        // different types -> returns false
        assertFalse(entryCard.equals(0));
    }

    /**
     * Asserts that {@code entryCard} displays the name of {@code expectedTask} correctly.
     */
    private void assertCardDisplay(EntryCard entryCard, Task expectedTask) {
        guiRobot.pauseForHuman();

        EntryCardHandle entryCardHandle = new EntryCardHandle(entryCard.getRoot());

        // verify task name is displayed correctly
        assertEntryCardDisplaysName(expectedTask, entryCardHandle);
    }

    /**
     * Asserts that {@code actualTask} equals to that of {@code expectedTask}.
     */
    private void assertTaskEquals(Task expectedTask, Task actualTask) {
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getPriority(), actualTask.getPriority());
        assertEquals(expectedTask.getDeadline(), actualTask.getDeadline());
        assertEquals(expectedTask.getDateAdded(), actualTask.getDateAdded());
        assertEquals(expectedTask.getDateCompleted(), actualTask.getDateCompleted());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getStatus(), actualTask.getStatus());
        assertEquals(expectedTask.getTags(), actualTask.getTags());
        assertEquals(expectedTask.getSubtasks(), actualTask.getSubtasks());
    }
}
```
###### /java/seedu/organizer/ui/MonthViewTest.java
``` java
public class MonthViewTest extends GuiUnitTest {
    private static final ObservableList<Task> TYPICAL_TASKS = FXCollections.observableList(getTypicalTasks());
    private static final ObservableList<String> TYPICAL_EXECUTED_COMMANDS = FXCollections.observableList
        (getTypicalExecutedCommands());

    private static final int SUNDAY = 7;
    private static final int FIRST_ROW = 0;
    private static final int MAX_NUM_OF_DAYS = 35;
    private static final double DAYS_IN_WEEK = 7.0;

    private static final YearMonth MAY_2018 = YearMonth.of(2018, 5);
    private static final YearMonth DEC_2018 = YearMonth.of(2018, 12);

    private MonthView monthView;
    private MonthViewHandle monthViewHandle;

    @Before
    public void setUp() {
        monthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        uiPartRule.setUiPart(monthView);

        monthViewHandle = new MonthViewHandle(monthView.getRoot());
    }

    @Test
    public void display_fiveWeeksCalendar() {
        monthView.getMonthView(MAY_2018);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = "MAY 2018";
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);

        assertEquals(0, startDateRow);
        assertEquals(2, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        Node lastDateNode = monthViewHandle.getPrintedDateNode(31);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);

        assertEquals(4, lastDateColumn);
        assertEquals(4, lastDateRow);
    }

    @Test
    public void display_sixWeeksCalendar() {
        monthView.getMonthView(DEC_2018);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = "DECEMBER 2018";
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that the last date of the month is displayed in the correct row and column
        Node lastDateNode = monthViewHandle.getPrintedDateNode(31);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);

        assertEquals(1, lastDateColumn);
        assertEquals(0, lastDateRow);
    }

    @Test
    public void showEntries_fiveWeeksCalendar() {
        monthView.getMonthView(MAY_2018);
        guiRobot.pause();

        // one entry
        Task toAddTask = new TaskBuilder().withName("ES2660").withDeadline("2018-05-01").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(0, 2);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);

        // multiple entries on different dates
        toAddTask = new TaskBuilder().withName("CS2101").withDeadline("2018-05-18").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(2, 5);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);

        // entry on a Sunday
        toAddTask = new TaskBuilder().withName("GEQ1000").withDeadline("2018-05-20").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(3, 0);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);

        // entries on the same date
        toAddTask = new TaskBuilder().withName("MA1101R").withDeadline("2018-05-18").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(2, 5);
        actualEntryCard = entriesListView.getItems().get(1);
        expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void showEntries_sixWeeksCalendar() {
        monthView.getMonthView(DEC_2018);
        guiRobot.pause();

        Task toAddTask = new TaskBuilder().withName("CS2103T").withDeadline("2018-12-31").build();
        addTaskToTaskList(toAddTask);
        guiRobot.pause();

        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(0, 1);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTask);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void goToPreviousMonth_commandsSuccessful() {
        monthView.getMonthView(MAY_2018);

        // using command word to go to previous month
        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_WORD);

        MonthView expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView.getMonthView(YearMonth.of(2018, 4));
        guiRobot.pause();
        monthView.equals(expectedMonthView);

        // using command alias to go to previous month

        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_ALIAS);
        expectedMonthView.getMonthView(YearMonth.of(2018, 3));
        guiRobot.pause();
        monthView.equals(expectedMonthView);
    }

    @Test
    public void goToPreviousMonth_titleDatesAndEntriesPrintedSuccessfully() {
        monthView.getMonthView(MAY_2018);

        Task toAddTaskOne = new TaskBuilder().withName("GER1000").withDeadline("2018-04-12").build();
        addTaskToTaskList(toAddTaskOne);

        Task toAddTaskTwo = new TaskBuilder().withName("CS2010").withDeadline("2018-04-25").build();
        addTaskToTaskList(toAddTaskTwo);

        addCommandToExecutedCommandsList(PREVIOUS_MONTH_COMMAND_WORD);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = "APRIL 2018";
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that grid lines are visible after clearCalendar() is called
        assertEquals(true, monthViewHandle.isGridLinesVisible());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);

        assertEquals(0, startDateRow);
        assertEquals(0, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        Node lastDateNode = monthViewHandle.getPrintedDateNode(30);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);

        assertEquals(1, lastDateColumn);
        assertEquals(4, lastDateRow);

        // verify that entries are displayed
        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(1, 4);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTaskOne);
        assertEquals(expectedEntryCard, actualEntryCard);

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(3, 3);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTaskTwo);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void goToNextMonth_commandsSuccessful() {
        monthView.getMonthView(MAY_2018);

        // using command word to go to next month
        addCommandToExecutedCommandsList(NEXT_MONTH_COMMAND_WORD);

        MonthView expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView.getMonthView(YearMonth.of(2018, 6));
        guiRobot.pause();
        monthView.equals(expectedMonthView);

        // using command alias to go to previous month
        addCommandToExecutedCommandsList(NEXT_MONTH_COMMAND_ALIAS);
        expectedMonthView.getMonthView(YearMonth.of(2018, 7));
        guiRobot.pause();
        monthView.equals(expectedMonthView);
    }

    @Test
    public void goToNextMonth_titleDatesAndEntriesPrintedSuccessfully() {
        monthView.getMonthView(MAY_2018);

        Task toAddTaskOne = new TaskBuilder().withName("GER1000").withDeadline("2018-06-12").build();
        addTaskToTaskList(toAddTaskOne);

        Task toAddTaskTwo = new TaskBuilder().withName("CS2010").withDeadline("2018-06-25").build();
        addTaskToTaskList(toAddTaskTwo);

        addCommandToExecutedCommandsList(NEXT_MONTH_COMMAND_WORD);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = "JUNE 2018";
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that grid lines are visible after clearCalendar() is called
        assertEquals(true, monthViewHandle.isGridLinesVisible());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);

        assertEquals(0, startDateRow);
        assertEquals(5, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        Node lastDateNode = monthViewHandle.getPrintedDateNode(30);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);

        assertEquals(6, lastDateColumn);
        assertEquals(4, lastDateRow);

        // verify that entries are displayed
        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(2, 2);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTaskOne);
        assertEquals(expectedEntryCard, actualEntryCard);

        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(4, 1);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTaskTwo);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void goToCurrentMonth_commandsSuccessful() {
        monthView.getMonthView(DEC_2018);

        // using command word to go to next month
        addCommandToExecutedCommandsList(CURRENT_MONTH_COMMAND_WORD);

        MonthView expectedMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        expectedMonthView.getMonthView(YearMonth.now());
        guiRobot.pause();
        monthView.equals(expectedMonthView);

        // using command alias to go to previous month
        monthView.getMonthView(DEC_2018);
        guiRobot.pause();

        addCommandToExecutedCommandsList(CURRENT_MONTH_COMMAND_ALIAS);
        expectedMonthView.getMonthView(YearMonth.now());
        guiRobot.pause();
        monthView.equals(expectedMonthView);
    }

    @Test
    public void goToCurrentMonth_titleDatesAndEntriesPrintedSuccessfully() {
        monthView.getMonthView(DEC_2018);
        YearMonth currentYearMonth = YearMonth.now();

        Task toAddTaskOne = new TaskBuilder().withName("GER1000").withDeadline(currentYearMonth.toString()
            + "-12").build();
        addTaskToTaskList(toAddTaskOne);

        Task toAddTaskTwo = new TaskBuilder().withName("CS2010").withDeadline(currentYearMonth.toString()
            + "-25").build();
        addTaskToTaskList(toAddTaskTwo);

        addCommandToExecutedCommandsList(CURRENT_MONTH_COMMAND_WORD);
        guiRobot.pause();

        // verify that calendar title is displayed correctly
        monthViewHandle.getCalendarTitleText();
        String expectedTitle = currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear();
        assertEquals(expectedTitle, monthViewHandle.getCalendarTitleText());

        // verify that grid lines are visible after clearCalendar() is called
        assertEquals(true, monthViewHandle.isGridLinesVisible());

        // verify that the first date of the month is displayed in the correct row and column
        Node startDateNode = monthViewHandle.getPrintedDateNode(1);
        int startDateRow = monthViewHandle.getRowIndex(startDateNode);
        int startDateColumn = monthViewHandle.getColumnIndex(startDateNode);
        int expectedStartDateColumn = getExpectedDateColumn(currentYearMonth, 1);

        assertEquals(FIRST_ROW, startDateRow);
        assertEquals(expectedStartDateColumn, startDateColumn);

        // verify that the last date of the month is displayed in the correct row and column
        int lastDate = currentYearMonth.lengthOfMonth();
        Node lastDateNode = monthViewHandle.getPrintedDateNode(lastDate);
        int lastDateRow = monthViewHandle.getRowIndex(lastDateNode);
        int lastDateColumn = monthViewHandle.getColumnIndex(lastDateNode);
        int expectedLastDateColumn = getExpectedDateColumn(currentYearMonth, lastDate);
        int expectedLastDateRow = getExpectedDateRow(currentYearMonth, lastDate);

        assertEquals(expectedLastDateColumn, lastDateColumn);
        assertEquals(expectedLastDateRow, lastDateRow);

        // verify that entries are displayed
        int expectedDateColumn = getExpectedDateColumn(currentYearMonth, 12);
        int expectedDateRow = getExpectedDateRow(currentYearMonth, 12);
        ListView<EntryCard> entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(expectedDateRow,
            expectedDateColumn);
        EntryCard actualEntryCard = entriesListView.getItems().get(0);
        EntryCard expectedEntryCard = new EntryCard(toAddTaskOne);
        assertEquals(expectedEntryCard, actualEntryCard);

        expectedDateColumn = getExpectedDateColumn(currentYearMonth, 25);
        expectedDateRow = getExpectedDateRow(currentYearMonth, 25);
        entriesListView = (ListView) monthViewHandle.getListViewEntriesNode(expectedDateRow, expectedDateColumn);
        actualEntryCard = entriesListView.getItems().get(0);
        expectedEntryCard = new EntryCard(toAddTaskTwo);
        assertEquals(expectedEntryCard, actualEntryCard);
    }

    @Test
    public void equals() {
        monthView.getMonthView(MAY_2018);

        // same object -> returns true
        assertTrue(monthView.equals(monthView));

        // same month view -> returns true
        MonthView newMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        newMonthView.getMonthView(YearMonth.of(2018, 5));
        guiRobot.pause();
        assertTrue(monthView.equals(newMonthView));

        // null -> returns false
        assertFalse(monthView.equals(null));

        // different types -> returns false
        assertFalse(monthView.equals(0));

        // different month view -> returns false
        newMonthView.getMonthView(YearMonth.of(2018, 3));
        guiRobot.pause();
        assertFalse(monthView.equals(newMonthView));

        // different entries -> returns false
        ObservableList<Task> newTaskList = FXCollections.observableList(new ArrayList<>(Arrays.asList(
            PREPAREBREAKFAST)));
        MonthView differentMonthView = new MonthView(newTaskList, TYPICAL_EXECUTED_COMMANDS);
        differentMonthView.getMonthView(MAY_2018);
        guiRobot.pause();
        assertFalse(monthView.equals(differentMonthView));

        // same title but different dates -> returns false
        MonthView otherMonthView = new MonthView(TYPICAL_TASKS, TYPICAL_EXECUTED_COMMANDS);
        otherMonthView.getMonthView(DEC_2018);
        guiRobot.pause();
        newMonthView.setMonthCalendarTitle(2018, "MAY");
        guiRobot.pause();
        assertFalse(monthView.dateIsEqual(otherMonthView));
    }

    /**
     * Adds a new {@code command} to the {@code TYPICAL_EXECUTED_COMMANDS} observable list.
     */
    private void addCommandToExecutedCommandsList(String command) {
        TYPICAL_EXECUTED_COMMANDS.add(command);
    }

    /**
     * Adds a new {@code task} to the {@code TYPICAL_TASKS} observable list.
     */
    private void addTaskToTaskList(Task task) {
        TYPICAL_TASKS.add(task);
    }

    /**
     * Retrieves the expected column index of a {@code date}.
     */
    private int getExpectedDateColumn(YearMonth yearMonth, int date) {
        int expectedDateColumn = yearMonth.atDay(date).getDayOfWeek().getValue();

        if (expectedDateColumn == SUNDAY) {
            expectedDateColumn = 0;
        }

        return expectedDateColumn;
    }

    /**
     * Retrieves the expected row index of a {@code date}.
     */
    private int getExpectedDateRow(YearMonth yearMonth, int date) {
        int startDay = yearMonth.atDay(1).getDayOfWeek().getValue();

        if (startDay == SUNDAY) {
            startDay = 0;
        }

        int totalDays = startDay + date;

        if (totalDays <= MAX_NUM_OF_DAYS) {
            return (int) (date / DAYS_IN_WEEK);
        } else {
            return FIRST_ROW;
        }
    }
}
```
###### /java/seedu/organizer/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTask}.
     */
    public static void assertEntryCardDisplaysName(Task expectedTask, EntryCardHandle actualCard) {
        assertEquals(expectedTask.getName().fullName, actualCard.getEntryCardText());
    }
```
