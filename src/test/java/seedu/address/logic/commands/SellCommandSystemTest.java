package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TARGET;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;
import static seedu.address.testutil.CoinUtil.getSellCommand;
import static seedu.address.testutil.TestUtil.DECIMAL_STRING;
import static seedu.address.testutil.TestUtil.NUM_STRING;
import static seedu.address.testutil.TestUtil.STRING_ONE_STRING;
import static seedu.address.testutil.TypicalCoins.KEYWORD_MATCHING_BTC;
import static seedu.address.testutil.TypicalTargets.INDEX_FIRST_COIN;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.testutil.CoinBuilder;
import systemtests.CoinBookSystemTest;

public class SellCommandSystemTest extends CoinBookSystemTest {

    @Test
    public void sell() throws Exception {
        Model model = getModel();
        initializeCoinsWithAmountHeld(model);

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: sell 9.99, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> bought 9.00
         */
        Index index = INDEX_FIRST_COIN;

        String command = " " + getSellCommand(index) + " ";
        Coin editedCoin = new CoinBuilder()
                .withName(model.getFilteredCoinList().get(index.getZeroBased()).getCode().toString())
                .withAmountSold(ParserUtil.parseAmount(NUM_STRING)).build();
        editedCoin.addTotalAmountSold(ParserUtil.parseAmount(DECIMAL_STRING));
        assertCommandSuccess(command, index, editedCoin);

        /* Case: undo the last sell action -> last bought coin amount is reverted to previous amount held */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo the last sell action -> last bought coin amount held is restored */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateCoin(
                getModel().getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased()), editedCoin);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered coin list, edit index within bounds of coin book and coin list -> edited */
        showCoinsWithName(KEYWORD_MATCHING_BTC);
        index = INDEX_FIRST_COIN;
        assertTrue(index.getZeroBased() < getModel().getFilteredCoinList().size());
        command = getSellCommand(index);
        Coin coinToEdit = getModel().getFilteredCoinList().get(index.getZeroBased());
        editedCoin = new CoinBuilder(coinToEdit).withAmountSold(ParserUtil.parseAmount(DECIMAL_STRING)).build();
        assertCommandSuccess(command, index, editedCoin);

        /* Case: filtered coin list, sell index within bounds of coin book but out of bounds of coin list
         * -> rejected
         */

        showCoinsWithName(KEYWORD_MATCHING_BTC);
        int invalidIndex = getModel().getCoinBook().getCoinList().size();
        assertCommandFailure(getSellCommand(Index.fromOneBased(invalidIndex)), MESSAGE_INVALID_COMMAND_TARGET);

        /* --------------------------------- Performing invalid sell operation -------------------------------------- */

        /* Case: missing index -> rejected */
        assertCommandFailure(SellCommand.COMMAND_WORD + "  " + PREFIX_AMOUNT + " " + DECIMAL_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(SellCommand.COMMAND_WORD + " " + INDEX_FIRST_COIN.getOneBased(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));

        /* Case: invalid number (TESTINGONE) -> rejected */
        assertCommandFailure(SellCommand.COMMAND_WORD + " " + INDEX_FIRST_COIN.getOneBased() + " "
                        + PREFIX_AMOUNT + STRING_ONE_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SellCommand.COMMAND_WORD + " 0 " + PREFIX_AMOUNT + DECIMAL_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SellCommand.COMMAND_WORD + " -1 " + PREFIX_AMOUNT + DECIMAL_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = model.getFilteredCoinList().size() + 1;
        assertCommandFailure(SellCommand.COMMAND_WORD + " " + Integer.toString(invalidIndex) + " "
                        + PREFIX_AMOUNT + DECIMAL_STRING,
                MESSAGE_INVALID_COMMAND_TARGET);
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the coin at index {@code toEdit} being
     * updated to values specified {@code editedCoin}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see SellCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Coin editedCoin,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateCoin(
                    expectedModel.getFilteredCoinList().get(toEdit.getZeroBased()), editedCoin);
            expectedModel.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        } catch (DuplicateCoinException | CoinNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedCoin is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(SellCommand.MESSAGE_SELL_COIN_SUCCESS, editedCoin), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Coin, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see SellCommandSystemTest#assertCommandSuccess(String, Index, Coin, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Coin editedCoin) {
        assertCommandSuccess(command, toEdit, editedCoin, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see SellCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CoinBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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

    /**
     * Initializes the {@code coins} in {@code model} to have an initial amount bought
     */
    private void initializeCoinsWithAmountHeld(Model model) throws IllegalValueException, CoinNotFoundException {
        for (Coin coin : model.getFilteredCoinList()) {
            Coin newCoin = new Coin(coin);
            newCoin.addTotalAmountSold(ParserUtil.parseAmount(NUM_STRING));
            model.updateCoin(coin, newCoin);
        }
    }
}
