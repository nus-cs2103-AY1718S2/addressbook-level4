package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import javafx.application.Platform;
import seedu.address.commons.core.LogsCenter;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<CalendarView> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @javafx.fxml.FXML
    private CalendarView calendarView;

    public CalendarPanel() {
        super(FXML);
        loadCalendar();
        updateTime();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads calendar into the calendarView
     */
    private void loadCalendar() {

        Calendar appointments = new Calendar("Appointments");

        appointments.setStyle(Calendar.Style.STYLE3);

        CalendarSource calendarSource = new CalendarSource("My Calendar");
        calendarSource.getCalendars().addAll(appointments);

        calendarView.getCalendarSources().addAll(calendarSource);

        calendarView.setRequestedTime(LocalTime.now());

    }

    /**
     * Update the current date and time shown in the calendar
     * Derived from http://dlsc.com/wp-content/html/calendarfx/manual.html
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
            };
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }


}
