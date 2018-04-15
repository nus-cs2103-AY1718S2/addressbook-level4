package seedu.address.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.DeskBoardChangedEvent;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;


//@@author jasmoon
/**
 * Panel containing the calendar.
 * NOTE: Calendar getter methods in API returns unmodifiableObservableList.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private CalendarView calendarView;

    public CalendarPanel(ObservableList<Activity> activityList) {
        super(FXML);

        calendarView = new CalendarView();
        registerAsAnEventHandler(this);
        configureCalendar();
        syncCalendarWithActivities(activityList);
    }

    /**
     * Configures the contents to display of the calendar.
     */
    private void configureCalendar()   {
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showMonthPage();
        calendarView.prefWidth(calendarView.computeAreaInScreen());
        calendarView.prefHeight(calendarView.computeAreaInScreen());
    }

    /**
     * Syncs the calender with UniqueActivityList at start.
     * @param activityList
     */
    private void syncCalendarWithActivities(ObservableList<Activity> activityList)  {
        resetCalendar();
        CalendarSource activityCalendarSource = new CalendarSource("Activity Calendar");
        Calendar taskCalendar = new Calendar("Task Calendar");
        taskCalendar.setLookAheadDuration(Duration.ofDays(365));
        taskCalendar.setStyle(Calendar.Style.getStyle(1));
        activityCalendarSource.getCalendars().add(taskCalendar);

        for (Activity activity: activityList)    {
            if (activity.getActivityType().equals("TASK")) {
                Task task = (Task) activity;
                LocalDateTime dueDateTime = task.getDueDateTime().getLocalDateTime();
                Entry entry = new Entry(task.getName().fullName);
                entry.setInterval(dueDateTime);
                taskCalendar.addEntry(entry);
            }
        }

        Calendar eventCalendar = new Calendar("Event Calendar");
        eventCalendar.setStyle(Calendar.Style.getStyle(4));
        eventCalendar.setLookAheadDuration(Duration.ofDays(365));

        activityCalendarSource.getCalendars().add(eventCalendar);

        for (Activity activity: activityList)    {
            if (activity.getActivityType().equals("EVENT")) {
                Event event = (Event) activity;
                LocalDateTime startDateTime = event.getStartDateTime().getLocalDateTime();
                LocalDateTime endDateTime = event.getEndDateTime().getLocalDateTime();
                Entry entry = new Entry(event.getName().fullName, new Interval(startDateTime, endDateTime));
                entry.setLocation(event.getLocation() == null ? null : event.getLocation().value);
                eventCalendar.addEntry(entry);
            }
        }
        calendarView.getCalendarSources().add(activityCalendarSource);
    }

    @Subscribe
    private void handleDeskBoardChangedEvent(DeskBoardChangedEvent event) {
        syncCalendarWithActivities(event.data.getActivityList());
    }

    private void resetCalendar()    {
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.getCalendarSources().clear();
    }

    public CalendarView getRoot()   {
        return this.calendarView;
    }
}
