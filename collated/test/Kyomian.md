# Kyomian
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_TAG_URGENT = "Urgent";

```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String TAG_DESC_URGENT = " " + PREFIX_TAG + VALID_TAG_URGENT;

    public static final String INVALID_TASK_NAME_DESC = " " + PREFIX_NAME + "CS2106 Assignment&"; // '&' not allowed
    public static final String INVALID_TASK_DATE_TIME_DESC = " " + PREFIX_DATE_TIME + "2018-03-04 17:00";
    public static final String INVALID_TASK_REMARK_DESC = " " + PREFIX_REMARK + "$"; // '$' not allowed
    public static final String INVALID_TASK_TAG_DESC = " " + PREFIX_TAG + "CS2106*"; // '*' not allowed in tags

    // ============================= EVENT =============================================
    //TODO: Tedious

```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Removes the first activity in {@code model}'s filtered list from {@code model}'s desk board.
     */
    public static void removeFirstActivity(Model model) {
        Activity firstActivity = model.getFilteredActivityList().get(0);
        try {
            model.deleteActivity(firstActivity);
        } catch (ActivityNotFoundException pnfe) {
            throw new AssertionError("Activity in filtered list must exist in model.", pnfe);
        }
    }

```
###### \java\seedu\address\logic\commands\EventCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code EventCommand}.
 */
public class EventCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    }

    @Test
    public void execute_newEvent_success() throws Exception {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.addActivity(validEvent);

        assertCommandSuccess(prepareCommand(validEvent, model), model,
                String.format(EventCommand.MESSAGE_SUCCESS, validEvent), expectedModel);
    }

    @Test
    // Questionable - does the app check for duplicate?
    public void execute_duplicateEvent_throwsCommandException() {
        Activity activityInList = model.getDeskBoard().getActivityList().get(0);
        assertCommandFailure(prepareCommand((Event) activityInList, model), model,
                EventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Generates a new {@code EventCommand} which upon execution, adds {@code event} into the {@code model}.
     */
    private EventCommand prepareCommand(Event event, Model model) {
        EventCommand command = new EventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\EventCommandTest.java
``` java
public class EventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getEventCommandForGivenEvent(validEvent, modelStub).execute();

        assertEquals(String.format(EventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateActivityException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EventCommand.MESSAGE_DUPLICATE_EVENT);

        getEventCommandForGivenEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event cip = new EventBuilder().build();
        Event cca = new EventBuilder().withName("CCA").build();
        EventCommand addCipCommand = new EventCommand(cip);
        EventCommand addCcaCommand = new EventCommand(cca);

        // same object -> returns true
        assertTrue(addCipCommand.equals(addCipCommand));

        // same values -> returns true
        EventCommand addAssignmentCommandCopy = new EventCommand(cip);
        assertTrue(addCipCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addCipCommand.equals(1));

        // null -> returns false
        assertFalse(addCipCommand.equals(null));

        // different activity -> returns false
        assertFalse(addCipCommand.equals(addCcaCommand));
    }

    /**
     * Generates a new EventCommand with the details of the given event.
     */
    private EventCommand getEventCommandForGivenEvent(Event event, Model model) {
        EventCommand command = new EventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDeskBoard newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteActivity(Activity target) throws ActivityNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateActivity(Activity target, Activity editedActivity)
                throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList() {
            fail("This method should not be called.");
            return null;
        }

```
###### \java\seedu\address\logic\commands\RemoveCommandTest.java
``` java
/**
 * Contains unit tests for {@code RemoveCommand} and integration tests (interactions with Model, UndoCommand
 * and RedoCommand)
 */
public class RemoveCommandTest {

    private Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());

    @Test
    public void execute_validTaskOptionValidIndex_success() throws Exception {
        Activity activityToDelete = model.getFilteredTaskList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        RemoveCommand removeCommand = prepareCommand("task", INDEX_FIRST_ACTIVITY);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_TASK_SUCCESS, activityToDelete);

        ModelManager expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.deleteActivity(activityToDelete);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEventOptionValidIndex_success() throws Exception {
        Activity activityToDelete = model.getFilteredEventList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        RemoveCommand removeCommand = prepareCommand("event", INDEX_FIRST_ACTIVITY);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_EVENT_SUCCESS, activityToDelete);

        ModelManager expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.deleteActivity(activityToDelete);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validOptionInvalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        RemoveCommand removeCommand = prepareCommand("task", outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validOptionValidIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Activity activityToDelete = model.getFilteredTaskList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        RemoveCommand removeCommand = prepareCommand("task", INDEX_FIRST_ACTIVITY);
        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());

        // delete -> first activity deleted
        removeCommand.execute();
        undoRedoStack.push(removeCommand);

        // undo -> reverts desk board back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first activity deleted again
        expectedModel.deleteActivity(activityToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validOptionInvalidIndex_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        RemoveCommand removeCommand = prepareCommand("task", outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        RemoveCommand removeFirstCommand = prepareCommand("task", INDEX_FIRST_ACTIVITY);
        RemoveCommand removeSecondCommand = prepareCommand("task", INDEX_SECOND_ACTIVITY);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveCommand removeFirstCommandCopy = prepareCommand("task", INDEX_FIRST_ACTIVITY);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        removeFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different activity -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    private RemoveCommand prepareCommand(String activityOption, Index index) {
        RemoveCommand removeCommand = new RemoveCommand(activityOption, index);
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}
```
###### \java\seedu\address\logic\commands\TaskCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code TaskCommand}.
 */
public class TaskCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() throws Exception {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.addActivity(validTask);

        assertCommandSuccess(prepareCommand(validTask, model), model,
                String.format(TaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    // Questionable - does the app check for duplicate task?
    public void execute_duplicateTask_throwsCommandException() {
        Activity activityInList = model.getDeskBoard().getActivityList().get(1);
        assertCommandFailure(prepareCommand((Task) activityInList, model), model, TaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Generates a new {@code TaskCommand} which upon execution, adds {@code task} into the {@code model}.
     */
    private TaskCommand prepareCommand(Task task, Model model) {
        TaskCommand command = new TaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\TaskCommandTest.java
``` java
public class TaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new TaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getTaskCommandForGivenTask(validTask, modelStub).execute();

        assertEquals(String.format(TaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateActivityException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(TaskCommand.MESSAGE_DUPLICATE_TASK);

        getTaskCommandForGivenTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Task assignment = new TaskBuilder().build();
        Task project = new TaskBuilder().withName("Project").build();
        TaskCommand addAssignmentCommand = new TaskCommand(assignment);
        TaskCommand addProjectCommand = new TaskCommand(project);

        // same object -> returns true
        assertTrue(addAssignmentCommand.equals(addAssignmentCommand));

        // same values -> returns true
        TaskCommand addAssignmentCommandCopy = new TaskCommand(assignment);
        assertTrue(addAssignmentCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand.equals(null));

        // different activity -> returns false
        assertFalse(addAssignmentCommand.equals(addProjectCommand));
    }

    /**
     * Generates a new TaskCommand with the details of the given task.
     */
    private TaskCommand getTaskCommandForGivenTask(Task task, Model model) {
        TaskCommand command = new TaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDeskBoard newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteActivity(Activity target) throws ActivityNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateActivity(Activity target, Activity editedActivity)
                throws DuplicateActivityException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList() {
            fail("This method should not be called.");
            return null;
        }

```
###### \java\seedu\address\logic\parser\DeskBoardParserTest.java
``` java
public class DeskBoardParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DeskBoardParser parser = new DeskBoardParser();

    @Test
    public void parseCommand_task() throws Exception {
        Task task = new TaskBuilder().build();
        TaskCommand command = (TaskCommand) parser.parseCommand(TaskUtil.getTaskCommand(task));
        assertEquals(new TaskCommand(task), command);
    }

```
###### \java\seedu\address\logic\parser\EventCommandParserTest.java
``` java
public class EventCommandParserTest {
   //TODO: Tedious
}
```
###### \java\seedu\address\logic\parser\RemoveCommandParserTest.java
``` java
public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "task 1", new RemoveCommand("task", INDEX_FIRST_ACTIVITY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid activity option
        assertParseFailure(parser, "invalidOption 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand
                .MESSAGE_USAGE));

        // Only one argument
        assertParseFailure(parser, "task", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand
                .MESSAGE_USAGE));

    }
}
```
###### \java\seedu\address\logic\parser\TaskCommandParserTest.java
``` java
public class TaskCommandParserTest {
    private TaskCommandParser parser = new TaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ).withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
                .withRemark(VALID_REMARK_CS2010_QUIZ).withTags(VALID_TAG_CS2010).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010, new TaskCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_MA2108_HOMEWORK + NAME_DESC_CS2010_QUIZ
                + DATE_TIME_DESC_CS2010_QUIZ + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010,
                new TaskCommand(expectedTask));

        // multiple date times - last date time accepted
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_MA2108_HOMEWORK
                + DATE_TIME_DESC_CS2010_QUIZ + REMARK_DESC_CS2010_QUIZ
                + TAG_DESC_CS2010, new TaskCommand(expectedTask));

        // multiple remarks - last remark accepted
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_MA2108_HOMEWORK + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010,
                new TaskCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder().withName(VALID_NAME_CS2010_QUIZ)
                .withDateTime(VALID_DATE_TIME_CS2010_QUIZ)
                .withRemark(VALID_REMARK_CS2010_QUIZ)
                .withTags(VALID_TAG_CS2010, VALID_TAG_URGENT).build();
        assertParseSuccess(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_URGENT + TAG_DESC_CS2010,
                new TaskCommand(expectedTaskMultipleTags));
    }


    //TODO: Is Remark optional?
    public void parse_optionalRemarkMissing_success() {

    }

    @Test
    public void parse_optionalTagsMissing_success() {
        Task expectedTask = new TaskBuilder().withName(VALID_NAME_MA2108_HOMEWORK)
                .withDateTime(VALID_DATE_TIME_MA2108_HOMEWORK)
                .withRemark(VALID_REMARK_MA2108_HOMEWORK).withTags().build();
        assertParseSuccess(parser, NAME_DESC_MA2108_HOMEWORK + DATE_TIME_DESC_MA2108_HOMEWORK
                        + REMARK_DESC_MA2108_HOMEWORK, new TaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ, expectedMessage);

        // missing date time prefix
        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + VALID_DATE_TIME_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ, expectedMessage);

