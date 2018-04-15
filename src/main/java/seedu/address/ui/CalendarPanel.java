package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.DetailedWeekView;
import com.calendarfx.view.page.PageBase;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ScheduleChangedEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.Student;

//@@author demitycho
/**
 * The Calendar Panel using the InfoPanel of Codeducator.
 */
public class CalendarPanel extends UiPart<CalendarView> {

    private static final String FXML = "CalendarPanel.fxml";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private static final String STRING_ENTRY_TITLE = "%d Lesson: %s";

    private static final Time TIME_DEFAULT_START = new Time("07:00");
    private static final Time TIME_DEFAULT_END = new Time("22:00");
    private static final LocalTime TEMPORAL_TIME_DEFAULT_START = LocalTime.of(00, 30);
    private static final LocalTime TEMPORAL_TIME_DEFAULT_END = LocalTime.of(23, 59);

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @javafx.fxml.FXML
    private CalendarView calendarView;
    private Calendar calendar;
    private PageBase pageBase;

    private Integer lessonDisplayIndex;

    private ReadOnlyAddressBook addressBook;

    private ReadOnlySchedule schedule;

    public CalendarPanel(ReadOnlySchedule readOnlySchedule, ReadOnlyAddressBook addressBook) {
        super(FXML);
        initializeCalendar();
        setUpCalendarView(readOnlySchedule);
        loadEntries(readOnlySchedule, addressBook);
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
        calendar = new Calendar("Lessons");
    }

    /**
     * Sets up the calendar view
     * Uses many methods to modify the default CalendarView
     */
    private void setUpCalendarView(ReadOnlySchedule schedule) {
        CalendarSource calendarSource = new CalendarSource("My Calendar");
        calendarSource.getCalendars().addAll(calendar);

        calendarView.getCalendarSources().addAll(calendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.showWeekPage();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSourceTray(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowAddCalendarButton(false);
        calendarView.showWeekPage();
        calendarView.setShowDeveloperConsole(true);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowToolBar(false);

        calendarView.setOnMouseClicked(null);
        calendarView.setDisable(true);
        calendar.setReadOnly(true);

        calendarView.getWeekPage().setShowNavigation(false);
        calendarView.getWeekPage().setShowDate(false);
        calendarView.weekFieldsProperty().setValue(WeekFields.of(Locale.FRANCE)); // Start week from Monday

        DetailedWeekView detailedWeekView = calendarView.getWeekPage().getDetailedWeekView();
        detailedWeekView.setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        detailedWeekView.setHoursLayoutStrategy(DayViewBase.HoursLayoutStrategy.FIXED_HOUR_COUNT);
        resizeCalendar(calendarView, schedule);
    }

    /**
     * Loads {@code lessons} into the calendar
     */
    private void loadEntries(ReadOnlySchedule readOnlySchedule, ReadOnlyAddressBook addressBook) {
        lessonDisplayIndex = 1;
        this.addressBook = addressBook;
        this.schedule = readOnlySchedule;
        readOnlySchedule.getSchedule().stream().forEach(this::loadEntry);
        resizeCalendar(calendarView, readOnlySchedule);
    }

    /**
     * Resizes the calendar view if any time slots are outside of the default time range
     * @param calendarView
     * @param schedule
     */
    private void resizeCalendar(CalendarView calendarView, ReadOnlySchedule schedule) {
        DetailedWeekView detailedWeekView = calendarView.getWeekPage().getDetailedWeekView();
        Time start = TIME_DEFAULT_START.compareTo(schedule.getEarliestStartTime()) < 0
                ? TIME_DEFAULT_START : schedule.getEarliestStartTime();
        Time end = TIME_DEFAULT_END.compareTo(schedule.getLatestEndTime()) > 0
                ? TIME_DEFAULT_END : schedule.getLatestEndTime();
        LocalTime startTime = LocalTime.parse(start.toString());
        LocalTime endTime = LocalTime.parse(end.toString());

        detailedWeekView.setVisibleHours((int) ChronoUnit.HOURS.between(startTime, endTime));
        calendarView.setStartTime(startTime);
        calendarView.setEndTime(endTime);
    }
    /**
     * Creates an entry with the {@code lesson} details and loads it into the calendar
     */
    private void loadEntry(Lesson lesson) {
        String dateString = lesson.getDay().toDateString();
        String startTimeString = lesson.getStartTime().toString();
        String endTimeString = lesson.getEndTime().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime startDateTime = LocalDateTime.parse(dateString + " " + startTimeString, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(dateString + " " + endTimeString, formatter);

        Student student = addressBook.findStudentByKey(lesson.getUniqueKey());
        Entry entry = new Entry();
        entry.setInterval(startDateTime, endDateTime);

        entry.setTitle(String.format(STRING_ENTRY_TITLE, lessonDisplayIndex++, student.getName()));
        entry.setCalendar(calendar);



    }


    /**
     * Handles the event where a lesson is deleted by loading the updated lessons list into the calendar
     * @param event contains the updated lessons list
     */
    @Subscribe
    private void handleScheduleChangedEvent(ScheduleChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendar.clear();
        loadEntries(event.getLessons(), event.getAddressBook());
    }

    //@@author demitycho-reused
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
