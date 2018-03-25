package seedu.organizer.ui;

import java.io.IOException;
import java.time.YearMonth;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.organizer.commons.core.LogsCenter;
import seedu.organizer.ui.calendar.Calendar;

//@@author guekling
/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Calendar calendar;
    private YearMonth currentYearMonth;

    @FXML
    private StackPane calendarPane;

    public CalendarPanel() {
        super(FXML);

        calendar = new Calendar();
        currentYearMonth = currentYearMonth.now();

        loadMainView();
    }

    /**
     * Loads the main view of the calendar.
     */
    private void loadMainView() {
        try {
            createMainView();
        } catch (IOException e) {
            logger.warning("Error loading Calendar.");
        }
    }

    /**
     * Creates the main view of the calendar, which by default, is the current month view.
     *
     * @throws IOException if there's problem fetching the Calendar.
     */
    private void createMainView() throws IOException {
        createMonthView(currentYearMonth);
        calendarPane.getChildren().add(calendar.getRoot());
    }

    /**
     * Creates the month view of the calendar.
     *
     * @param yearMonth Current year and month in the YearMonth format.
     * @throws IOException if there's problem fetching the Calendar.
     */
    private void createMonthView(YearMonth yearMonth) throws IOException {
        calendar.getMonthView(yearMonth);
    }
}
