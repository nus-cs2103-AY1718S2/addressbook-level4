package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarFocusEvent;
import seedu.address.commons.events.ui.CalendarUnfocusEvent;

//@@author yuxiangSg
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel {

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private CalendarView calendarPage;

    public CalendarPanel(CalendarSource calendar) {
        calendarPage = new CalendarView();
        assignCalendar(calendar);
        configurCalendarPage();
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Configure the calendarView to fit the browser panel.
     */
    void configurCalendarPage() {
        calendarPage.setShowAddCalendarButton(false);
        calendarPage.setShowDeveloperConsole(false);
        calendarPage.setShowPageSwitcher(true);
        calendarPage.setShowPageToolBarControls(true);
        calendarPage.setShowPrintButton(false);
        calendarPage.setShowSearchField(false);
        calendarPage.setShowSearchResultsTray(false);
        calendarPage.setShowSourceTray(false);
        calendarPage.setShowToolBar(false);
        calendarPage.showMonthPage();


    }

    /**
     * Assign calendar to Calendar GUI
     */
    void assignCalendar(CalendarSource calendar) {
        calendarPage.getCalendarSources().setAll(calendar);
    }

    @Subscribe
    private void handleCalendarFocusEvent(CalendarFocusEvent event) {
        calendarPage.showDate(event.dateToLook);
    }

    @Subscribe
    private void handleCalendarUnFocusEvent(CalendarUnfocusEvent event) {
        calendarPage.showMonthPage();
    }

    public CalendarView getCalendarPage() {
        return calendarPage;
    }
}
