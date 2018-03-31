//@@author jasmoon
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label dateTime;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane status;

    public TaskCard(Activity task, int displayedIndex) {
        super(FXML);
        this.task = (Task) task;
        id.setText(displayedIndex + ". ");
        name.setText(this.task.getName().fullName);
        dateTime.setText(this.task.getDateTime().toString());
        remark.setText(this.task.getRemark().value);
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (task.isCompleted()) {
            status.getChildren().add(new Label("Completed"));
        } else {
            status.getChildren().add(new Label("Uncompleted"));
        }
    }

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
