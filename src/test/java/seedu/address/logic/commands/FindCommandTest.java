package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_COINS_LISTED_OVERVIEW;
import static seedu.address.testutil.TestUtil.COIN_0;
import static seedu.address.testutil.TestUtil.COIN_1;
import static seedu.address.testutil.TestUtil.COIN_2;
import static seedu.address.testutil.TestUtil.COIN_3;
import static seedu.address.testutil.TestUtil.COIN_4;
import static seedu.address.testutil.TestUtil.COIN_5;
import static seedu.address.testutil.TestUtil.COIN_6;
import static seedu.address.testutil.TestUtil.COIN_7;
import static seedu.address.testutil.TestUtil.STRING_ONE_STRING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.conditions.TagCondition;
import seedu.address.model.CoinBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.DuplicateCoinException;
import seedu.address.model.tag.Tag;


//@@author Eldon-Chung
public class FindCommandTest {

    private Model model;

    @Before
    public void setupModel() {
        this.model = new ModelManager();
    }

    @Test
    public void execute_nullCondition_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_COINS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand((coin) -> false);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findTags() throws DuplicateCoinException {
        String expectedMessage = String.format(MESSAGE_COINS_LISTED_OVERVIEW, 4);
        Predicate<Coin> tagCondition = new TagCondition(new Tag(STRING_ONE_STRING));
        FindCommand command = prepareCommand(tagCondition);
        model.addCoin(COIN_0);
        model.addCoin(COIN_1);
        model.addCoin(COIN_2);
        model.addCoin(COIN_3);
        model.addCoin(COIN_4);
        model.addCoin(COIN_5);
        model.addCoin(COIN_6);
        model.addCoin(COIN_7);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(COIN_4, COIN_5, COIN_6, COIN_7));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(Predicate<Coin> coinCondition) {
        FindCommand command =
                new FindCommand("", coinCondition);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<Coin> expectedList) {
        CoinBook expectedAddressBook = new CoinBook(model.getCoinBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredCoinList());
        assertEquals(expectedAddressBook, model.getCoinBook());
    }

}
//@@author
