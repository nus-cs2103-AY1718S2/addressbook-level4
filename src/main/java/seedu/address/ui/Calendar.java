package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

//@@author LeonidAgarth
/**
 * The Calendar of the App.
 * <p>
 * Adapted from javafx-calendar by SirGoose3432
 * URL: https://github.com/SirGoose3432/javafx-calendar
 */
public class Calendar extends UiPart<Region> {

    private static final String FXML = "Calendar.fxml";

    private ArrayList<CalendarDate> allCalendarDays = new ArrayList<>(42);
    private VBox calendarView;
    private Text calendarHeader;
    private YearMonth currentYearMonth;
    private ObservableList<Event> events;

    @FXML
    private ListView<CalendarDate> calendarListView;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public Calendar(ObservableList<Event> eventList) {
        this(YearMonth.now(), eventList);
    }

    public Calendar(YearMonth yearMonth, ObservableList<Event> eventList) {
        super(FXML);
        currentYearMonth = yearMonth;
        events = eventList;
        initCalendar();

        registerAsAnEventHandler(this);
    }

    /**
     * Create the calendarView for the calendar
     */
    private void initCalendar() {
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(1400, 600);
        calendar.setMaxWidth(1400);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                CalendarDate apn = new CalendarDate();
                calendar.add(apn.getBox(), j, i);
                allCalendarDays.add(apn);
            }
        }
        // Days of the week
        Text[] dayNames = new Text[]{new Text("Mon"), new Text("Tue"), new Text("Wed"),
            new Text("Thu"), new Text("Fri"), new Text("Sat"), new Text("Sun")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        dayLabels.setMaxWidth(1120);
        dayLabels.setGridLinesVisible(true);
        int col = 0;
        for (Text txt : dayNames) {
            txt.getStyleClass().add("dayName");
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 20);
            AnchorPane.setLeftAnchor(txt, 5.0);
            AnchorPane.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            if (txt.getText().equals("Sun") || txt.getText().equals("Sat")) {
                ap.getStyleClass().add("weekend");
            } else {
                ap.getStyleClass().add("weekday");
            }
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarHeader and navigation
        calendarHeader = new Text();
        calendarHeader.getStyleClass().add("yearMonth");
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, new Text("\t"), calendarHeader, new Text("\t"), nextMonth);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        fillCalendar(currentYearMonth);
        showEvents();
        // Create the calendar calendarView
        calendarView = new VBox(titleBar, dayLabels, calendar);
    }

    /**
     * Set the visible days on the calendar to the appropriate {@code yearMonth}
     */
    private void fillCalendar(YearMonth yearMonth) {
        // Get the month and year to display
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        //Roll the day back to MONDAY in order to fill up the whole calendar
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Fill the calendar
        for (CalendarDate date : allCalendarDays) {
            date.setDate(calendarDate, yearMonth);
            calendarDate = calendarDate.plusDays(1);
            date.setEventText("");
        }
        // Change the header of the calendar
        calendarHeader.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Show all events in eventList onto Calendar
     */
    private void showEvents() {
        for (Event e : events) {
            String[] dayMonthYear = e.getDate().split("/");
            int day = Integer.parseInt(dayMonthYear[0]);
            int month = Integer.parseInt(dayMonthYear[1]);
            int year = Integer.parseInt(dayMonthYear[2]);
            if (month != currentYearMonth.getMonthValue()) {
                continue;
            }
            LocalDate date = LocalDate.of(year, month, day);
            CalendarDate node = getDateNode(date);
            node.setEventText(e.getName());
        }
    }

    /**
     * Move back 1 month, then refill the calendar.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        fillCalendar(currentYearMonth);
        showEvents();
    }

    /**
     * Move forward 1 month, then refill the calendar.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        fillCalendar(currentYearMonth);
        showEvents();
    }

    /**
     * Return the Calendar to view in application
     */
    public VBox getCalendarView() {
        return calendarView;
    }

    /**
     * Return the header of the Calendar
     */
    public Text getCalendarHeader() {
        return calendarHeader;
    }

    /**
     * Return the list of all visible day in the current month
     */
    public ArrayList<CalendarDate> getAllCalendarDays() {
        return allCalendarDays;
    }

    /**
     * Set all currently visible days to {@code allCalendarDays}
     */
    public void setAllCalendarDays(ArrayList<CalendarDate> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

    public CalendarDate getDateNode(LocalDate date) {
        LocalDate firstDay = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonthValue(), 1);
        int firstDayIndex = firstDay.getDayOfWeek().getValue() - 1;
        int gap = date.getDayOfMonth() - firstDay.getDayOfMonth();
        return allCalendarDays.get(firstDayIndex + gap);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Calendar)) {
            return false;
        }

        // state check
        Calendar theOther = (Calendar) other;
        return calendarHeader.getText().equals(theOther.getCalendarHeader().getText())
                && allCalendarDays.equals(theOther.getAllCalendarDays());
    }
}
