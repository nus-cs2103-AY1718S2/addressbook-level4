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
 * The default view for center stage.
 */
public class MainView extends UiPart<Region> {

    private static final String FXML = "MainView.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TaskListPanel taskListPanel;
    private EventListPanel eventListPanel;
    private CalendarPanel calendarPanel;
    private BrowserPanel browserPanel;

    @FXML
    private SplitPane mainView;

    @FXML
    private SplitPane mainViewSplitPane2;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    public MainView(Logic logic) {
        super(FXML);
        configureDividers();

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

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
     * Make dividers not resizable.
     */
    private void configureDividers()    {

        SplitPane.Divider divider = mainView.getDividers().get(0);
        divider.positionProperty().addListener((ObservableValue<? extends Number>
                                                        observable, Number oldValue, Number newValue) -> {
            divider.setPosition(0.25);
        });

        SplitPane.Divider divider2 = mainViewSplitPane2.getDividers().get(0);
        divider2.positionProperty().addListener((ObservableValue<? extends Number>
                                                         observable, Number oldValue, Number newValue) -> {
            divider2.setPosition(0.34);
        });

    }

    public SplitPane getRoot()   {
        return this.mainView;
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    public EventListPanel getEventListPanel() {
        return eventListPanel;
    }
}
