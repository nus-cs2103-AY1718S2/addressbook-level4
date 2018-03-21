package systemtests;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.organizer.testutil.TypicalTasks.EXAM;
import static seedu.organizer.testutil.TypicalTasks.GROCERY;
import static seedu.organizer.testutil.TypicalTasks.INTERVIEWPREP;
import static seedu.organizer.testutil.TypicalTasks.KEYWORD_MATCHING_SPRING;
import static seedu.organizer.testutil.TypicalTasks.MAKEPRESENT;
import static seedu.organizer.testutil.TypicalTasks.PREPAREBREAKFAST;
import static seedu.organizer.testutil.TypicalTasks.STUDY;

import org.junit.Test;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.RedoCommand;
import seedu.organizer.logic.commands.UndoCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.testutil.TaskBuilder;
import seedu.organizer.testutil.TaskUtil;

public class AddCommandSystemTest extends OrganizerSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a task without tags to a non-empty organizer, command with leading spaces and trailing spaces
         * -> added
         */
        Task toAdd = EXAM;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_EXAM + "  " + PRIORITY_DESC_EXAM + " "
                + DEADLINE_DESC_EXAM + "   " + DESCRIPTION_DESC_EXAM + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a task with all fields same as another task in the organizer book except name -> added */
        toAdd = new TaskBuilder().withName(VALID_NAME_STUDY)
                .withPriority(VALID_PRIORITY_EXAM).withDeadline(VALID_DEADLINE_EXAM)
                .withDescription(VALID_DESCRIPTION_EXAM).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_STUDY
                + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the organizer book except priority -> added */
        toAdd = new TaskBuilder().withName(VALID_NAME_EXAM)
                .withPriority(VALID_PRIORITY_STUDY).withDeadline(VALID_DEADLINE_EXAM)
                .withDescription(VALID_DESCRIPTION_EXAM).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM
                + PRIORITY_DESC_STUDY + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the organizer book except deadline -> added */
        toAdd = new TaskBuilder().withName(VALID_NAME_EXAM)
                .withPriority(VALID_PRIORITY_EXAM).withDeadline(VALID_DEADLINE_STUDY)
                .withDescription(VALID_DESCRIPTION_EXAM).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM
                + PRIORITY_DESC_EXAM + DEADLINE_DESC_STUDY + DESCRIPTION_DESC_EXAM
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the organizer book except description -> added */
        toAdd = new TaskBuilder().withName(VALID_NAME_EXAM)
                .withPriority(VALID_PRIORITY_EXAM).withDeadline(VALID_DEADLINE_EXAM)
                .withDescription(VALID_DESCRIPTION_STUDY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM
                + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_STUDY
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty organizer book -> added */
        deleteAllTasks();
        assertCommandSuccess(GROCERY);

        /* Case: add a task with tags, command with parameters in random order -> added */
        toAdd = STUDY;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND
                + PRIORITY_DESC_STUDY + DESCRIPTION_DESC_STUDY + NAME_DESC_STUDY
                + TAG_DESC_HUSBAND + DEADLINE_DESC_STUDY;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task, missing tags -> added */
        assertCommandSuccess(MAKEPRESENT);

        //@@guekling
        /* Case: add a task, missing description -> added */
        // deleteAllTasks();
        toAdd = new TaskBuilder().withName(VALID_NAME_EXAM).withPriority
                (VALID_PRIORITY_EXAM).withDeadline(VALID_DEADLINE_EXAM).withDescription("")
                .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
        //@@author

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the task list before adding -> added */
        showTasksWithName(KEYWORD_MATCHING_SPRING);
        assertCommandSuccess(INTERVIEWPREP);

        /* ------------------------ Perform add operation while a task card is selected --------------------------- */

        /* Case: selects first card in the task list, add a task -> added, card selection remains unchanged */
        selectTask(Index.fromOneBased(1));
        assertCommandSuccess(PREPAREBREAKFAST);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate task -> rejected */
        command = TaskUtil.getAddCommand(MAKEPRESENT);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a duplicate task except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalTasks#GROCERY
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // Organizer#addTask(Task)
        command = TaskUtil.getAddCommand(MAKEPRESENT) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing deadline -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM + PRIORITY_DESC_EXAM + DESCRIPTION_DESC_EXAM
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addss " + TaskUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC
                + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid priority -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM
                + INVALID_PRIORITY_DESC + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM;
        assertCommandFailure(command, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        /* Case: invalid deadline -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM
                + PRIORITY_DESC_EXAM + INVALID_DEADLINE_DESC + DESCRIPTION_DESC_EXAM;
        assertCommandFailure(command, Deadline.MESSAGE_DEADLINE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_EXAM
                + PRIORITY_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Task toAdd) {
        assertCommandSuccess(TaskUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Task)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Task)
     */
    private void assertCommandSuccess(String command, Task toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addTask(toAdd);
        } catch (DuplicateTaskException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Task)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Task)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
