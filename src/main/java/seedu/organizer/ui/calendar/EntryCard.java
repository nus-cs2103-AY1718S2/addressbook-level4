package seedu.organizer.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.organizer.model.task.Task;
import seedu.organizer.ui.UiPart;

//@@author guekling
/**
 * An UI component that displays the name of a {@code Task}.
 */
public class EntryCard extends UiPart<Region> {

    private static final String FXML = "EntryCard.fxml";

    private Task task;

    @FXML
    private Label entryCard;

    public EntryCard(Task task) {
        super(FXML);

        this.task = task;
        entryCard.setText(task.getName().fullName);
    }

    public Task getTask() {
        return task;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntryCard)) {
            return false;
        }

        // state check
        EntryCard card = (EntryCard) other;
        return task.equals(card.task);
    }
}
