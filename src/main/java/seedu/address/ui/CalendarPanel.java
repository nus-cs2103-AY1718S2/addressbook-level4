package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowDateRequestEvent;
import seedu.address.commons.events.ui.ShowDateTimeRequestEvent;
import seedu.address.commons.events.ui.ShowMonthRequestEvent;
import seedu.address.commons.events.ui.ShowWeekRequestEvent;
import seedu.address.commons.events.ui.ShowYearRequestEvent;
import seedu.address.model.appointment.Appointment;

//@@author trafalgarandre
/**
 * The CalendarPanel of the App
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private CalendarSource source = new CalendarSource("My Calendars");
    private Calendar calendar = new Calendar("My Calendar");
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    @FXML
    private CalendarView calendarView;



    public CalendarPanel(List<Appointment> appointmentsList) {
        super(FXML);
        calendar.setStyle(Calendar.Style.STYLE1);
        addAppointments(appointmentsList);
        source.getCalendars().add(calendar);
        setUpCalendarView();
        startTimeThread();
        registerAsAnEventHandler(this);
    }

    /**
     * Add appointment to Calendar
     * @param appointments
     */
    private void addAppointments(List<Appointment> appointments) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Appointment appointment: appointments) {
            Entry entry = new Entry();
            entry.setCalendar(calendar);
            LocalDateTime start = LocalDateTime.parse(appointment.getStartDateTime().startDateTime, formatter);
            LocalDateTime end = LocalDateTime.parse(appointment.getEndDateTime().endDateTime, formatter);
            entry.setInterval(start, end);
            entry.setTitle(appointment.getTitle().title);
            calendar.addEntry(entry);
        }
    }

    /**
     * Set up calendar View
     */
    public void setUpCalendarView() {
        calendarView.getCalendarSources().addAll(source);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setTransitionsEnabled(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowToolBar(false);
        calendarView.setShowSourceTray(false);
        calendarView.showMonthPage();
    }

    /**
     * Start time thread
     */
    private void startTimeThread() {
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

    @Subscribe
    private void handleShowDateRequestEvent(ShowDateRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetDate == null) {
            calendarView.showDayPage();
        } else {
            calendarView.showDate(event.targetDate);
        }
    }

    @Subscribe
    private void handleShowDateTimeRequestEvent(ShowDateTimeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendarView.showDateTime(event.targetDateTime);
    }

    @Subscribe
    private void handleShowWeekRequestEvent(ShowWeekRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYear == null) {
            calendarView.showWeekPage();
        } else {
            calendarView.showWeek(event.targetYear, event.targetWeek);
        }
    }

    @Subscribe
    private void handleShowMonthRequestEvent(ShowMonthRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYearMonth == null) {
            calendarView.showMonthPage();
        } else {
            calendarView.showYearMonth(event.targetYearMonth);
        }
    }

    @Subscribe
    private void handleShowYearRequestEvent(ShowYearRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.targetYear == null) {
            calendarView.showYearPage();
        } else {
            calendarView.showYear(event.targetYear);
        }
    }
}
