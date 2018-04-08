package seedu.organizer.ui;

import static org.junit.Assert.assertEquals;
import static seedu.organizer.testutil.EventsUtil.postNow;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.ui.StatusBarFooter.CURRENT_USER_STATUS_INITIAL;
import static seedu.organizer.ui.StatusBarFooter.CURRENT_USER_STATUS_UPDATED;
import static seedu.organizer.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.organizer.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

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
import seedu.organizer.model.Organizer;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;
import seedu.organizer.testutil.OrganizerBuilder;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub";
    private static final String RELATIVE_PATH = "./";

    private static Organizer expectedOrganizer;
    private static OrganizerChangedEvent eventStub;

    private static final Clock originalClock = StatusBarFooter.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeClass
    public static void setUpBeforeClass() {
        // inject fixed clock
        StatusBarFooter.setClock(injectedClock);

        //Set up eventStub
        expectedOrganizer = new OrganizerBuilder().build();
        try {
            expectedOrganizer.addUser(ADMIN_USER);
            expectedOrganizer.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }
        eventStub = new OrganizerChangedEvent(expectedOrganizer);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        StatusBarFooter.setClock(originalClock);
    }

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL,
            CURRENT_USER_STATUS_INITIAL);

        // after organizer is updated
        postNow(eventStub);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
            String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
            String.format(CURRENT_USER_STATUS_UPDATED, eventStub.data.getCurrentLoggedInUser().username));
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, the
     * sync status matches that of {@code expectedSyncStatus}, and the current user matches that of
     * {@code expectedCurrentUsertatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus, String
            expectedCurrentUserStatus) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedCurrentUserStatus, statusBarFooterHandle.getCurrentUserStatus());
        guiRobot.pauseForHuman();
    }

}
