package seedu.organizer.ui;

import static org.junit.Assert.assertEquals;
import static seedu.organizer.testutil.EventsUtil.postNow;
import static seedu.organizer.testutil.TypicalTasks.GROCERY;
import static seedu.organizer.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.organizer.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.organizer.ui.StatusBarFooter.TOTAL_TASKS_STATUS;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.organizer.commons.events.model.OrganizerChangedEvent;
import seedu.organizer.testutil.OrganizerBuilder;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub";
    private static final String RELATIVE_PATH = "./";

    private static final OrganizerChangedEvent EVENT_STUB = new OrganizerChangedEvent(new OrganizerBuilder().withTask
        (GROCERY).build());

    private static final int INITIAL_TOTAL_TASKS = 0;

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
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION, INITIAL_TOTAL_TASKS);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL,
            String.format(TOTAL_TASKS_STATUS, INITIAL_TOTAL_TASKS));

        // after organizer book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
            String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
            String.format(TOTAL_TASKS_STATUS, EVENT_STUB.data.getCurrentUserTaskList().size()));
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, the
     * sync status matches that of {@code expectedSyncStatus}, and the total tasks matches that of
     * {@code expectedTotalTasksStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus, String
            expectedTotalTasksStatus) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedTotalTasksStatus, statusBarFooterHandle.getTotalTasksStatus());
        guiRobot.pauseForHuman();
    }

}
