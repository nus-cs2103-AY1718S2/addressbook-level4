package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOE;
import static seedu.address.testutil.TypicalCoins.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COIN;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.TypicalCoins;

public class CommandTargetTest {

    private static final ObservableList<Coin> coinList = new ModelManager(getTypicalAddressBook(),
            new UserPrefs()).getFilteredCoinList();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        // same values -> returns true
        Code validCode = new Code(VALID_NAME_JOE);
        CommandTarget testTarget = new CommandTarget(validCode);
        assertTrue(testTarget.equals(new CommandTarget(new Code(VALID_NAME_JOE))));

        // same object -> returns true
        assertTrue(testTarget.equals(testTarget));

        // null -> returns false
        assertFalse(testTarget.equals(null));

        // different types -> returns false
        assertFalse(testTarget.equals(5));

        // different modes -> returns false
        CommandTarget otherTarget = new CommandTarget(INDEX_FIRST_COIN);
        assertFalse(testTarget.equals(otherTarget));

        // same mode but different values -> returns false
        otherTarget = new CommandTarget(TypicalCoins.BOB.getCode());
        assertFalse(testTarget.equals(otherTarget));
    }

    @Test
    public void toIndex_validCode() {
        CommandTarget codeTarget = new CommandTarget(
                coinList.get(INDEX_FIRST_COIN.getZeroBased()).getCode());
        assertTrue(codeTarget.toIndex(coinList).equals(INDEX_FIRST_COIN));
    }

    @Test
    public void toIndex_invalidCode_throwsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        Index index = new CommandTarget(new Code("not a valid code")).toIndex(coinList);
    }

    @Test
    public void toIndex_indexGreaterThanListSize_throwsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        Index index = new CommandTarget(Index.fromZeroBased(coinList.size())).toIndex(coinList);
    }

}
