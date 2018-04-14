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
 * The task view for center stage
 */
public class TaskView extends UiPart<Region> {

    private static final String FXML = "TaskView.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TaskListPanel taskListPanel;
    private CalendarPanel calendarPanel;
    private BrowserPanel browserPanel;

    @FXML
    private SplitPane taskView;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    public TaskView(Logic logic) {
        super(FXML);
        configureDividers();

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

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

        SplitPane.Divider divider = taskView.getDividers().get(0);
        divider.positionProperty().addListener((ObservableValue<? extends Number>
                                                        observable, Number oldValue, Number newValue) -> {
            divider.setPosition(0.50);
        });

    }

    public SplitPane getRoot()   {
        return this.taskView;
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }
}
