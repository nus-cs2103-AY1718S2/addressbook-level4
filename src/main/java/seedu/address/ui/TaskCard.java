package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.student.dashboard.Task;

//@@author yapni
/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskCard.fxml";

    public final Task task;

    @FXML
    private VBox cardPane;

    @FXML
    private Label index;

    @FXML
    private Label name;

    @FXML
    private Label description;

    @FXML
    private Label isCompleted;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;

        index.setText(displayedIndex + ")");
        name.setText(task.getName());
        description.setText(task.getDescription());
        if (task.isCompleted()) {
            isCompleted.setText("Yes");
        } else {
            isCompleted.setText("No");
        }
    }
}
