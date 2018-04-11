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
 * The default view for center stage.
 */
public class MainView extends UiPart<Region> {

    private static final String FXML = "MainView.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private TaskListPanel taskListPanel;
    private EventListPanel eventListPanel;
    private CalendarPanel calendarPanel;

    @FXML
    private SplitPane mainView;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    public MainView(Logic logic) {
        super(FXML);

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        eventListPanel = new EventListPanel(logic.getFilteredEventList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        calendarPanel = new CalendarPanel(logic.getFilteredActivitiesList());
        calendarPanelPlaceholder.getChildren().add(calendarPanel.getRoot());
    }


    public SplitPane getRoot()   {
        return this.mainView;
    }
}
