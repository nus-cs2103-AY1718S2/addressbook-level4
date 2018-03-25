package seedu.organizer.ui.calendar;

import java.io.IOException;
import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.organizer.ui.UiPart;

//@@author guekling
/**
 * Supports the display of the calendar onto the Calendar Panel.
 */
public class Calendar extends UiPart<Region> {

    private static final String FXML = "Calendar.fxml";

    private MonthView monthView;

    @FXML
    private StackPane calendarPlaceholder;

    public Calendar() {
        super(FXML);
    }

    /**
     * Displays the month view in the {@code calendarPlaceholder}.
     *
     * @param yearMonth Current year and month in the YearMonth format.
     * @throws IOException if there's problem fetching the Month View.
     */
    public void getMonthView(YearMonth yearMonth) throws IOException {
        monthView = new MonthView();

        int currentYear = yearMonth.getYear();

        monthView.setMonthCalendarTitle(currentYear, yearMonth.getMonth().toString());
        monthView.setMonthCalendarDates(currentYear, yearMonth.getMonthValue());
        calendarPlaceholder.getChildren().add(monthView.getRoot());
    }
}
