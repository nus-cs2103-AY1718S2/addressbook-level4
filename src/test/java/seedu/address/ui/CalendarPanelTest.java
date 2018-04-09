package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersonsAndAppointments.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import com.calendarfx.view.page.PageBase;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.CalendarGoBackwardEvent;
import seedu.address.commons.events.logic.CalendarGoForwardEvent;
import seedu.address.commons.events.logic.ZoomInEvent;
import seedu.address.commons.events.logic.ZoomOutEvent;

public class CalendarPanelTest extends GuiUnitTest {
    private CalendarPanel calendarPanel;

    @Before
    public void setUp() {
        guiRobot.interact(() -> calendarPanel = new CalendarPanel(getTypicalAddressBook().getAppointmentList()));
    }

    @Test
    public void zoomInAndOut() throws Exception {

        //starts with Month Page
        assertEquals(calendarPanel.getCalendarView().getMonthPage(), calendarPanel.getPageBase());

        //zoom in to Week Page
        raiseZoomInEvent();
        assertEquals(calendarPanel.getCalendarView().getWeekPage(), calendarPanel.getPageBase());

        //zoom in to Day Page
        raiseZoomInEvent();
        assertEquals(calendarPanel.getCalendarView().getDayPage(), calendarPanel.getPageBase());

        //can't zoom in anymore, stays at Day Page
        raiseZoomInEvent();
        assertEquals(calendarPanel.getCalendarView().getDayPage(), calendarPanel.getPageBase());

        //zoom out to Week Page
        raiseZoomOutEvent();
        assertEquals(calendarPanel.getCalendarView().getWeekPage(), calendarPanel.getPageBase());

        //zoom out to Month Page
        raiseZoomOutEvent();
        assertEquals(calendarPanel.getCalendarView().getMonthPage(), calendarPanel.getPageBase());

        //zoom out to Year Page
        raiseZoomOutEvent();
        assertEquals(calendarPanel.getCalendarView().getYearPage(), calendarPanel.getPageBase());

        //can't zoom out anymore, stays at Year Page
        raiseZoomOutEvent();
        assertEquals(calendarPanel.getCalendarView().getYearPage(), calendarPanel.getPageBase());

        //zoom in to Month Page
        raiseZoomInEvent();
        assertEquals(calendarPanel.getCalendarView().getMonthPage(), calendarPanel.getPageBase());
    }

    @Test
    public void handleCalendarGoBackwardEvent_valid_success() {
        calendarPanel.getPageBase().goBack();
        PageBase pageBase = calendarPanel.getPageBase();
        calendarPanel.getPageBase().goForward();
        raiseCalendarGoBackwardEvent();
        assertEquals(pageBase, calendarPanel.getPageBase());
    }

    @Test
    public void handleCalendarGoForwardEvent_valid_success() {
        calendarPanel.getPageBase().goForward();
        PageBase pageBase = calendarPanel.getPageBase();
        calendarPanel.getPageBase().goBack();
        raiseCalendarGoForwardEvent();
        assertEquals(pageBase, calendarPanel.getPageBase());
    }

    /**
     * Raises a {@code ZoomInEvent}
     */
    private void raiseZoomInEvent() {
        EventsCenter.getInstance().post(new ZoomInEvent());
    }

    /**
     * Raises a {@code ZoomOutEvent}
     */
    private void raiseZoomOutEvent() {
        EventsCenter.getInstance().post(new ZoomOutEvent());
    }

    /**
     * Raises a {@code CalendarGoBackwardEvent}
     */
    private void raiseCalendarGoBackwardEvent() {
        EventsCenter.getInstance().post(new CalendarGoBackwardEvent());
    }

    /**
     * Raises a {@code CalendarGoForwardEvent}
     */
    private void raiseCalendarGoForwardEvent() {
        EventsCenter.getInstance().post(new CalendarGoForwardEvent());
    }

}
