package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TARGET;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_COIN_SUCCESS;
import static seedu.address.testutil.TestUtil.getCoin;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalCoins.KEYWORD_MATCHING_BTC;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;

public class DeleteCommandSystemTest extends CoinBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first coin in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_COIN.getOneBased() + "       ";
        Coin deletedCoin = removeCoin(expectedModel, INDEX_FIRST_COIN);
        String expectedResultMessage = String.format(MESSAGE_DELETE_COIN_SUCCESS, deletedCoin);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last coin in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastCoinIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastCoinIndex);

        /* Case: undo deleting the last coin in the list -> last coin restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last coin in the list -> last coin deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeCoin(modelBeforeDeletingLast, lastCoinIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle coin in the list -> deleted */
        Index middleCoinIndex = getMidIndex(getModel());
        assertCommandSuccess(middleCoinIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, delete index within bounds of coin book and person list -> deleted */
        showCoinsWithName(KEYWORD_MATCHING_BTC);
        Index index = INDEX_FIRST_COIN;
        assertTrue(index.getZeroBased() < getModel().getFilteredCoinList().size());
        assertCommandSuccess(index);

        /* Case: filtered person list, delete index within bounds of coin book but out of bounds of person list
         * -> rejected
         */
        showCoinsWithName(KEYWORD_MATCHING_BTC);
        int invalidIndex = getModel().getCoinBook().getCoinList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_COMMAND_TARGET);

        /* --------------------- Performing delete operation while a coin card is selected ------------------------ */

        /* Case: delete the selected coin -> coin list panel selects the coin before the deleted coin */
        showAllCoins();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectCoin(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedCoin = removeCoin(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_COIN_SUCCESS, deletedCoin);
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
                getModel().getCoinBook().getCoinList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_COMMAND_TARGET);

        /* Case: invalid arguments (symbols) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " -#abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Coin} at the specified {@code index} in {@code model}'s coin book.
     * @return the removed coin
     */
    private Coin removeCoin(Model model, Index index) {
        Coin targetCoin = getCoin(model, index);
        try {
            model.deleteCoin(targetCoin);
        } catch (CoinNotFoundException pnfe) {
            throw new AssertionError("targetCoin is retrieved from model.");
        }
        return targetCoin;
    }

    /**
     * Deletes the coin at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Coin deletedCoin = removeCoin(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_COIN_SUCCESS, deletedCoin);

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
     * {@code CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see CoinBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
