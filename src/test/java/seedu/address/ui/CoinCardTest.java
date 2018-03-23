package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCoin;

import org.junit.Test;

import guitests.guihandles.CoinCardHandle;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.CoinBuilder;

public class CoinCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Coin coinWithNoTags = new CoinBuilder().withTags(new String[0]).build();
        CoinCard coinCard = new CoinCard(coinWithNoTags, 1);
        uiPartRule.setUiPart(coinCard);
        assertCardDisplay(coinCard, coinWithNoTags, 1);

        // with tags
        Coin coinWithTags = new CoinBuilder().build();
        coinCard = new CoinCard(coinWithTags, 2);
        uiPartRule.setUiPart(coinCard);
        assertCardDisplay(coinCard, coinWithTags, 2);
    }

    @Test
    public void equals() {
        Coin coin = new CoinBuilder().build();
        CoinCard coinCard = new CoinCard(coin, 0);

        // same coin, same index -> returns true
        CoinCard copy = new CoinCard(coin, 0);
        assertTrue(coinCard.equals(copy));

        // same object -> returns true
        assertTrue(coinCard.equals(coinCard));

        // null -> returns false
        assertFalse(coinCard.equals(null));

        // different types -> returns false
        assertFalse(coinCard.equals(0));

        // different coin, same index -> returns false
        Coin differentCoin = new CoinBuilder().withName("differentName").build();
        assertFalse(coinCard.equals(new CoinCard(differentCoin, 0)));

        // same coin, different index -> returns false
        assertFalse(coinCard.equals(new CoinCard(coin, 1)));
    }

    /**
     * Asserts that {@code coinCard} displays the details of {@code expectedCoin} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(CoinCard coinCard, Coin expectedCoin, int expectedId) {
        guiRobot.pauseForHuman();

        CoinCardHandle coinCardHandle = new CoinCardHandle(coinCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", coinCardHandle.getId());

        // verify coin details are displayed correctly
        assertCardDisplaysCoin(expectedCoin, coinCardHandle);
    }
}
