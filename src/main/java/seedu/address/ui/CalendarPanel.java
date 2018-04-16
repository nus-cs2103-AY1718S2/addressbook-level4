package seedu.address.ui;

import static seedu.address.logic.parser.DateTimeParser.nattyDateAndTimeParser;

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

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;




//@@author fuadsahmawi
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";

    private CalendarView calendarView;

    private ObservableList<Reminder> reminderList;

    private ObservableList<Person> personList;

    public CalendarPanel(ObservableList<Reminder> reminderList, ObservableList<Person> personList) {
        super(FXML);

        this.reminderList = reminderList;
        this.personList = personList;

        calendarView = new CalendarView();
        setupCalendar();
        updateCalendar();
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewCalendarEvent(AddressBookChangedEvent event) {
        reminderList = event.data.getReminderList();
        personList = event.data.getPersonList();
        Platform.runLater(this::updateCalendar);
    }

    /**
     * Updates the Calendar with Reminders that are already added
     */
    private void updateCalendar() {
        setDateAndTime();
        CalendarSource myCalendarSource = new CalendarSource("Reminders and Meetups");
        Calendar calendarRDue = new Calendar("Reminders Already Due");
        Calendar calendarRNotDue = new Calendar("Reminders Not Due");
        Calendar calendarM = new Calendar("Meetups");
        calendarRDue.setStyle(Calendar.Style.getStyle(4));
        calendarRDue.setLookAheadDuration(Duration.ofDays(365));
        calendarRNotDue.setStyle(Calendar.Style.getStyle(1));
        calendarRNotDue.setLookAheadDuration(Duration.ofDays(365));
        calendarM.setStyle(Calendar.Style.getStyle(3));
        myCalendarSource.getCalendars().add(calendarRDue);
        myCalendarSource.getCalendars().add(calendarRNotDue);
        myCalendarSource.getCalendars().add(calendarM);
        for (Reminder reminder : reminderList) {
            LocalDateTime ldtstart = nattyDateAndTimeParser(reminder.getDateTime().toString()).get();
            LocalDateTime ldtend = nattyDateAndTimeParser(reminder.getEndDateTime().toString()).get();
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(ldtend)) {
                calendarRNotDue.addEntry(new Entry(
                        reminder.getReminderText().toString(), new Interval(ldtstart, ldtend)));
            } else {
                calendarRDue.addEntry(new Entry(reminder.getReminderText().toString(), new Interval(ldtstart, ldtend)));
            }
        }
        //@@author sham-sheer
        for (Person person : personList) {
            String meetDate = person.getMeetDate().toString();
            if (!meetDate.isEmpty()) {
                int day = Integer.parseInt(meetDate.substring(0,
                        2));
                int month = Integer.parseInt(meetDate.substring(3,
                        5));
                int year = Integer.parseInt(meetDate.substring(6,
                        10));
                calendarM.addEntry(new Entry("Meeting " + person.getName().toString(),
                        new Interval(LocalDate.of(year, month, day), LocalTime.of(12, 0),
                                LocalDate.of(year, month, day), LocalTime.of(13, 0))));
            }
        }
        calendarView.getCalendarSources().add(myCalendarSource);
    }

    //@@author fuadsahmawi
    private void setDateAndTime() {
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.getCalendarSources().clear();
    }

    private void setupCalendar() {
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showMonthPage();
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

}
