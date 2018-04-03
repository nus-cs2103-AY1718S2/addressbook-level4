package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.page.MonthPage;
import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowCalendarViewRequestEvent;
import seedu.address.model.appointment.AppointmentEntry;

/**
 * Panel containing the calendar view displaying patients' appointment.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    @FXML
    private MonthPage calendarView;

    private Calendar calendar;
    private CalendarSource calendarSource;

    public CalendarPanel() {
        super(FXML);
        calendarSource = new CalendarSource("calendar");
        calendar = new Calendar("calendar");
        calendar.setStyle(Calendar.Style.STYLE2);
        calendarSource.getCalendars().add(calendar);
        setConnections();
        registerAsAnEventHandler(this);
    }

    public CalendarPanel(ObservableList<AppointmentEntry> entries) {
        super(FXML);
        calendarSource = new CalendarSource("calendar");
        calendar = new Calendar("calendar");
        calendar.setReadOnly(true);
        calendar.setStyle(Calendar.Style.STYLE2);
        calendarSource.getCalendars().add(calendar);
        setConnections(entries);
        registerAsAnEventHandler(this);
    }

    private void setConnections() {

    }

    private void setConnections(ObservableList<AppointmentEntry> entries) {

        for (AppointmentEntry entry : entries) {
            calendar.addEntry(entry.getAppointmentEntry());
        }
        calendarView.getCalendarSources().add(calendarSource);

    }

    @Subscribe
    public void handleShowCalendar(ShowCalendarViewRequestEvent scvre) {
        setConnections(scvre.appointmentEntries);
    }
}
