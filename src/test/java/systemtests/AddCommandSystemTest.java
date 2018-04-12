package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FAV;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FAV;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;
import static seedu.address.testutil.TestUtil.getAddCommandSuccessMessage;
import static seedu.address.testutil.TypicalCoins.ALIS;
import static seedu.address.testutil.TypicalCoins.AMB;
import static seedu.address.testutil.TypicalCoins.BOS;
import static seedu.address.testutil.TypicalCoins.CAS;
import static seedu.address.testutil.TypicalCoins.HORSE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CoinBuilder;
import seedu.address.testutil.CoinUtil;

public class AddCommandSystemTest extends CoinBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a coin without tags to a non-empty coin book, command with leading spaces and trailing spaces
         * -> added
         */
        Coin toAdd = AMB;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMB + "  "
                + "   " + TAG_DESC_FAV + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addCoin(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a coin with all fields same as another coin in the coin book except name -> added */
        toAdd = new CoinBuilder().withName(VALID_NAME_BOS)
                .withTags(VALID_TAG_FAV).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOS
                + TAG_DESC_FAV;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty coin book -> added */
        deleteAllCoins();
        assertCommandSuccess(ALIS);

        /* Case: add a coin with tags, command with parameters in random order -> added */
        toAdd = BOS;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FAV + NAME_DESC_BOS
                + TAG_DESC_HOT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a coin, missing tags -> added */
        assertCommandSuccess(HORSE);

        /* ------------------------ Perform add operation while a coin card is selected --------------------------- */

        /* Case: selects first card in the coin list, add a coin -> added, card selection remains unchanged */
        selectCoin(Index.fromOneBased(1));
        assertCommandSuccess(CAS);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate coin -> rejected */
        command = CoinUtil.getAddCommand(HORSE);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_COIN);

        /* Case: add a duplicate coin except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalCoins#ALIS
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // CoinBook#addCoin(Coin)
        command = CoinUtil.getAddCommand(HORSE) + " " + PREFIX_TAG + "fav";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_COIN);
        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + CoinUtil.getCoinDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC;
        assertCommandFailure(command, Code.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMB
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code CoinListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Coin toAdd) {
        assertCommandSuccess(CoinUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Coin)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Coin)
     */
    private void assertCommandSuccess(String command, Coin toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addCoin(toAdd);
        } catch (DuplicateCoinException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = getAddCommandSuccessMessage(toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Coin)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code CoinListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Coin)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code CoinListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
