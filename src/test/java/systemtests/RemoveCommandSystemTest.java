//package systemtests;
//
//import static org.junit.Assert.assertTrue;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX;
//import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.address.logic.commands.RemoveCommand.MESSAGE_REMOVE_TASK_SUCCESS;
//import static seedu.address.testutil.TestUtil.*;
//import static seedu.address.testutil.TypicalActivities.KEYWORD_MATCHING_MEIER;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;/**/
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.RemoveCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.Model;
//import seedu.address.model.activity.Activity;
//import seedu.address.model.activity.exceptions.ActivityNotFoundException;
//
//public class RemoveCommandSystemTest extends DeskBoardSystemTest {
//
//    private static final String MESSAGE_INVALID_REMOVE_COMMAND_FORMAT =
//            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE);
//
//    //TODO: TEST
//
//    /**
//     * Test
//     */
//    public void delete() {
//        /* ----------------- Performing delete operation while an unfiltered list is being shown
// -------------------- */
//
//        /* Case: delete the first activity in the list, command with leading spaces and trailing spaces -> deleted */
//        Model expectedModel = getModel();
//        String command = "     " + RemoveCommand.COMMAND_WORD + "      task"
//                + INDEX_FIRST_ACTIVITY.getOneBased() + "       ";
//        Activity deletedTask = removeTask(expectedModel, INDEX_FIRST_ACTIVITY);
//        String expectedResultMessage = String.format(MESSAGE_REMOVE_TASK_SUCCESS, deletedTask);
//        assertCommandSuccess(command, expectedModel, expectedResultMessage);
//
//        /* Case: delete the last activity in the list -> deleted */
//        Model modelBeforeDeletingLast = getModel();
//        Index lastTaskIndex = getLastIndex(modelBeforeDeletingLast);
//        assertCommandSuccess(lastTaskIndex);
//
//        /* Case: undo deleting the last activity in the list -> last activity restored */
//        command = UndoCommand.COMMAND_WORD;
//        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
//
//        /* Case: redo deleting the last activity in the list -> last activity deleted again */
//        command = RedoCommand.COMMAND_WORD;
//        removeTask(modelBeforeDeletingLast, lastTaskIndex);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
//
//        /* Case: delete the middle activity in the list -> deleted */
//        Index middlePersonIndex = getMidIndex(getModel());
//        assertCommandSuccess(middlePersonIndex);
//
//        /* ------------------ Performing delete operation while a filtered list is being shown
// ---------------------- */
//
//        /* Case: filtered activity list, delete index within bounds of address book and activity list -> deleted */
//      /*  showPersonsWithName(KEYWORD_MATCHING_MEIER);
//        Index index = INDEX_FIRST_ACTIVITY;
//        assertTrue(index.getZeroBased() < getModel().getFilteredActivityList().size());
//        assertCommandSuccess(index);
//
//        /* Case: filtered activity list, delete index within bounds of address book but out of bounds of activity list
//         * -> rejected
//         */
//       /* showPersonsWithName(KEYWORD_MATCHING_MEIER);
//        int invalidIndex = getModel().getDeskBoard().getActivityList().size();
//        command = RemoveCommand.COMMAND_WORD + " " + invalidIndex;
//        assertCommandFailure(command, MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
//
//        /* --------------------- Performing delete operation while a activity card is selected
// ---------------------- */
//
//        /* Case: delete the selected activity -> activity list panel selects the activity before the deleted activity
// */
//      /*  showAllTask();
//        expectedModel = getModel();
//        Index selectedIndex = getLastIndex(expectedModel);
//        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
//        selectPerson(selectedIndex);
//        command = RemoveCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
//        deletedActivity = removePerson(expectedModel, selectedIndex);
//        expectedResultMessage = String.format(MESSAGE_REMOVE_PERSON_SUCCESS, deletedActivity);
//        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);
//
//        /* --------------------------------- Performing invalid delete operation
// ------------------------------------ */
//
//        /* Case: invalid index (0) -> rejected */
//        command = RemoveCommand.COMMAND_WORD + " 0";
//        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_COMMAND_FORMAT);
//
//        /* Case: invalid index (-1) -> rejected */
//        command = RemoveCommand.COMMAND_WORD + " -1";
//        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_COMMAND_FORMAT);
//
//        /* Case: invalid index (size + 1) -> rejected */
//        Index outOfBoundsIndex = Index.fromOneBased(
//                getModel().getDeskBoard().getActivityList().size() + 1);
//        command = RemoveCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
//        assertCommandFailure(command, MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
//
//        /* Case: invalid arguments (alphabets) -> rejected */
//        assertCommandFailure(RemoveCommand.COMMAND_WORD + " abc",
//                MESSAGE_INVALID_REMOVE_COMMAND_FORMAT);
//
//        /* Case: invalid arguments (extra argument) -> rejected */
//        assertCommandFailure(RemoveCommand.COMMAND_WORD + " 1 abc",
//                MESSAGE_INVALID_REMOVE_COMMAND_FORMAT);
//
//        /* Case: mixed case command word -> rejected */
//        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    /**
//     * Removes the {@code Activity} at the specified {@code index} in {@code model}'s address book.
//     * @return the removed activity
//     */
//    private Activity removeTask(Model model, Index index) {
//        Activity targetActivity = getTask(model, index);
//        try {
//            model.deleteActivity(targetActivity);
//        } catch (ActivityNotFoundException pnfe) {
//            throw new AssertionError("targetActivity is retrieved from model.");
//        }
//        return targetActivity;
//    }
//
//    /**
//     * Deletes the activity at {@code toDelete} by creating a default {@code RemoveCommand} using {@code toDelete} and
//     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
//     * @see RemoveCommandSystemTest#assertCommandSuccess(String, Model, String)
//     */
//    private void assertCommandSuccess(Index toDelete) {
//        Model expectedModel = getModel();
//        Activity deletedActivity = removeTask(expectedModel, toDelete);
//        String expectedResultMessage = String.format(MESSAGE_REMOVE_TASK_SUCCESS, deletedActivity);
//
//        assertCommandSuccess(
//                RemoveCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
//                expectedResultMessage);
//    }
//
//    /**
//     * Executes {@code command} and in addition,<br>
//     * 1. Asserts that the command box displays an empty string.<br>
//     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
//     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
//     * 4. Asserts that the browser url and selected card remains unchanged.<br>
//     * 5. Asserts that the status bar's sync status changes.<br>
//     * 6. Asserts that the command box has the default style class.<br>
//     * Verifications 1 to 3 are performed by
//     * {@code RemarkBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
//     * @see DeskBoardSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
//        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)}
//      * except that the browser url
//     * and selected card are expected to update accordingly depending on the card at
//     {@code expectedSelectedCardIndex}.
//     * @see RemoveCommandSystemTest#assertCommandSuccess(String, Model, String)
//     * @see DeskBoardSystemTest#assertSelectedTaskCardChanged(Index)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
//            Index expectedSelectedCardIndex) {
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//
//        if (expectedSelectedCardIndex != null) {
//            assertSelectedTaskCardChanged(expectedSelectedCardIndex);
//        } else {
//            assertSelectedTaskCardUnchanged();
//        }
//
//        assertCommandBoxShowsDefaultStyle();
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
//     * {@code RemarkBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     * @see DeskBoardSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedTaskCardUnchanged();
//        assertSelectedEventCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
