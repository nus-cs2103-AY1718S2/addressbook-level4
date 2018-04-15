package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
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
    private BrowserPanel browserPanel;

    @FXML
    private SplitPane eventView;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    public EventView(Logic logic) {
        super(FXML);
        configureDividers();

        eventListPanel = new EventListPanel(logic.getFilteredEventList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        calendarPanel = new CalendarPanel(logic.getFilteredActivitiesList());
        ZoomPane zoomPane = new ZoomPane();
        Parent calendarPane = zoomPane.createZoomPane(new Group(calendarPanel.getRoot()));
        calendarPanelPlaceholder.getChildren().setAll(calendarPane);
    }

    /**
     * Make divider not resizable.
     */
    private void configureDividers()    {

        SplitPane.Divider divider = eventView.getDividers().get(0);
        divider.positionProperty().addListener((ObservableValue<? extends Number>
                                                        observable, Number oldValue, Number newValue) -> {
            divider.setPosition(0.50);
        });

    }

    public SplitPane getRoot()   {
        return this.eventView;
    }

    public EventListPanel getEventListPanel() {
        return eventListPanel;
    }
}