//        // missing remark prefix
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ + VALID_EMAIL_BOB + ,
//                expectedMessage);

//        // all prefixes missing
//        assertParseFailure(parser, VALID_NAME_CS2010_QUIZ + VALID_DATE_TIME_CS2010_QUIZ + VALID_EMAIL_BOB
//                + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010, Name.MESSAGE_NAME_CONSTRAINTS);

//        TODO: Got to do with DateTime Regex in Model
//        // invalid date time
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + INVALID_TASK_DATE_TIME_DESC
//                + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010, DateTime.MESSAGE_DATETIME_CONSTRAINTS);

//        TODO: Not sure myself
//        // invalid remark
//        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
//                + INVALID_TASK_REMARK_DESC + TAG_DESC_CS2010, Remark.MESSAGE_REMARK_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                + REMARK_DESC_CS2010_QUIZ + INVALID_TASK_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_TASK_NAME_DESC + DATE_TIME_DESC_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_CS2010_QUIZ + DATE_TIME_DESC_CS2010_QUIZ
                        + REMARK_DESC_CS2010_QUIZ + TAG_DESC_CS2010,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\activity\DateTimeTest.java
``` java
public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDateTime));
    }

    @Test
    public void isValidDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));
    }

    @Test
    public void isValidDateTime_validDateTime() {
        assertTrue(DateTime.isValidDateTime("01/08/1995 12:00"));
        assertTrue(DateTime.isValidDateTime("03/03/2019 12:00"));
        assertTrue(DateTime.isValidDateTime("3/3/2019 00:00"));
        assertTrue(DateTime.isValidDateTime("3/3/2019 23:59"));

    }

    @Test
    public void isValidDateTime_invalidDateTime() {
        assertFalse(DateTime.isValidDateTime(""));
        assertFalse(DateTime.isValidDateTime(" "));
        assertFalse(DateTime.isValidDateTime("2019/03/03 12:00")); // YYMMDD
        assertFalse(DateTime.isValidDateTime("12:00 03/03/2019")); // time before date
        assertFalse(DateTime.isValidDateTime("03-03-2019 12:00")); // dash, instead of slash
        assertFalse(DateTime.isValidDateTime("32/12/2018 12:00")); // wrong day
        assertFalse(DateTime.isValidDateTime("1/13/2018 10:00")); // wrong month
        assertFalse(DateTime.isValidDateTime("1/12/0000 12:00")); // wrong year
        assertFalse(DateTime.isValidDateTime("1/8/1995 25:00")); // wrong time
    }
}
```
###### \java\seedu\address\testutil\ActivityBuilder.java
``` java
    T build();

}
```
###### \java\seedu\address\testutil\EventBuilder.java
``` java
    public Event build() {
        return new Event(name, startDateTime, endDateTime, location, remark, tags);
    }

}
```
###### \java\seedu\address\testutil\EventUtil.java
``` java
/**
 * A utility class for Event.
 */
public class EventUtil {
    /**
     * Returns an event command string for adding the {@code event}.
     */
    public static String getEventCommand(Event event) {
        return EventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName().fullName + " ");
        sb.append(PREFIX_START_DATETIME + event.getStartDateTime().toString() + " ");
        sb.append(PREFIX_END_DATETIME + event.getEndDateTime().toString() + " ");
        sb.append(PREFIX_LOCATION + event.getLocation().toString() + " ");
        sb.append(PREFIX_REMARK + event.getRemark().value + " ");
        event.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TaskBuilder.java
``` java
    public Task build() {
        return new Task(name, dateTime, remark, tags);
    }
}
```
###### \java\seedu\address\testutil\TaskUtil.java
``` java
/**
 * A utility class for Task.
 */
public class TaskUtil {
    /**
     * Returns a task command string for adding the {@code task}.
     */
    public static String getTaskCommand(Task task) {
        return TaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().fullName + " ");
        sb.append(PREFIX_DATE_TIME + task.getDueDateTime().toString() + " ");
        sb.append(PREFIX_REMARK + task.getRemark().value + " ");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
```
