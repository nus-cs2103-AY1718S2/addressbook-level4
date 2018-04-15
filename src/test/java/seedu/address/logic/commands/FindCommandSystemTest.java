package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_COINS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;
import static seedu.address.testutil.TypicalCoins.ALIS;
import static seedu.address.testutil.TypicalCoins.BTCZ;
import static seedu.address.testutil.TypicalCoins.DADI;
import static seedu.address.testutil.TypicalCoins.ELIX;
import static seedu.address.testutil.TypicalCoins.KEYWORD_MATCHING_BTC;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import systemtests.CoinBookSystemTest;
import systemtests.ModelHelper;

public class FindCommandSystemTest extends CoinBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in coin book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + PREFIX_CODE + "LI";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALIS, ELIX); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + "L";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + KEYWORD_MATCHING_BTC;
        ModelHelper.setFilteredList(expectedModel, BTCZ);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in coin book, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + BTCZ.getCode() + " OR "
                + PREFIX_CODE + DADI.getCode();
        ModelHelper.setFilteredList(expectedModel, BTCZ, DADI);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in coin book, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + DADI.getCode() + " OR "
                + PREFIX_CODE + BTCZ.getCode();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in coin book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + DADI.getCode() + " OR "
                + PREFIX_CODE + BTCZ.getCode();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in coin book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + DADI.getCode() + " OR "
                + PREFIX_CODE + BTCZ.getCode() + " OR " + PREFIX_CODE + "nonmatchingcodename";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in coin book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getCoinBook().getCoinList().contains(BTCZ));
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + DADI.getCode() + " OR "
                + PREFIX_CODE + BTCZ.getCode();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DADI);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in coin book, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + " daDI";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in coin book, keyword is substring of name -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + " da";
        ModelHelper.setFilteredList(expectedModel, DADI);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in coin book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE  + " NONEXISTENTCODE";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in coin book -> 0 persons found */
        List<Tag> tags = new ArrayList<>(BTCZ.getTags());
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + tags.get(tags.size() - 1).tagName;
        ModelHelper.setFilteredList(expectedModel, BTCZ);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllCoins();
        selectCoin(Index.fromOneBased(1));
        assertFalse(getCoinListPanel().getHandleToSelectedCard().getName().equals(DADI.getCode().fullName));
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + " DADI";
        ModelHelper.setFilteredList(expectedModel, DADI);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty coin book -> 0 persons found */
        deleteAllCoins();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_CODE + KEYWORD_MATCHING_BTC;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DADI);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_COINS_LISTED_OVERVIEW, expectedModel.getFilteredCoinList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code CoinBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
