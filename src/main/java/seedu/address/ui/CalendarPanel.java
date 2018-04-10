package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;

import com.google.common.eventbus.Subscribe;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.CalendarRemoveActivityEvent;
import seedu.address.commons.events.model.DeskBoardChangedEvent;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;

//@@author jasmoon
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

    private void configureCalendar()   {
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showMonthPage();
    }


    private void syncCalendarWithActivities(ObservableList<Activity> activityList)  {
        CalendarSource activityCS = new CalendarSource("Activity Calendar");
        Calendar taskCalendar = new Calendar("Task Calendar");
        taskCalendar.setStyle(Calendar.Style.getStyle(1));
        activityCS.getCalendars().add(taskCalendar);

        for(Activity activity: activityList)    {
            if(activity.getActivityType().equals("TASK")) {
                Task task = (Task) activity;
                LocalDateTime dueDateTime = task.getDueDateTime().getLocalDateTime();
                Entry entry = new Entry(task.getName().fullName);
                entry.setInterval(dueDateTime);
                taskCalendar.addEntry(entry);
            }
        }

        Calendar eventCalendar = new Calendar("Event Calendar");
        eventCalendar.setStyle(Calendar.Style.getStyle(4));
        activityCS.getCalendars().add(eventCalendar);

        for(Activity activity: activityList)    {
            if(activity.getActivityType().equals("EVENT")) {
                Event event = (Event) activity;
                LocalDateTime startDateTime = event.getStartDateTime().getLocalDateTime();
                LocalDateTime endDateTime = event.getEndDateTime().getLocalDateTime();
                Entry entry = new Entry(event.getName().fullName, new Interval(startDateTime, endDateTime));
                entry.setLocation(event.getLocation().value);
                eventCalendar.addEntry(entry);
            }
        }
        calendarView.getCalendarSources().add(activityCS);
    }

    @Subscribe
    private void handleNewCalendarChangedEvent(DeskBoardChangedEvent event) {
        syncCalendarWithActivities(event.data.getActivityList());
    }

    @Subscribe
    private void handleCalendarRemoveActivityEvent(CalendarRemoveActivityEvent event)   {
        Activity activityToRemove = event.activityToRemove;
        if(activityToRemove.getActivityType().equals("TASK")) {
            Task taskToRemove = (Task) activityToRemove;
            List<Entry<?>> taskEntryList = calendarView.getCalendarSources().get(0).getCalendars().get(0).
                    findEntries(taskToRemove.getName().fullName);

            Entry<?> entryToRemove = taskEntryList.get(0);
            if(taskEntryList.size()>1)  {
                LocalDate taskEntryDueDate = taskToRemove.getDueDateTime().getLocalDateTime().toLocalDate();
                Map<LocalDate, List<Entry<?>>> mapEntry = calendarView.getCalendarSources().get(0).getCalendars().get(0).
                        findEntries(taskEntryDueDate, taskEntryDueDate, ZoneId.systemDefault());
                entryToRemove = mapEntry.get(taskEntryDueDate).get(0);
            }
            calendarView.getCalendarSources().get(0).getCalendars().get(0)
                    .removeEntry(entryToRemove);

        } else {

        }
    }

    private void calendarRemoveActivityEvent(Activity activity) {}

    public CalendarView getRoot()   {
        return this.calendarView;
    }
}
