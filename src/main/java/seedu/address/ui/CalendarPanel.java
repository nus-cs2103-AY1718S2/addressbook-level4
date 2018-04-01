package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.page.PageBase;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ZoomInEvent;
import seedu.address.commons.events.logic.ZoomOutEvent;
import seedu.address.commons.events.model.AppointmentDeletedEvent;
import seedu.address.commons.events.model.NewAppointmentAddedEvent;
import seedu.address.model.appointment.Appointment;

//@@author jlks96
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<CalendarView> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private static final String ENTRY_TITLE = "Appt: ";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @javafx.fxml.FXML
    private CalendarView calendarView;
    private Calendar calendar;
    private PageBase pageBase;

    public CalendarPanel(ObservableList<Appointment> appointments) {
        super(FXML);
        initializeCalendar();
        setUpCalendarView();
        loadEntries(appointments);
        updateTime();
        registerAsAnEventHandler(this);
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public PageBase getPageBase() {
        return pageBase;
    }

    /**
     * Initializes the calendar
     */
    private void initializeCalendar() {
        calendar = new Calendar("Appointments");
    }

    /**
     * Sets up the calendar view
     */
    private void setUpCalendarView() {
        CalendarSource calendarSource = new CalendarSource("My Calendar");
        calendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(calendarSource);

        calendarView.setRequestedTime(LocalTime.now());
        calendarView.showMonthPage();
        pageBase = calendarView.getSelectedPage();
    }

    /**
     * Loads {@code appointments} into the calendar
     */
    private void loadEntries(ObservableList<Appointment> appointments) {
        appointments.stream().forEach(this::loadEntry);
    }

    /**
     * Creates an entry with the {@code appointment} details and loads it into the calendar
     */
    private void loadEntry(Appointment appointment) {
        String dateString = appointment.getDate().date;
        String startTimeString = appointment.getStartTime().time;
        String endTimeString = appointment.getEndTime().time;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime startDateTime = LocalDateTime.parse(dateString + " " + startTimeString, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(dateString + " " + endTimeString, formatter);

        Entry entry = new Entry();
        entry.setInterval(startDateTime, endDateTime);
        entry.setLocation(appointment.getLocation().value);
        entry.setTitle(ENTRY_TITLE + appointment.getName() + " " + appointment.getLocation());
        entry.setCalendar(calendar);
    }

    /**
     * Handles the event where a new appointment is added by loading the appointment into the calendar
     * @param event contains the newly added appointment
     */
    @Subscribe
    private void handleNewAppointmentAddedEvent(NewAppointmentAddedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEntry(event.getAppointmentAdded());
    }

    /**
     * Handles the event where an appointment is deleted by loading the updated appointments list into the calendar
     * @param event contains the updated appointments list
     */
    @Subscribe
    private void handleAppointmentDeletedEvent(AppointmentDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendar.clear();
        loadEntries(event.getUpdatedAppointments());
    }

    /**
     * Handles the event where the user is trying to zoom in on the calendar
     */
    @Subscribe
    private void handleZoomInEvent(ZoomInEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        zoomIn();
    }

    /**
     * Zooms in on the calendar if possible
     */
    private void zoomIn() {
        if (pageBase.equals(calendarView.getYearPage())) {
            calendarView.showMonthPage();
        } else if (pageBase.equals(calendarView.getMonthPage())) {
            calendarView.showWeekPage();
        } else if (pageBase.equals(calendarView.getWeekPage())) {
            calendarView.showDayPage();
        }
        pageBase = calendarView.getSelectedPage();
    }

    /**
     * Handles the event where the user is trying to zoom out on the calendar
     */
    @Subscribe
    private void handleZoomOutEvent(ZoomOutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        zoomOut();
    }

    /**
     * Zooms out on the calendar if possible
     */
    private void zoomOut() {
        if (pageBase.equals(calendarView.getDayPage())) {
            calendarView.showWeekPage();
        } else if (pageBase.equals(calendarView.getWeekPage())) {
            calendarView.showMonthPage();
        } else if (pageBase.equals(calendarView.getMonthPage())) {
            calendarView.showYearPage();
        }
        pageBase = calendarView.getSelectedPage();
    }

    /**
     * Update the current date and time shown in the calendar as a thread in the background
     * Adapted from http://dlsc.com/wp-content/html/calendarfx/manual.html
     */
    private void updateTime() {
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }
}
