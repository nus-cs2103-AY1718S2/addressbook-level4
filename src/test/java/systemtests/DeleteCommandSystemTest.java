package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_CINEMA_SUCCESS;
import static seedu.address.testutil.TestUtil.getCinema;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalCinemas.KEYWORD_MATCHING_SHAWS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CINEMA;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.exceptions.CinemaNotFoundException;

public class DeleteCommandSystemTest extends MoviePlannerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first cinema in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_CINEMA.getOneBased() + "       ";
        Cinema deletedCinema = removeCinema(expectedModel, INDEX_FIRST_CINEMA);
        String expectedResultMessage = String.format(MESSAGE_DELETE_CINEMA_SUCCESS, deletedCinema);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last cinema in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastCinemaIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastCinemaIndex);

        /* Case: undo deleting the last cinema in the list -> last cinema restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last cinema in the list -> last cinema deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeCinema(modelBeforeDeletingLast, lastCinemaIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle cinema in the list -> deleted */
        Index middleCinemaIndex = getMidIndex(getModel());
        assertCommandSuccess(middleCinemaIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered cinema list, delete index within bounds of movie planner and cinema list -> deleted */
        showCinemasWithName(KEYWORD_MATCHING_SHAWS);
        Index index = INDEX_FIRST_CINEMA;
        assertTrue(index.getZeroBased() < getModel().getFilteredCinemaList().size());
        assertCommandSuccess(index);

        /* Case: filtered cinema list, delete index within bounds of movie planner but out of bounds of cinema list
         * -> rejected
         */
        showCinemasWithName(KEYWORD_MATCHING_SHAWS);
        int invalidIndex = getModel().getMoviePlanner().getCinemaList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a cinema card is selected ------------------------ */

        /* Case: delete the selected cinema -> cinema list panel selects the cinema before the deleted cinema */
        showAllCinemas();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectCinema(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedCinema = removeCinema(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_CINEMA_SUCCESS, deletedCinema);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getMoviePlanner().getCinemaList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Cinema} at the specified {@code index} in {@code model}'s movie planner.
     * @return the removed cinema
     */
    private Cinema removeCinema(Model model, Index index) {
        Cinema targetCinema = getCinema(model, index);
        try {
            model.deleteCinema(targetCinema);
        } catch (CinemaNotFoundException cnfe) {
            throw new AssertionError("targetCinema is retrieved from model.");
        }
        return targetCinema;
    }

    /**
     * Deletes the cinema at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Cinema deletedCinema = removeCinema(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_CINEMA_SUCCESS, deletedCinema);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see MoviePlannerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex == null) {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MoviePlannerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
