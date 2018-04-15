package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarWindowHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.ShowCalendarEvent;

//@@author wayneong95

public class CalendarWindowTest extends GuiUnitTest {
    private ShowCalendarEvent showCalendarEventstub;

    private CalendarWindow calendarWindow;
    private CalendarWindowHandle calendarWindowHandle;

    @Before
    public void setUp() {
        showCalendarEventstub = new ShowCalendarEvent();
        guiRobot.interact(() -> calendarWindow = new CalendarWindow());
        uiPartRule.setUiPart(calendarWindow);

        calendarWindowHandle = new CalendarWindowHandle(calendarWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        // updated default window
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertFalse(expectedDefaultPageUrl.equals(calendarWindowHandle.getLoadedUrl()));

        /*Does not work on Travis. Use for local testing.
        // calendar window
        postNow(showCalendarEventstub);
        URL expectedCalendarWindow = new URL(CalendarWindow.CALENDAR_PAGE_URL);
        waitUntilCalendarLoaded(calendarWindowHandle);
        assertEquals(expectedCalendarWindow, calendarWindowHandle.getLoadedUrl());
        */
    }
}
