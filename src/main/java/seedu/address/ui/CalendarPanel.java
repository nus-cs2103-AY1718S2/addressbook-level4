package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel {

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private CalendarView calendarPage;

    public CalendarPanel(CalendarSource calendar) {
        calendarPage = new CalendarView();
        calendarPage.getCalendarSources().setAll(calendar);
        configurCalendarPage();
        //registerAsAnEventHandler(this);
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



    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    public CalendarView getCalendarPage() {
        return calendarPage;
    }
}
