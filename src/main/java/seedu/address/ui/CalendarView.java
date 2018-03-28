package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.model.event.CalendarEvent;

/**
 * WORK IN PROGRESS FOR EVENTS AND LOGGING
 */
public class CalendarView {
    private ArrayList<AnchorPaneNode> calendarMonth = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private ObservableList<CalendarEvent> eventList;
    private Logic logic;
    //private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    /**
     * Provides layout for the calendar month with anchor panes.
     */
    public CalendarView(Logic logic, ObservableList<CalendarEvent> eventList, YearMonth yearMonth) {
        this.logic = logic;
        this.eventList = eventList;
        currentYearMonth = yearMonth;

        // Creates the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(450, 300);

        // Create rows and columns of anchor panes for calendar
        calendarMonthSetup(calendar);

        // Days of a Week
        Text[] days = new Text[]{new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
            new Text("Wednesday"), new Text("Thursday"),
            new Text("Friday"), new Text("Saturday")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(450);
        Integer col = 0;

        for (Text day : days) {
            day.setFill(Color.WHITE);
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            AnchorPane.setBottomAnchor(day, 5.0);
            ap.getChildren().add(day);
            dayLabels.add(ap, col++, 0);
        }

        // Creates a title for the calendar
        calendarTitle = new Text();
        calendarTitle.setFill(Color.WHITE);

        // Buttons to navigate through months
        Button previousMonth = new Button("< Previous");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button("Next >");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(calendarTitle, new Insets(0, 20, 0, 20));
        titleBar.setAlignment(Pos.BASELINE_CENTER);

        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, null);

        // Creates the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        VBox.setMargin(titleBar, new Insets(0, 0, 15, 0));

    }

    /**
     * WORK IN PROGRESS FOR EVENTS
     */
    public void populateCalendar(YearMonth yearMonth, Index targetIndex) {
        // Gets the current date as reference
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);

        // Sets first day to be a Sunday
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        // Fills up calendar with day numbers
        for (AnchorPaneNode ap : calendarMonth) {
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
            }

            String dayValue = String.valueOf(calendarDate.getDayOfMonth());
            String monthValue = String.valueOf(calendarDate.getMonthValue());
            String yearValue = String.valueOf(calendarDate.getYear());

            boolean eventExist = false;

            if (targetIndex == null) {
                //eventExist checks here to see
                eventExist = false;
            } else {
                CalendarEvent e = eventList.get(targetIndex.getZeroBased());

                if (checkEventDay(e, dayValue)
                        && checkEventMonth(e, monthValue) && checkEventYear(e, yearValue)) {
                    eventExist = true;
                }

            }

            Text dateNumber = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            // Days from a different month shows up as a different colour
            if (calendarDate.getMonthValue() != yearMonth.getMonthValue()) {
                dateNumber.setFill(Color.GREY);
            } else {
                dateNumber.setFill(Color.WHITE);
            }
            ap.setDate(calendarDate);
            ap.setTopAnchor(dateNumber, 5.0);
            ap.setLeftAnchor(dateNumber, 5.0);

            ap.getChildren().add(dateNumber);
            calendarDate = calendarDate.plusDays(1);
        }

        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Provides layout for the calendar month with anchor panes.
     */
    private void calendarMonthSetup(GridPane calendar) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.getStyleClass().add("anchor");
                ap.setPrefSize(100, 100);
                calendar.add(ap, j, i);
                calendarMonth.add(ap);
            }
        }
    }

    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, null);
    }

    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, null);
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void setCurrentYearMonth(YearMonth currentYearMonth) {
        this.currentYearMonth = currentYearMonth;
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return calendarMonth;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.calendarMonth = allCalendarDays;
    }

    /**
     * Check whether the event Day matches the input dayValue
     * @param event
     * @param dayValue
     * @return
     */
    private boolean checkEventDay(CalendarEvent event, String dayValue) {
        if (dayValue.length() == 1) {
            return event.getDatetime().substring(0, 2).equals("0" + dayValue);
        } else {
            return event.getDatetime().substring(0, 2).equals(dayValue);
        }
    }

    /**
     * Check whether the event Day matches the input monthValue
     * @param event
     * @param monthValue
     * @return
     */
    private boolean checkEventMonth(CalendarEvent event, String monthValue) {
        if (monthValue.length() == 1) {
            return event.getDatetime().substring(3, 5).equals("0" + monthValue);
        } else {
            return event.getDatetime().substring(3, 5).equals(monthValue);
        }
    }

    /**
     * Check whether the event Day matches the input yearValue
     * @param event
     * @param yearValue
     * @return
     */
    private boolean checkEventYear(CalendarEvent event, String yearValue) {
        return event.getDatetime().substring(6, 10).equals(yearValue);
    }

    private String getFormatDate(String day, String month, String year) {
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        return day + "-" + month + "-" + year;
    }

}
