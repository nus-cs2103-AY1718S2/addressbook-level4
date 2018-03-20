package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.TitleBar.SYNC_STATUS_INITIAL;
import static seedu.address.ui.TitleBar.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.TitleBarHandle;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.AddressBook;

public class TitleBarTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub";
    private static final String RELATIVE_PATH = "./";

    private static final AddressBookChangedEvent EVENT_STUB = new AddressBookChangedEvent(new AddressBook());

    private static final Clock originalClock = TitleBar.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private TitleBarHandle titleBarHandle;

    @BeforeClass
    public static void setUpBeforeClass() {
        // inject fixed clock
        TitleBar.setClock(injectedClock);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        TitleBar.setClock(originalClock);
    }

    @Before
    public void setUp() {
        TitleBar titleBar = new TitleBar(STUB_SAVE_LOCATION);
        uiPartRule.setUiPart(titleBar);

        titleBarHandle = new TitleBarHandle(titleBar.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL);

        // after address book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus) {
        assertEquals(expectedSaveLocation, titleBarHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, titleBarHandle.getSyncStatus());
        guiRobot.pauseForHuman();
    }

}
