//@@author ZhangYijiong
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class OrderCard extends UiPart<Region> {
    private static final String FXML = "OrderCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label order;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label description;

    public OrderCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        bindListeners(task);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Task task) {
        order.textProperty().bind(Bindings.convert(task.orderObjectProperty()));
        address.textProperty().bind(Bindings.convert(task.addressObjectProperty()));
        description.textProperty().bind(Bindings.convert(task.descriptionObjectProperty()));

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrderCard)) {
            return false;
        }

        // state check
        OrderCard card = (OrderCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
