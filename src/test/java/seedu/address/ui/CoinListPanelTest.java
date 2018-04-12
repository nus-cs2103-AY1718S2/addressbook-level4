package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCoins.getTypicalCoins;
import static seedu.address.testutil.TypicalTargets.INDEX_SECOND_COIN;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCoin;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CoinCardHandle;
import guitests.guihandles.CoinListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.coin.Coin;

public class CoinListPanelTest extends GuiUnitTest {
    private static final ObservableList<Coin> TYPICAL_COINS =
            FXCollections.observableList(getTypicalCoins());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_COIN);

    private CoinListPanelHandle coinListPanelHandle;

    @Before
    public void setUp() {
        CoinListPanel coinListPanel = new CoinListPanel(TYPICAL_COINS);
        uiPartRule.setUiPart(coinListPanel);

        coinListPanelHandle = new CoinListPanelHandle(getChildNode(coinListPanel.getRoot(),
                CoinListPanelHandle.COIN_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_COINS.size(); i++) {
            coinListPanelHandle.navigateToCard(TYPICAL_COINS.get(i));
            Coin expectedCoin = TYPICAL_COINS.get(i);
            CoinCardHandle actualCard = coinListPanelHandle.getCoinCardHandle(i);

            assertCardDisplaysCoin(expectedCoin, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        CoinCardHandle expectedCard = coinListPanelHandle.getCoinCardHandle(INDEX_SECOND_COIN.getZeroBased());
        CoinCardHandle selectedCard = coinListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
