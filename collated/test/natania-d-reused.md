# natania-d-reused
###### \java\seedu\organizer\logic\commands\DeleteRecurredTasksCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteRecurredTasksCommand}.
 */
public class DeleteRecurredTasksCommandTest {

    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
            model.recurWeeklyTask(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()), 1);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        } catch (TaskAlreadyRecurredException e) {
            e.printStackTrace();
        } catch (TaskNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_FIRST_TASK);


        String expectedMessage = String.format(DeleteRecurredTasksCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);


        ModelManager expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.deleteRecurredTasks(taskToDelete);

        assertCommandSuccess(deleteRecurredTasksCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecurredTasksCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showRecurredTasksAtIndex(model, INDEX_FIRST_TASK);

        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteRecurredTasksCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.deleteRecurredTasks(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteRecurredTasksCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecurredTasksAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_THIRD_TASK;
        // ensures that outOfBoundIndex is still in bounds of organizer list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrganizer().getTaskList().size());

        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecurredTasksCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskNotRecurring_throwsCommandException() {
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_THIRD_TASK);

        assertCommandFailure(deleteRecurredTasksCommand, model, DeleteRecurredTasksCommand.MESSAGE_NOT_RECURRED_TASK);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());

        // delete -> first task and its iterations deleted
        deleteRecurredTasksCommand.execute();
        undoRedoStack.push(deleteRecurredTasksCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.deleteRecurredTasks(taskToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteRecurredTasksCommand not pushed into undoRedoStack
        assertCommandFailure(deleteRecurredTasksCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteRecurredTasksCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_TASK);
        DeleteRecurredTasksCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRecurredTasksCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteRecurredTasksCommand} with the parameter {@code index}.
     */
    private DeleteRecurredTasksCommand prepareCommand(Index index) {
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = new DeleteRecurredTasksCommand(index);
        deleteRecurredTasksCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteRecurredTasksCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
```
###### \java\seedu\organizer\logic\parser\DeleteRecurredTasksCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteRecurredTasksCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteRecurredTasksCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteRecurredTasksCommandParserTest {

    private DeleteRecurredTasksCommandParser parser = new DeleteRecurredTasksCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRecurredTasksCommand() {
        assertParseSuccess(parser, "1", new DeleteRecurredTasksCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRecurredTasksCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\organizer\logic\parser\OrganizerParserLoggedInTest.java
``` java
    @Test
    public void parseCommand_deleteRecurredTasks() throws Exception {
        DeleteRecurredTasksCommand command = (DeleteRecurredTasksCommand) parser.parseCommand(
                DeleteRecurredTasksCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        DeleteRecurredTasksCommand commandAlias = (DeleteRecurredTasksCommand) parser.parseCommand(
                DeleteRecurredTasksCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteRecurredTasksCommand(INDEX_FIRST_TASK), command);
        assertEquals(new DeleteRecurredTasksCommand(INDEX_FIRST_TASK), commandAlias);
    }

    @Test
    public void parseCommand_deleteSubtask() throws Exception {
        DeleteSubtaskCommand command = (DeleteSubtaskCommand) parser.parseCommand(
                DeleteSubtaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + " "
                        + INDEX_FIRST_TASK.getOneBased());
        DeleteSubtaskCommand commandAlias = (DeleteSubtaskCommand) parser.parseCommand(
                DeleteSubtaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased() + " "
                        + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK), command);
        assertEquals(new DeleteSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK), commandAlias);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getTaskDetails(task));
        EditCommand commandAlias = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getTaskDetails(task));
        assertEquals(new EditCommand(INDEX_FIRST_TASK, descriptor), command);
        assertEquals(new EditCommand(INDEX_FIRST_TASK, descriptor), commandAlias);
    }

