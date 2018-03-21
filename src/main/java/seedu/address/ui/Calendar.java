package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import seedu.address.commons.core.LogsCenter;

/**
 * The Calendar of the App.
 *
 * Adapted from javafx-calendar by SirGoose3432
 * URL: https://github.com/SirGoose3432/javafx-calendar
 */
public class Calendar extends UiPart<Region> {

    private static final String FXML = "Calendar.fxml";

    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(42);
    private VBox calendarView;
    private Text calendarHeader;
    private YearMonth currentYearMonth;

    public Calendar() {
        this(YearMonth.now());
    }

    public Calendar(YearMonth yearMonth) {
        super(FXML);
        currentYearMonth = yearMonth;
        initCalendar();
    }

    /**
     * Create the calendarView for the calendar
     */
    private void initCalendar() {
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode apn = new AnchorPaneNode();
                apn.setPrefSize(200, 200);
                apn.getStyleClass().add("date");
                calendar.add(apn, j, i);
                allCalendarDays.add(apn);
            }
        }
        // Days of the week
        Text[] dayNames = new Text[]{ new Text("Mon"), new Text("Tue"), new Text("Wed"), new Text("Thu"),
            new Text("Fri"), new Text("Sat"), new Text("Sun") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
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
        for (AnchorPaneNode apn : allCalendarDays) {
            if (apn.getChildren().size() != 0) {
                apn.getChildren().remove(0);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            if (calendarDate.getMonth().equals(yearMonth.getMonth())) {
                txt.getStyleClass().add("thisMonthDate");
            } else {
                txt.getStyleClass().add("notThisMonthDate");
            }
            apn.setDate(calendarDate);
            AnchorPane.setTopAnchor(txt, 5.0);
            AnchorPane.setLeftAnchor(txt, 5.0);
            apn.getChildren().add(txt);
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the header of the calendar
        calendarHeader.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    /**
     * Move back 1 month, then refill the calendar.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        fillCalendar(currentYearMonth);
    }

    /**
     * Move forward 1 month, then refill the calendar.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        fillCalendar(currentYearMonth);
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
    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    /**
     * Set all currently visible days to {@code allCalendarDays}
     */
    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

    public Node getDateNode(LocalDate date) {
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

    /*
    private class Controller {
        // The pane to display the calendar
        @FXML
        Pane calendarPane;
    }*/

    /**
     * Node to display each cell/day of the calendar
     */
    private class AnchorPaneNode extends AnchorPane {

        private LocalDate date;

        /**
         * Create a anchor pane node containing all of the {@code children}.
         * The date of the node is not set in the constructor
         */
        public AnchorPaneNode(Node... children) {
            super(children);
            // Add action handler for mouse clicked
            this.setOnMouseClicked(e -> {
                System.out.println("This pane's date is: " + date);
                LogsCenter.getLogger(this.getClass()).info(e.toString());
            });
        }

        /**
         * Returns the date of the node
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * Set the date of the node to {@code date}
         */
        public void setDate(LocalDate date) {
            this.date = date;
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AnchorPaneNode)) {
                return false;
            }

            // state check
            AnchorPaneNode theOther = (AnchorPaneNode) other;
            return date.toString().equals(theOther.date.toString());
        }
    }
}
