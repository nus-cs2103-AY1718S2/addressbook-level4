package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.view.CalendarView;
import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowCalendarViewRequestEvent;

/**
 * Panel containing the calendar view displaying patients' appointment.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    @FXML
    private StackPane calendarView;

    private CalendarView calendar;

    public CalendarPanel() {
        super(FXML);
        calendar = new CalendarView();
        setConnections();
    }

    private void setConnections() {
        calendarView.setAlignment(calendar, Pos.CENTER);
    }
}
