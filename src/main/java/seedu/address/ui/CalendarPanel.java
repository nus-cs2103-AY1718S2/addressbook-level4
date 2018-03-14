package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.page.PageBase;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel {

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private PageBase calendarPage;

    public CalendarPanel(CalendarSource calendar) {
        calendarPage = new MonthPage();
        calendarPage.getCalendarSources().setAll(calendar);



        //registerAsAnEventHandler(this);
    }



    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    public PageBase getCalendarPage() {
        return calendarPage;
    }
}
