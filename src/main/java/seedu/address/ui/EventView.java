package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

//@@author jasmoon

/**
 * The event view for center stage.
 */
public class EventView extends UiPart<Region> {

    private static final String FXML = "EventView.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private EventListPanel eventListPanel;
    private CalendarPanel calendarPanel;

    @FXML
    private SplitPane eventView;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    public EventView(Logic logic) {
        super(FXML);

        eventListPanel = new EventListPanel(logic.getFilteredEventList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        calendarPanel = new CalendarPanel(logic.getFilteredActivitiesList());
        calendarPanelPlaceholder.getChildren().add(calendarPanel.getRoot());
    }

    public SplitPane getRoot()   {
        return this.eventView;
    }
}
