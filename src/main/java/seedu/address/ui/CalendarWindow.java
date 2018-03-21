package seedu.address.ui;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;



/**
 *
 */
public class CalendarWindow extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "CalendarPanel.fxml";

    private ObservableList<Person> OwnerList;
    private Calendar calendar;

    @FXML
    private CalendarView calendarView;

    public CalendarWindow(ObservableList<Person> OwnerList) {
        super(DEFAULT_PAGE);
        this.OwnerList = OwnerList;
        calendarView = new CalendarView();

        CalendarSource newCalendarSource = new CalendarSource("My Calendars");

        root = new BorderPane();
        root.setCenter(agenda);
    }

    public BorderPane getPane() {
        return this.root;
    }
}
