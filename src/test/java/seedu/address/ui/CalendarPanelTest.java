package seedu.address.ui;
//@@author SuxianAlicia

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_DATE_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_DAY_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_MONTH_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_NEXT_PAGE_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_PREVIOUS_PAGE_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_TODAY_EVENT;
import static seedu.address.testutil.TypicalBaseEvents.CHANGE_TO_WEEK_EVENT;
import static seedu.address.testutil.TypicalLocalDates.LEAP_YEAR_DATE;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.calendarfx.model.Calendar;

import guitests.guihandles.CenterPanelHandle;

/**
 * Contains integration tests (interaction with the CenterPanel) for {@code CalendarPanel}.
 */
public class CalendarPanelTest extends GuiUnitTest {

    private CenterPanelHandle centerPanelHandle;

    @Before
    public void setUp() {
        Calendar calendar = new Calendar();
        calendar.setReadOnly(true);
        calendar.setStyle(Calendar.Style.STYLE1);
        CenterPanel centerPanel = new CenterPanel(calendar);
        centerPanel.displayCalendarPanel();
        uiPartRule.setUiPart(centerPanel);

        centerPanelHandle = new CenterPanelHandle(getChildNode(centerPanel.getRoot(),
                CenterPanelHandle.CENTER_PANEL_ID));
    }

    @Test
    public void handleChangeCalendarViewRequestEvent() {
        centerPanelHandle.setUpCalendarPanelHandle();

        postNow(CHANGE_TO_MONTH_EVENT);
        guiRobot.pauseForHuman();
        assertEquals(MONTH_VIEW, centerPanelHandle.getCalendarCurrentView());

        postNow(CHANGE_TO_WEEK_EVENT);
        guiRobot.pauseForHuman();
        assertEquals(WEEK_VIEW, centerPanelHandle.getCalendarCurrentView());

        postNow(CHANGE_TO_DAY_EVENT);
        guiRobot.pauseForHuman();
        assertEquals(DAY_VIEW, centerPanelHandle.getCalendarCurrentView());
    }

    @Test
    public void handleChangeCalendarPageRequestEvent() {
        centerPanelHandle.setUpCalendarPanelHandle();

        LocalDate originalDate = centerPanelHandle.getCalendarCurrentDate();
        postNow(CHANGE_TO_NEXT_PAGE_EVENT);
        guiRobot.pauseForHuman();

        LocalDate expectedDate = originalDate.plusDays(1);
        assertEquals(expectedDate, centerPanelHandle.getCalendarCurrentDate());

        postNow(CHANGE_TO_TODAY_EVENT);
        guiRobot.pauseForHuman();

        expectedDate = centerPanelHandle.getCalendarTodayDate();
        assertEquals(expectedDate, centerPanelHandle.getCalendarCurrentDate());

        originalDate = centerPanelHandle.getCalendarCurrentDate();
        postNow(CHANGE_TO_PREVIOUS_PAGE_EVENT);
        guiRobot.pauseForHuman();

        expectedDate = originalDate.minusDays(1);
        assertEquals(expectedDate, centerPanelHandle.getCalendarCurrentDate());
    }

    @Test
    public void handleChangeCalendarDateRequestEvent() {
        centerPanelHandle.setUpCalendarPanelHandle();

        LocalDate previousDate = centerPanelHandle.getCalendarCurrentDate();
        postNow(CHANGE_DATE_EVENT);
        guiRobot.pauseForHuman();

        assertNotEquals(previousDate, LEAP_YEAR_DATE);
        assertEquals(LEAP_YEAR_DATE, centerPanelHandle.getCalendarCurrentDate());
    }
}
