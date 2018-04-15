//@@author ewaldhew
package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCoins.ALIS;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ChartsPanelHandle;
import seedu.address.commons.events.ui.CoinPanelSelectionChangedEvent;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.CoinBuilder;

public class ChartsPanelTest extends GuiUnitTest {
    private CoinPanelSelectionChangedEvent selectionChangedEventValidCoinStub;
    private CoinPanelSelectionChangedEvent selectionChangedEventInvalidCoinStub;

    private ChartsPanel chartsPanel;
    private ChartsPanelHandle chartsPanelHandle;

    @Before
    public void setUp() {
        final ArrayList<String> testDataX = new ArrayList<>(Arrays.asList(
                "1452592800", "1452596400", "1452600000", "1452603600", "1452607200",
                "1452610800", "1452614400", "1452618000", "1452621600"));

        final ArrayList<Amount> testDataY = new ArrayList<>(Arrays.asList(
                new Amount("0.002591"), new Amount("0.002580"), new Amount("0.002617"),
                new Amount("0.002563"), new Amount("0.002597"), new Amount("0.002576"),
                new Amount("0.002555"), new Amount("0.002719"), new Amount("0.002575")));

        Coin validCodeCoin = ALIS;
        Coin invalidCodeCoin = new CoinBuilder().withName("invalid").build();

        validCodeCoin.getPrice().setHistorical(testDataY, testDataX);

        selectionChangedEventValidCoinStub = new CoinPanelSelectionChangedEvent(new CoinCard(validCodeCoin, 0));
        selectionChangedEventInvalidCoinStub = new CoinPanelSelectionChangedEvent(new CoinCard(invalidCodeCoin, 1));

        guiRobot.interact(() -> chartsPanel = new ChartsPanel());
        uiPartRule.setUiPart(chartsPanel);

        chartsPanelHandle = new ChartsPanelHandle(chartsPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        postNow(selectionChangedEventValidCoinStub);
        assertFalse(chartsPanelHandle.isEmpty());

        postNow(selectionChangedEventInvalidCoinStub);
        assertTrue(chartsPanelHandle.isEmpty());
    }
}
