package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.StatusBarFooter.NUMBER_OF_PEOPLE_STATUS;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.testutil.AddressBookBuilder;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub";
    private static final String RELATIVE_PATH = "./";

    private static final AddressBookChangedEvent EVENT_STUB = new AddressBookChangedEvent(
            new AddressBookBuilder().withPerson(BENSON).build());

    private static final int STARTING_NUMBER_OF_PEOPLE = 0;

    private static final Clock originalClock = StatusBarFooter.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

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
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION, STARTING_NUMBER_OF_PEOPLE);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL,
                String.format(NUMBER_OF_PEOPLE_STATUS, STARTING_NUMBER_OF_PEOPLE));

        // after address book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
                String.format(NUMBER_OF_PEOPLE_STATUS, EVENT_STUB.data.getPersonList().size()));
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, the
     * sync status matches that of {@code expectedSyncStatus} and the number of people
     * matches that of {@code expectedNumberOfPeople}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus,
                                        String expectedNumberOfPeople) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedNumberOfPeople, statusBarFooterHandle.getNumberOfPeople());
        guiRobot.pauseForHuman();
    }

}
