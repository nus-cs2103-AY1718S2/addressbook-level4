package seedu.organizer.ui;

import java.time.YearMonth;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.organizer.model.task.Task;
import seedu.organizer.ui.calendar.MonthView;

//@@author guekling
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private MonthView monthView;
    private YearMonth currentYearMonth;

    @FXML
    private StackPane calendarPane;

    public CalendarPanel(ObservableList<Task> taskList, ObservableList<String> executedCommandsList) {
        super(FXML);

        monthView = new MonthView(taskList, executedCommandsList);
        currentYearMonth = currentYearMonth.now();

        createMainView();
    }

    /**
     * Creates the main view of the calendar, which by default, is the current month view.*
     */
    private void createMainView() {
        monthView.getMonthView(currentYearMonth);
        calendarPane.getChildren().add(monthView.getRoot());
    }

    public MonthView getMonthView() {
        return monthView;
    }
}
