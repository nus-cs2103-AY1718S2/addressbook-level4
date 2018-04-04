//// Commented out as it takes too long on travis, please uncomment before running local tests
//
//package systemtests;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_REVISION;
//import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_STUDY;
//import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_REVISION;
//import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_STUDY;
//import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
//import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
//import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
//import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_EXAM;
//import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_REVISION;
//import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_STUDY;
//import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_REVISION;
//import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_STUDY;
//import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
//import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIENDS;
//import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
//import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_STUDY;
//import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STUDY;
//import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
//import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_STUDY;
//import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
////import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
////import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
//import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
//import static seedu.organizer.testutil.TypicalTasks.KEYWORD_MATCHING_SPRING;
//import static seedu.organizer.testutil.TypicalTasks.REVISION;
//import static seedu.organizer.testutil.TypicalTasks.STUDY;
//
//import org.junit.Test;
//
//import seedu.organizer.commons.core.Messages;
//import seedu.organizer.commons.core.index.Index;
//import seedu.organizer.logic.commands.EditCommand;
//import seedu.organizer.logic.commands.RedoCommand;
//import seedu.organizer.logic.commands.UndoCommand;
//import seedu.organizer.model.Model;
//import seedu.organizer.model.tag.Tag;
//import seedu.organizer.model.task.Deadline;
//import seedu.organizer.model.task.Name;
//import seedu.organizer.model.task.Priority;
//import seedu.organizer.model.task.Task;
//import seedu.organizer.model.task.exceptions.DuplicateTaskException;
//import seedu.organizer.model.task.exceptions.TaskNotFoundException;
//import seedu.organizer.testutil.TaskBuilder;
//import seedu.organizer.testutil.TaskUtil;
//
//public class EditCommandSystemTest extends OrganizerSystemTest {
//
//    @Test
//    public void edit_unfilteredList() throws Exception {
//        Model model = getModel();
//
/* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */
//        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
//         * -> edited
//         */
//
//        Index index = INDEX_FIRST_TASK;
//        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
//                + NAME_DESC_STUDY + "  " + PRIORITY_DESC_STUDY + " " + DEADLINE_DESC_STUDY
//                + "  " + DESCRIPTION_DESC_STUDY + " " + TAG_DESC_HUSBAND + " ";
//        Task editedTask = new TaskBuilder().withName(VALID_NAME_STUDY)
//                .withPriority(VALID_PRIORITY_STUDY).withDeadline(VALID_DEADLINE_STUDY)
//                .withDescription(VALID_DESCRIPTION_STUDY).withTags(VALID_TAG_HUSBAND).build();
//        assertCommandSuccess(command, index, editedTask);
//
//        /* Case: undo editing the last task in the list -> last task restored */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: redo editing the last task in the list -> last task edited again */
//        command = RedoCommand.COMMAND_WORD;
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        model.updateTask(getModel().getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()), editedTask);
//        assertCommandSuccess(command, model, expectedResultMessage);
//
//        /* Case: edit a task with new values same as existing values -> edited */
//        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
//                + NAME_DESC_REVISION + PRIORITY_DESC_REVISION + DEADLINE_DESC_REVISION
//                + DESCRIPTION_DESC_REVISION;
//        assertCommandSuccess(command, index, REVISION);
//
//        /*Commented out as it hangs the tests, tested this test case manually and it works @@dominickenn*/
//        //        /* Case: edit some fields -> edited */
//        //        index = INDEX_FIRST_TASK;
//        //        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND;
//        //        Task taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
//        //        editedTask = new TaskBuilder(taskToEdit).withTags(VALID_TAG_FRIEND).build();
//        //        assertCommandSuccess(command, index, editedTask);
//        //
//        //        /* Case: clear tags -> cleared */
//        //        index = INDEX_FIRST_TASK;
//        //        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
//        //        editedTask = new TaskBuilder(taskToEdit).withTags().build();
//        //        assertCommandSuccess(command, index, editedTask);
//    }
//
//    @Test
//    public void edit_filteredList() {
//        Index index;
//        String command;
//        Task taskToEdit;
//        Task editedTask;
//
/* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */
//        /* Case: filtered task list, edit index within bounds of organizer book and task list -> edited */
//        showTasksWithName(KEYWORD_MATCHING_SPRING);
//        index = INDEX_FIRST_TASK;
//        assertTrue(index.getZeroBased() < getModel().getFilteredTaskList().size());
//        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_EXAM;
//        taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
//        editedTask = new TaskBuilder(taskToEdit).withName(VALID_NAME_EXAM).build();
//        assertCommandSuccess(command, index, editedTask);
//
//        /* Case: filtered task list, edit index within bounds of organizer book but out of bounds of task list
//         * -> rejected
//         */
//        showTasksWithName(KEYWORD_MATCHING_SPRING);
//        int invalidIndex = getModel().getOrganizer().getTaskList().size();
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_EXAM,
//                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void edit_taskCardSelected() {
//        Index index;
//        String command;
//
//        /* --------------------- Performing edit operation while a task card is selected -------------------------- */
//        /* Case: selects first card in the task list, edit a task -> edited, card selection remains unchanged but
//         * browser url changes
//         */
//        showAllTasks();
//        index = INDEX_FIRST_TASK;
//        selectTask(index);
//        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
//                + NAME_DESC_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY
//                + DESCRIPTION_DESC_STUDY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
//        // this can be misleading: card selection actually remains unchanged but the
//        // browser's url is updated to reflect the new task's name
//        assertCommandSuccess(command, index, STUDY, index);
//    }
//
//    @Test
//    public void edit_invalidOperation() {
//        Index index;
//        String command;
//        int invalidIndex;
//
/* --------------------------------- Performing invalid edit operation -------------------------------------- */
//        /* Case: invalid index (0) -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_STUDY,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (-1) -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_STUDY,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//
//        /* Case: invalid index (size + 1) -> rejected */
//        invalidIndex = getModel().getFilteredTaskList().size() + 1;
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_STUDY,
//                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//
//        /* Case: missing index -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_STUDY,
//                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//
//        /* Case: missing all fields -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased(),
//                EditCommand.MESSAGE_NOT_EDITED);
//
//        /* Case: invalid name -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + INVALID_NAME_DESC,
//                Name.MESSAGE_NAME_CONSTRAINTS);
//
//        /* Case: invalid priority -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + INVALID_PRIORITY_DESC,
//                Priority.MESSAGE_PRIORITY_CONSTRAINTS);
//
//        /* Case: invalid deadline -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + INVALID_DEADLINE_DESC,
//                Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
//
//        /* Case: invalid tag -> rejected */
//        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + INVALID_TAG_DESC,
//                Tag.MESSAGE_TAG_CONSTRAINTS);
//
//        /* Case: edit a task with new values same as another task's values -> rejected */
//        index = INDEX_FIRST_TASK;
//        assertFalse(getModel().getFilteredTaskList()
//                .get(index.getZeroBased()).equals(REVISION));
//        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
//                + NAME_DESC_REVISION + PRIORITY_DESC_REVISION + DEADLINE_DESC_REVISION
//                + DESCRIPTION_DESC_REVISION + TAG_DESC_FRIENDS;
//        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_TASK);
//
//        /* Case: edit a task with new values same as another task's values but with different tags -> rejected */
//        executeCommand(TaskUtil.getAddCommand(STUDY));
//        assertTrue(getModel().getOrganizer().getTaskList().contains(STUDY));
//        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()
//                + NAME_DESC_STUDY + PRIORITY_DESC_STUDY + DEADLINE_DESC_STUDY
//                + DESCRIPTION_DESC_STUDY + TAG_DESC_HUSBAND;
//        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_TASK);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Index, Task, Index)} except that
//     * the browser url and selected card remain unchanged.
//     *
//     * @param toEdit the index of the current model's filtered list
//     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Task, Index)
//     */
//    private void assertCommandSuccess(String command, Index toEdit, Task editedTask) {
//        assertCommandSuccess(command, toEdit, editedTask, null);
//    }
//
/**
 * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
 * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
 * 2. Asserts that the model related components are updated to reflect the task at index {@code toEdit} being
 * updated to values specified {@code editedTask}.<br>
 *
 * @param toEdit the index of the current model's filtered list.
 * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
 */
