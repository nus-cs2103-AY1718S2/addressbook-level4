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
import seedu.address.commons.events.ui.DeselectListCellTask;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PanelSelectionChangedEvent;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of activities.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
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
                        raise(new DeselectListCellTask(taskListView, index));
                    } else {
                        taskListView.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new PanelSelectionChangedEvent(newValue));
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

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
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
    /**
     * Getter method for taskListView
     * @return taskListView
     */
    public ListView<TaskCard> getTaskListView()   {
        return taskListView;
    }
}
