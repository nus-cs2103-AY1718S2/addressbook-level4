package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.StatusBarFooter.ITEM_COUNT_STATUS;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.commons.events.model.CoinBookChangedEvent;
import seedu.address.model.CoinBook;
import seedu.address.testutil.TypicalCoins;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub";
    private static final String RELATIVE_PATH = "./";

    private static final CoinBookChangedEvent EVENT_STUB = new CoinBookChangedEvent(new CoinBook());

    private static final Clock originalClock = StatusBarFooter.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private static CoinBookChangedEvent eventADDED;

    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeClass
    public static void setUpBeforeClass() {
        // inject fixed clock
        StatusBarFooter.setClock(injectedClock);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        StatusBarFooter.setClock(originalClock);
    }

    @Before
    public void setUp() throws Exception {
        StatusBarFooter statusBarFooter = new StatusBarFooter(0, STUB_SAVE_LOCATION);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());

        CoinBook tempCoinBook = new CoinBook();
        tempCoinBook.addCoin(TypicalCoins.ALIS);
        eventADDED = new CoinBookChangedEvent(tempCoinBook);
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(ITEM_COUNT_STATUS, 0),
                SYNC_STATUS_INITIAL);

        // after address book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(ITEM_COUNT_STATUS, EVENT_STUB.data.getCoinList().size()),
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));

        // after address book is updated again
        postNow(eventADDED);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(ITEM_COUNT_STATUS, eventADDED.data.getCoinList().size()),
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));
    }

    /**
     * Asserts that the -
     *   save location matches that of {@code expectedSaveLocation},
     *   item count matches that of {@code expectedItemCount}, and
     *   sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation,
                                        String expectedItemCount,
                                        String expectedSyncStatus) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedItemCount, statusBarFooterHandle.getItemCount());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        guiRobot.pauseForHuman();
    }

}
