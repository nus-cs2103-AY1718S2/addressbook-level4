package seedu.organizer.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.organizer.model.task.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/organizer-level4/issues/336">The issue on Organizer level 4</a>
     */

    private static final String[] TAG_COLOR_STYLES = { "blue", "brown", "gray", "green", "maroon", "orange",
        "pink", "purple", "red", "yellow" };
    private static final int CELL_HEIGHT = 24;
    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label status;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label description;
    @FXML
    private Label deadline;
    @FXML
    private Label dateadded;
    @FXML
    private Label datecompleted;
    @FXML
    private FlowPane tags;
    @FXML
    private ListView<Label> subtasks;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().fullName);
        status.setText("[" + task.getStatus() + "]");
        priority.setText("Priority : " + task.getUpdatedPriority().value);
        description.setText("Description : " + task.getDescription().value);
        deadline.setText("Deadline : " + task.getDeadline().toString());
        dateadded.setText("Date Added : " + task.getDateAdded().toString());
        datecompleted.setText("Date Completed: " + task.getDateCompleted().toString());
        initSubtask(task);
        initTags(task);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code task}.
     */
    private void initTags(Task task) {
        task.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    //@@author aguss787
    /**
     * Creates the subtask for {@code task}.
     */
    private void initSubtask(Task task) {
        task.getSubtasks().forEach(subtask-> {
            Label subtaskLabel = new Label(subtask.toString());
            subtasks.getItems().add(subtaskLabel);
        });
        subtasks.setPrefHeight(10 + CELL_HEIGHT * task.getSubtasks().size());
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
