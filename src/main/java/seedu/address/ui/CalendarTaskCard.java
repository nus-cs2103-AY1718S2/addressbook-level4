package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

//@@author JoonKai1995
/**
 * A UI component that displays compressed information of a {@code Task} on the calendar.
 */
public class CalendarTaskCard extends UiPart<Region> {

    private static final String FXML = "CalendarTaskCard.fxml";

    public final Task task;

    @FXML
    private Label title;

    public CalendarTaskCard(Task task) {
        super(FXML);
        this.task = task;
        title.setText(task.getTitle().toString());
        if (task.getPriority().value == 1) {
            title.getStyleClass().clear();
            title.getStyleClass().add("label-small-green");
        } else if (task.getPriority().value == 2) {
            title.getStyleClass().clear();
            title.getStyleClass().add("label-small-yellow");
        } else {
            title.getStyleClass().clear();
            title.getStyleClass().add("label-small-red");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarTaskCard)) {
            return false;
        }

        // state check
        CalendarTaskCard card = (CalendarTaskCard) other;
        return task.equals(card.task);
    }
}