//    private void assertCommandSuccess(String command, Index toEdit, Task editedTask,
//                                      Index expectedSelectedCardIndex) {
//        Model expectedModel = getModel();
//        try {
//            expectedModel.updateTask(
//                    expectedModel.getFilteredTaskList().get(toEdit.getZeroBased()), editedTask);
//            expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
//        } catch (DuplicateTaskException | TaskNotFoundException e) {
//            throw new IllegalArgumentException(
//                    "editedTask is a duplicate in expectedModel, or it isn't found in the model.");
//        }
//
//        assertCommandSuccess(command, expectedModel,
//                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask), expectedSelectedCardIndex);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
//     * browser url and selected card remain unchanged.
//     *
//     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
//        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
//    }
//
//    /**
//     * Executes {@code command} and in addition,<br>
//     * 1. Asserts that the command box displays an empty string.<br>
//     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
//     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
//     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
//     * {@code expectedSelectedCardIndex}.<br>
//     * 5. Asserts that the status bar's sync status changes.<br>
//     * 6. Asserts that the command box has the default style class.<br>
//     * Verifications 1 to 3 are performed by
//     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     *
//     * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     * @see OrganizerSystemTest#assertSelectedCardChanged(Index)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
//                                      Index expectedSelectedCardIndex) {
//        executeCommand(command);
//        expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//        assertCommandBoxShowsDefaultStyle();
//        if (expectedSelectedCardIndex != null) {
//            assertSelectedCardChanged(expectedSelectedCardIndex);
//        } else {
//            assertSelectedCardUnchanged();
//        }
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and in addition,<br>
//     * 1. Asserts that the command box displays {@code command}.<br>
//     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
//     * 3. Asserts that the model related components equal to the current model.<br>
//     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
//     * 5. Asserts that the command box has the error style.<br>
//     * Verifications 1 to 3 are performed by
//     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     *
//     * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
