package seedu.address.ui;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Task;

//@@author yapni
/**
 * An UI component that displays information of a {@code Milestone}.
 */
public class MilestoneCard extends UiPart<Region> {

    private static final String FXML = "MilestoneCard.fxml";

    public final Milestone milestone;

    @FXML
    private VBox cardPane;

    @FXML
    private ListView<TaskCard> taskListView;

    @FXML
    private Label index;

    @FXML
    private Label description;

    @FXML
    private Label dueDate;

    @FXML
    private Label progressPercent;

    @FXML
    private ProgressBar progress;

    public MilestoneCard(Milestone milestone, int displayedIndex) {
        super(FXML);
        this.milestone = milestone;

        index.setText(Integer.toString(displayedIndex));
        description.setText(milestone.getDescription());
        dueDate.setText(milestone.getDueDate().toString());
        progress.setProgress(milestone.getProgress().getProgressInPercent() / 100.0);
        progressPercent.setText(milestone.getProgress().getProgressInPercent() + "%");
        loadTaskList(milestone.getTaskList().asObservableList());
    }

    /**
     * Loads the list of tasks in to the milestone card
     */
    private void loadTaskList(ObservableList<Task> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }
}
