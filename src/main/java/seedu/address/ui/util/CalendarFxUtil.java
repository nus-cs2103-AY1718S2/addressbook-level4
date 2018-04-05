package seedu.address.ui.util;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.DayPage;

/**
 * Contains methods to initialise Calendar such that unused functions are not displayed, and mouse events are not
 * listened to.
 */
public class CalendarFxUtil {

    /**
     * Returns modified CalendarView such that unnecessary buttons and features are not shown.
     */
    public static CalendarView returnModifiedCalendarView() {
        CalendarView calendarView = new CalendarView();

        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSourceTray(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowToolBar(false);

        calendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);

        //calendarView.getDayPage().setDisable(true);
        return calendarView;
    }
}
