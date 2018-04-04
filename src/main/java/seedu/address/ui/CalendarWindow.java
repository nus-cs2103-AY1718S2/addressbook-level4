package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
//import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;


//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.stage.Stage;

//import java.time.Duration;

//import java.time.LocalDateTime;

//import java.time.ZoneId;


//import seedu.address.MainApp;
//import seedu.address.commons.core.LogsCenter;
//import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;



//@@author Robert-Peng
/**
 * Implement CalendarView from CalendarFX to show appointments
 */
public class CalendarWindow extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "CalendarPanel.fxml";

    private  ObservableList<Appointment> appointmentList;
    private Calendar calendar;

    @FXML
    private CalendarView calendarView;

    /**
     *
     * @param OwnerList
     */
    public CalendarWindow(ObservableList<Appointment> appointmentList) {
        super(DEFAULT_PAGE);

        this.appointmentList = appointmentList;

        calendarView = new CalendarView();

        setTime();
        setCalendar();
        disableViews();
        registerAsAnEventHandler(this);
    }

    private void setTime() {
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
    }

    /**
     * Creates a new a calendar
     */
    private void setCalendar() {
        setTime();
        calendarView.getCalendarSources().clear();
        CalendarSource calendarSource = new CalendarSource("Appointments");
        int styleNumber = 0;
        int appointmentCounter = 0;
        for (Appointment appointment : appointmentList) {
            Calendar calendar = createCalendar(styleNumber, appointment);
            calendarSource.getCalendars().add(calendar);

            LocalDateTime ldt = appointment.getDateTime();
            Entry entry = new Entry (++appointmentCounter + ". " + appointment.getPetPatientName().toString());
            entry.setInterval(new Interval(ldt, ldt.plusMinutes(30)));

            styleNumber++;
            styleNumber = styleNumber % 7;

            calendar.addEntry(entry);

        }
        calendarView.getCalendarSources().add(calendarSource);
    }

    /**
     *
     * @param styleNumber
     * @param appointment
     * @return a calendar with given info and corresponding style
     */
    private Calendar createCalendar(int styleNumber, Appointment appointment) {
        Calendar calendar = new Calendar(appointment.getPetPatientName().toString());
        calendar.setStyle(Calendar.Style.getStyle(styleNumber));
        calendar.setLookAheadDuration(Duration.ofDays(365));
        calendar.setLookBackDuration(Duration.ofDays(365));
        return calendar;
    }

    /**
     * close unwanted UI components
     */

    private void disableViews() {
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.showDayPage();
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }



}


