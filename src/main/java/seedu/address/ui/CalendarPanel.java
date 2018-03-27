package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * The CalendarPanel of the App
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private CalendarSource source;

    @FXML
    private CalendarView calendarView;

    public CalendarPanel() {
        super(FXML);
        setCalendarSource();
        addCalendars(new Calendar("Birthdays"));
        startTimeThread();
        registerAsAnEventHandler(this);
    }

    public void setUpCalendarView() {
        calendarView.getCalendarSources().addAll(source);
        calendarView.setRequestedTime(LocalTime.now());
    }

    public void setCalendarSource() {
        source = new CalendarSource("My Calendars");
    }

    /**
     * Add calendar to calendarSource
     * @param calendar
     */
    public void addCalendars(Calendar... calendar) {
        calendar[0].setStyle(Calendar.Style.STYLE1);
        source.getCalendars().addAll(calendar);
    }

    /**
     * Set update Time for Time Thread
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

}
