package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CoinCardHandle;
import guitests.guihandles.CoinListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.coin.Coin;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(CoinCardHandle expectedCard, CoinCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCoin}.
     */
    public static void assertCardDisplaysCoin(Coin expectedCoin, CoinCardHandle actualCard) {
        assertEquals(expectedCoin.getCode().fullName, actualCard.getName());
        assertEquals(expectedCoin.getCurrentAmountHeld().toString(), actualCard.getAmount());
        assertEquals(expectedCoin.getPrice().toString(), actualCard.getPrice());
        assertEquals(expectedCoin.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code coinListPanelHandle} displays the details of {@code coins} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CoinListPanelHandle coinListPanelHandle, Coin... coins) {
        for (int i = 0; i < coins.length; i++) {
            assertCardDisplaysCoin(coins[i], coinListPanelHandle.getCoinCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code coinListPanelHandle} displays the details of {@code coins} correctly and
     * in the correct order.
     */
    public static void assertListMatching(CoinListPanelHandle coinListPanelHandle, List<Coin> coins) {
        assertListMatching(coinListPanelHandle, coins.toArray(new Coin[0]));
    }

    /**
     * Asserts the size of the list in {@code coinListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(CoinListPanelHandle coinListPanelHandle, int size) {
        int numberOfPeople = coinListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
