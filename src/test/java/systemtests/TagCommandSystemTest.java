package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;
import static seedu.address.testutil.TypicalCoins.ALICE;
import static seedu.address.testutil.TypicalCoins.BOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COIN;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CoinBuilder;
import seedu.address.testutil.CoinUtil;

public class TagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_COIN;
        String command = " " + TagCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + TAG_DESC_HUSBAND + " ";
        Coin editedCoin = new CoinBuilder()
                .withName(model.getFilteredCoinList().get(index.getZeroBased()).getCode().toString())
                .withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedCoin);

        /* Case: undo editing the last coin in the list -> last coin restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last coin in the list -> last coin edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateCoin(
                getModel().getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased()), editedCoin);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_COIN;
        command = TagCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND + "s";
        Coin coinToEdit = getModel().getFilteredCoinList().get(index.getZeroBased());
        editedCoin = new CoinBuilder(coinToEdit).withTags(VALID_TAG_FRIEND + "s").build();
        assertCommandSuccess(command, index, editedCoin);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_COIN;
        command = TagCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedCoin = new CoinBuilder(coinToEdit).withTags().build();
        assertCommandSuccess(command, index, editedCoin);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------

        /* Case: filtered coin list, edit index within bounds of address book and coin list -> edited
        showCoinsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_COIN;
        assertTrue(index.getZeroBased() < getModel().getFilteredCoinList().size());
        command = TagCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TAG_DESC_FRIEND;
        coinToEdit = getModel().getFilteredCoinList().get(index.getZeroBased());
        editedCoin = new CoinBuilder(coinToEdit).withTags(VALID_TAG_FRIEND).build();
        assertCommandSuccess(command, index, editedCoin);

        /* Case: filtered coin list, edit index within bounds of address book but out of bounds of coin list
         * -> rejected

        showCoinsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getCoinBook().getCoinList().size();
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + invalidIndex + TAG_DESC_FRIEND,
                Messages.MESSAGE_INVALID_COIN_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a coin card is selected -------------------------- */

        /* Case: selects first card in the coin list, edit a coin -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllCoins();
        index = INDEX_FIRST_COIN;
        selectCoin(index);
        command = TagCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND + "s";
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new coin's name
        assertCommandSuccess(command, index, ALICE, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " 0" + TAG_DESC_FRIEND,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " -1" + TAG_DESC_FRIEND,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredCoinList().size() + 1;
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + invalidIndex + TAG_DESC_FRIEND,
                Messages.MESSAGE_INVALID_COIN_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + TAG_DESC_FRIEND,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + INDEX_FIRST_COIN.getOneBased(),
                TagCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(TagCommand.COMMAND_WORD + " " + INDEX_FIRST_COIN.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a coin with new values same as another coin's values -> rejected */
        executeCommand(CoinUtil.getAddCommand(BOB));
        assertTrue(getModel().getCoinBook().getCoinList().contains(BOB));
        index = INDEX_FIRST_COIN;
        assertFalse(getModel().getFilteredCoinList().get(index.getZeroBased()).equals(BOB));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Coin, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see TagCommandSystemTest#assertCommandSuccess(String, Index, Coin, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Coin editedCoin) {
        assertCommandSuccess(command, toEdit, editedCoin, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the coin at index {@code toEdit} being
     * updated to values specified {@code editedCoin}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see TagCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
                String.format(TagCommand.MESSAGE_EDIT_COIN_SUCCESS, editedCoin), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see TagCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
