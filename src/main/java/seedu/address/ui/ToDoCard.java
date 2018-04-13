//@@author nhatquang3112
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.todo.ToDo;

/**
 * An UI component that displays information of a {@code ToDo}.
 */
public class ToDoCard extends UiPart<Region> {

    private static final String FXML = "ToDoListCard.fxml";

    public final ToDo toDo;

    @FXML
    private HBox cardPane;
    @FXML
    private Label content;
    @FXML
    private Label status;
    @FXML
    private Label id;

    public ToDoCard(ToDo toDo, int displayedIndex) {
        super(FXML);
        this.toDo = toDo;
        id.setText(displayedIndex + ". ");
        content.setText(toDo.getContent().value);
        status.setText(toDo.getStatus().value);
    }

    public boolean isDone() {
        return toDo.getStatus().value.equals("done");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ToDoCard)) {
            return false;
        }

        // state check
        ToDoCard card = (ToDoCard) other;
        return id.getText().equals(card.id.getText())
                && toDo.equals(card.toDo);
    }
}
