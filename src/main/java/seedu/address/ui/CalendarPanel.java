package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayCalendarRequestEvent;
import seedu.address.model.event.CalendarEvent;

/**
 * Calendar Panel displaying calendar in Month-View Format.
 * The code is adapted from: https://github.com/sedj601/CalendarFx
 */
public class CalendarPanel extends UiPart<Region> {


    public static final String DATE_VALIDATION_FORMAT = "dd MM yyyy";
    public static final String DEFAULT_TIME = "00:00";
    public static final LocalTime DEFAULT_TIME_AT_CALENDAR = LocalTime.parse(DEFAULT_TIME);

    private static final String FXML = "CalendarPanel.fxml";
    private static final String MONTH = "Month";

    private static ObservableList<CalendarEvent> eventList;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label month;

    @FXML
    private Label year;

    @FXML
    private GridPane daysOfMonth;

    public CalendarPanel(ObservableList<CalendarEvent> calendarEventsList) {
        super(FXML);

        initialiseCalendarEventList(calendarEventsList);
        initialiseCalendar(LocalDateTime.now());
        registerAsAnEventHandler(this);
    }

    private void initialiseCalendarEventList(ObservableList<CalendarEvent> calendarEventsList) {
        eventList = calendarEventsList;
    }

    private void initialiseCalendar(LocalDateTime givenLdt) {
        LocalDateTime currentLdt = givenLdt;
        loadCurrentMonth(currentLdt);

    }

    /**
     * Sets Month and Year Labels to display Month and Year corresponding to given {@code LocalDateTime}.
     * Loads the number of daysOfMonth and any events set in the given month of the year.
     */
    private void loadCurrentMonth(LocalDateTime givenLdt) {

        if (daysOfMonth.getChildren().size() > 0) {
            daysOfMonth.getChildren().clear();
        }

        month.setText(givenLdt.getMonth().toString());
        year.setText(Integer.toString(givenLdt.getYear()));

        setDaysOfWeek();

        //The following sets the date numbers in gridpane for the current Month and Year.
        LocalDateTime ldtIterator = givenLdt.minusDays(givenLdt.getDayOfMonth() - 1);
        ldtIterator.format(DateTimeFormatter.ISO_DATE);

        int control = getColumn(ldtIterator);
        int control2 = 0;
        int i = 0;
        while (ldtIterator.getMonth() == givenLdt.getMonth()) {
            if (i == 0 || i == 1 && control2 <= control) {
                i = 1;
                control2++;
            } else {
                i = ((control2 - (control + 1)) / 7) + 2;
                control2++;
            }

            Label tempLabel = new Label(Integer.toString(ldtIterator.getDayOfMonth()));

            daysOfMonth.add(createCell(tempLabel, ldtIterator), ldtIterator.getDayOfWeek().getValue() - 1, i);

            ldtIterator = ldtIterator.plusDays(1);
        }
    }

    /**
     * Sets first row of daysOfMonth to be the 7 days of the week, starting from Monday.
     */
    private void setDaysOfWeek() {

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(days[i]);
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            daysOfMonth.add(dayLabel, i, 0);
        }
    }

    /**
     * Returns the starting column number in GridPane based on givenLdt.
     */
    private int getColumn (LocalDateTime givenLdt) {

        int i = 0;
        while (givenLdt.getDayOfWeek() != DayOfWeek.SUNDAY) {
            i++;
            givenLdt = givenLdt.plusDays(1);
        }

        return i;
    }

    /**
     * Creates a date cell for the given date.
     */
    private BorderPane createCell(Label label, LocalDateTime ldt) {

        BorderPane cell = new BorderPane();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(loadEventsForDate(ldt));
        BorderPane.setAlignment(vbox, Pos.CENTER);
        cell.setCenter(vbox);

        BorderPane.setAlignment(label, Pos.TOP_RIGHT);
        cell.setTop(label);
        cell.getStyleClass().add("cell-cal-label");

        return cell;
    }


    /**
     * Returns List of Label, each Label containing Event title of Events occurring during ldt time.
     */
    private List<Label> loadEventsForDate(LocalDateTime ldt) {
        List<Label> labelList = new ArrayList<>();

        for (CalendarEvent ce: eventList) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_VALIDATION_FORMAT);
            LocalDate startDateWithoutTime = LocalDate.parse(ce.getStartDate().toString() , format);
            LocalDate endDateWithoutTime = LocalDate.parse(ce.getEndDate().toString(), format);
            LocalDateTime startDate = LocalDateTime.of(startDateWithoutTime, DEFAULT_TIME_AT_CALENDAR);
            LocalDateTime endDate = LocalDateTime.of(endDateWithoutTime, DEFAULT_TIME_AT_CALENDAR);

            // Checks if ldt is within start date and end date of Event
            if (ldt.isAfter(startDate) || ldt.equals(startDate) && ldt.isBefore(endDate) || ldt.equals(endDate)) {
                String eventTitle = ce.getEventTitle().toString().substring(0, 15); //Set limit to characters displayed
                Label eventLabel = new Label(eventTitle);
                eventLabel.getStyleClass().add("cell-cal-event");
                eventLabel.setText(eventTitle);
                labelList.add(eventLabel);
            }

        }
        return labelList;
    }

    @Subscribe
    public void handleDisplayCalendarRequestEvent(DisplayCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

}
