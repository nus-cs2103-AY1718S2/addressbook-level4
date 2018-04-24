//@@author jasmoon
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectTaskListCellEvent;
import seedu.address.commons.events.ui.JumpToEventListRequestEvent;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.commons.events.ui.PanelSelectionChangedEvent;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of activities.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private static EventListPanel eventListPanel;
    private static int selectedIndex = -1;
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    private Label emptyLabel = new Label("Task List is empty!");

    public TaskListPanel(ObservableList<Activity> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
        setUpPlaceholder();
    }

    private void setUpPlaceholder() {
        taskListView.setPlaceholder(emptyLabel);
        emptyLabel.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 25px; ");
    }

    private void setConnections(ObservableList<Activity> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (activity) -> new TaskCard(activity, taskList.indexOf(activity) + 1));
        taskListView.setItems(mappedList);
        linkCell();
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Links taskListView to taskListViewCell as its custom ListCell
     * Add deselection for mouse and ESC key.
     */
    private void linkCell() {
        taskListView.setCellFactory(listView -> {
            TaskListViewCell cell = new TaskListViewCell();
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                taskListView.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (taskListView.getSelectionModel().getSelectedIndices().contains(index))  {
                        logger.fine("Selection in task list panel with index '" + index
                                + "' has been deselected");
                        raise(new DeselectTaskListCellEvent(taskListView, index));
                    } else {
                        selectedIndex = index;
                        taskListView.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });

            return cell;
        });
    }

    public void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new PanelSelectionChangedEvent(newValue, "TaskCard"));
                    }
                });
    }

    /**
     * Scrolls to the {@code TaskCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    //@@author YuanQLLer
    /**
     * Unselect a tab..
     */
    private void unselect() {
        Platform.runLater(() -> {
            taskListView.getSelectionModel().clearSelection();
        });
    }

    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleJumpToEventListRequestEvent(JumpToEventListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        unselect();
    }

    //@@author YuanQLLer
    @Subscribe
    private void handleDeselectTaskListCellEvent(DeselectTaskListCellEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectedIndex = -1;
        event.getPanel().getSelectionModel().clearSelection(event.getTargetIndex());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(activity.getRoot());
            }
        }
    }

    //@@author jasmoon
    public ListView<TaskCard> getTaskListView()   {
        return taskListView;
    }

    public void setData(EventListPanel eventListPanel) {
        this.eventListPanel = eventListPanel;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

}