```
###### \java\seedu\organizer\model\ModelManagerTest.java
``` java
    @Test
    public void deleteTag_nonExistentTag_modelUnchanged() throws Exception {
        Organizer organizer = new OrganizerBuilder().withTask(EXAM).withTask(STUDY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        assertEquals(new ModelManager(organizer, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagUsedByMultipleTasks_tagRemoved() throws Exception {
        Organizer organizer = new OrganizerBuilder().withTask(EXAM).withTask(STUDY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Task examWithoutFriendTag = new TaskBuilder(EXAM).withTags().build();
        Task studyWithoutFriendTag = new TaskBuilder(STUDY).withTags(VALID_TAG_HUSBAND).build();
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(examWithoutFriendTag)
                .withTask(studyWithoutFriendTag).build();

        assertEquals(new ModelManager(expectedOrganizer, userPrefs), modelManager);
    }
```
###### \java\seedu\organizer\model\OrganizerTest.java
``` java
    @Test
    public void updateTask_detailsChanged_tasksAndTagsListUpdated() throws Exception {
        Organizer organizerUpdatedToExam = new OrganizerBuilder().withTask(STUDY).build();
        organizerUpdatedToExam.updateTask(STUDY, EXAM);

        Organizer expectedOrganizer = new OrganizerBuilder().withTask(EXAM).build();

        assertEquals(expectedOrganizer, organizerUpdatedToExam);
    }

    @Test
    public void removeTag_nonExistentTag_organizerUnchanged() throws Exception {
        organizerWithStudyAndExam.removeTag(new Tag(VALID_TAG_UNUSED));

        Organizer expectedOrganizer = new OrganizerBuilder().withTask(STUDY).withTask(EXAM).build();

        assertEquals(expectedOrganizer, organizerWithStudyAndExam);
    }

    @Test
    public void removeTag_tagUsedByMultipleTasks_tagRemoved() throws Exception {
        organizerWithStudyAndExam.removeTag(new Tag(VALID_TAG_FRIEND));

        Task examWithoutFriendTag = new TaskBuilder(EXAM).withTags().build();
        Task studyWithoutFriendTag = new TaskBuilder(STUDY).withTags(VALID_TAG_HUSBAND).build();
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(studyWithoutFriendTag)
                .withTask(examWithoutFriendTag).build();

        assertEquals(expectedOrganizer, organizerWithStudyAndExam);
    }

```
###### \java\seedu\organizer\model\task\DateCompletedTest.java
``` java
// Reused from team member guekling
public class DateCompletedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateCompleted(null));
    }

    @Test
    public void constructor_invalidDateCompleted_throwsIllegalArgumentException() {
        String invalidDateCompleted = "2018";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateCompleted(invalidDateCompleted));
    }

    @Test
    public void isValidDateCompleted() {
        // null dateCompleted
        Assert.assertThrows(NullPointerException.class, () -> DateCompleted.isValidDateCompleted(null));

        // blank dateCompleted
        assertFalse(DateCompleted.isValidDateCompleted("")); // empty string
        assertFalse(DateCompleted.isValidDateCompleted(" ")); // spaces only


        // missing parts
        assertFalse(DateCompleted.isValidDateCompleted("2018-02")); // missing date
        assertFalse(DateCompleted.isValidDateCompleted("12-02")); // missing year
        assertFalse(DateCompleted.isValidDateCompleted("2019")); // missing month and date
        assertFalse(DateCompleted.isValidDateCompleted("12")); // missing year and date

        // invalid parts
        assertFalse(DateCompleted.isValidDateCompleted("17-12-12")); // invalid year
        assertFalse(DateCompleted.isValidDateCompleted("2019-20-09")); // invalid month
        assertFalse(DateCompleted.isValidDateCompleted("2016-02-40")); // invalid date
        assertFalse(DateCompleted.isValidDateCompleted("2017-2-09")); // single numbered months should be declared '0x'
        assertFalse(DateCompleted.isValidDateCompleted("2017-02-9")); // single numbered dates should be declared '0x'
        assertFalse(DateCompleted.isValidDateCompleted("12-30-2017")); // wrong format of MM-DD-YYYY
        assertFalse(DateCompleted.isValidDateCompleted("30-12-2017")); // wrong format of DD-MM-YYYY
        assertFalse(DateCompleted.isValidDateCompleted(" 2017-08-09")); // leading space
        assertFalse(DateCompleted.isValidDateCompleted("2017-08-09 ")); // trailing space
        assertFalse(DateCompleted.isValidDateCompleted("2017/09/09")); // wrong symbol

        // valid dateCompleted
        assertTrue(DateCompleted.isValidDateCompleted("2018-03-11"));
        assertTrue(DateCompleted.isValidDateCompleted("2017-02-31"));  // dates that have already passed
        assertTrue(DateCompleted.isValidDateCompleted("3000-03-23"));   // dates in the far future
    }

    @Test
    public void hashCode_equals() {
        DateCompleted testDateCompleted = new DateCompleted("2018-09-09");
        LocalDate testDateCompletedValue = LocalDate.parse("2018-09-09");
        assertEquals(testDateCompleted.hashCode(), testDateCompletedValue.hashCode());
    }
}
```
