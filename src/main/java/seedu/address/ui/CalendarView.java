package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATETIME;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToCalendarRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.ReadOnlyEvent;

//@@author x3tsunayh

/**
 * Creates a Calendar View
 */
public class CalendarView {
    private ArrayList<AnchorPaneNode> calendarMonth = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth defaultYearMonth;
    private YearMonth currentYearMonth;
    private ObservableList<ReadOnlyEvent> eventList;
    private Logic logic;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    /**
     * Provides layout for the calendar month with anchor panes.
     */
    public CalendarView(Logic logic, ObservableList<ReadOnlyEvent> eventList, YearMonth yearMonth) {
        this.logic = logic;
        this.eventList = eventList;
        defaultYearMonth = yearMonth;
        currentYearMonth = yearMonth;

        // Creates the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.setPrefSize(450, 300);

        // Create rows and columns of anchor panes for calendar
        calendarMonthSetup(calendar);

        // Days of the Week
        Text[] days = new Text[]{new Text("SUNDAY"), new Text("MONDAY"), new Text("TUESDAY"),
            new Text("WEDNESDAY"), new Text("THURSDAY"), new Text("FRIDAY"), new Text("SATURDAY")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(450);
        Integer col = 0;

        for (Text day : days) {
            day.setFill(Color.WHITE);
            day.setFont(new Font("Serif", 13));
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            AnchorPane.setBottomAnchor(day, 5.0);
            ap.getChildren().add(day);
            dayLabels.add(ap, col++, 0);
        }

        // Creates a title for the calendar
        calendarTitle = new Text();
        calendarTitle.setFill(Color.WHITE);
        calendarTitle.setFont(new Font("Serif", 16));

        // Buttons to navigate through months
        Button previousMonth = new Button("< Previous");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button("Next >");
        nextMonth.setOnAction(e -> nextMonth());
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        HBox.setMargin(calendarTitle, new Insets(0, 10, 0, 10));
        titleBar.setAlignment(Pos.BASELINE_CENTER);

        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth, null);

        // Displaying the current date and time
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        Button calendarDateTime = new Button("Today's date is " + dateTimeFormat.format(LocalDate.now()));
        calendarDateTime.setStyle("-fx-border-color: transparent; "
                + "-fx-background-color: transparent; -fx-font-size: 18");
        calendarDateTime.setOnAction(e -> originalYearMonth());
        HBox calendarDtBar = new HBox(calendarDateTime);
        calendarDtBar.setAlignment(Pos.BASELINE_CENTER);

        // Displaying a welcome message
        Text welcomeMessage = new Text("Welcome to BizConnect Journal!");
        welcomeMessage.setFill(Color.WHITE);
        welcomeMessage.setFont(new Font("Impact", 24));
        HBox welcomeMessageBar = new HBox(welcomeMessage);
        welcomeMessageBar.setAlignment(Pos.BASELINE_CENTER);

        // Creates the calendar view
        view = new VBox(calendarDtBar, titleBar, dayLabels, calendar);
        VBox.setMargin(calendarDtBar, new Insets(0, 0, 5, 0));
        VBox.setMargin(titleBar, new Insets(0, 0, 5, 0));

        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Sets the calendar days according to the intended month and year
     * @param targetIndex for finding specific event(s)
     * @param yearMonth for desired year and month of the calendar view
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
                eventExist = eventList.stream()
                        .anyMatch(e -> checkEventDay(e, dayValue)
                                && checkEventMonth(e, monthValue)
                                && checkEventYear(e, yearValue));
            } else {
                ReadOnlyEvent e = eventList.get(targetIndex.getZeroBased());

                if (checkEventDay(e, dayValue)
                        && checkEventMonth(e, monthValue)
                        && checkEventYear(e, yearValue)) {
                    eventExist = true;
                }
            }

            Text dateNumber = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            // Days from a different month shows up as a different colour
            if (calendarDate.getMonthValue() != yearMonth.getMonthValue()) {
                dateNumber.setFill(Color.DARKGREY);
            } else {
                dateNumber.setFill(Color.WHITE);
            }
            ap.setDate(calendarDate);
            ap.setTopAnchor(dateNumber, 5.0);
            ap.setLeftAnchor(dateNumber, 5.0);

            if (eventExist) {
                ap.setOnMouseClicked(ev -> {
                    String commandText = FindEventCommand.getCommandWord()
                            + " " + PREFIX_EVENT_DATETIME + getFormatDate(yearValue, monthValue, dayValue);
                    try {
                        CommandResult commandResult = logic.execute(commandText);
                        logger.info("Command Result: " + commandResult.feedbackToUser);

                    } catch (CommandException | IllegalValueException e) {
                        logger.info("Invalid Command: " + commandText);
                    }
                });
                ap.setStyle("-fx-background-color: #2e5577;");
            } else {
                ap.setStyle("-fx-background-color: #3d719d;");
            }

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

    // to look at the previous month
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth, null);
    }

    // to look at the next month
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth, null);
    }

    // to jump back to the current year and month easily
    private void originalYearMonth() {
        currentYearMonth = defaultYearMonth;
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
    private boolean checkEventDay(ReadOnlyEvent event, String dayValue) {
        if (dayValue.length() == 1) {
            return event.getDatetime().value.substring(8, 10).equals("0" + dayValue);
        } else {
            return event.getDatetime().value.substring(8, 10).equals(dayValue);
        }
    }

    /**
     * Check whether the event Day matches the input monthValue
     * @param event
     * @param monthValue
     * @return
     */
    private boolean checkEventMonth(ReadOnlyEvent event, String monthValue) {
        if (monthValue.length() == 1) {
            return event.getDatetime().value.substring(5, 7).equals("0" + monthValue);
        } else {
            return event.getDatetime().value.substring(5, 7).equals(monthValue);
        }
    }

    /**
     * Check whether the event Day matches the input yearValue
     * @param event
     * @param yearValue
     * @return
     */
    private boolean checkEventYear(ReadOnlyEvent event, String yearValue) {
        return event.getDatetime().value.substring(0, 4).equals(yearValue);
    }

    private String getFormatDate(String year, String month, String day) {
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }
        return year + "-" + month + "-" + day;
    }

    @Subscribe
    private void handleJumpToCalendarRequestEvent(JumpToCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        System.out.println("test");
        setCurrentYearMonth(event.getYearMonth());
        populateCalendar(event.getYearMonth(), null);
    }

}
