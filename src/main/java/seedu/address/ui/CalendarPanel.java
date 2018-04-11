//@@author ongkuanyang
package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.appointment.Appointment;

/**
 * The Browser Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private CalendarView calendarView;

    private ObservableList<Appointment> appointmentList;

    private Calendar calendar;

    public CalendarPanel(ObservableList<Appointment> appointmentList) {
        super(FXML);
        registerAsAnEventHandler(this);

        calendarView.setShowAddCalendarButton(false);
        calendarView.showMonthPage();

        calendar = new Calendar("Appointments");
        calendar.setReadOnly(true);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(calendar);
        calendarView.getCalendarSources().addAll(myCalendarSource);

        this.appointmentList = appointmentList;
        syncAppointments();
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        calendarView = null;
    }

    /**
     * Synchronises the appointments and calendar entries
     */
    private void syncAppointments() {
        calendar.clear();
        for (Appointment appointment : appointmentList) {
            Entry<String> entry = new Entry(appointment.getName().name);
            entry.setInterval(appointment.getTime().time);
            entry.setLocation(appointment.getTime().time.format(DateTimeFormatter.ofPattern("VV")));
            calendar.addEntry(entry);
        }
    }

    @Subscribe
    private void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        syncAppointments();
    }
}
