package seedu.address.ui;

import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;

//@@author x3tsunayh

/**
 * Panel containing the calendar.
 */
public class CalendarViewPanel extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";

    @FXML
    private Pane calendarPanel;

    private CalendarView calendarView;
    private Logic logic;

    public CalendarViewPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        setConnections();
    }

    private void setConnections() {
        calendarView = new CalendarView(logic, logic.getFilteredEventList(), YearMonth.now());
        calendarPanel.getChildren().add(calendarView.getView());
    }

    public CalendarView getCalendarPane() {
        return calendarView;
    }
}
