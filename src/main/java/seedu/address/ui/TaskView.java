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
 * The task view for center stage
 */
public class TaskView extends UiPart<Region> {

    private static final String FXML = "TaskView.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TaskListPanel taskListPanel;
    private CalendarPanel calendarPanel;

    @FXML
    private SplitPane taskView;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    public TaskView(Logic logic) {
        super(FXML);

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        calendarPanel = new CalendarPanel(logic.getFilteredActivitiesList());
        calendarPanelPlaceholder.getChildren().add(calendarPanel.getRoot());
    }

    public SplitPane getRoot()   {
        return this.taskView;
    }
}
