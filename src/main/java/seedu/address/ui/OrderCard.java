package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.order.Order;

/**
 * An UI component that displays information of a {@code Order}.
 */
public class OrderCard extends UiPart<Region> {

    private static final String FXML = "OrderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Order order;

    @FXML
    private HBox cardPane;
    @FXML
    private Label personId;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private FlowPane subOrders;

    public OrderCard(Order order, int displayedIndex) {
        super(FXML);
        this.order = order;
        id.setText(displayedIndex + ". ");
        personId.setText(order.getPersonId());
        id.setText(Integer.toString(order.getId()));
        time.setText(order.getTime().toString());

        order.getSubOrders().forEach(subOrder ->
                subOrders.getChildren().add(
                        createProductBox(subOrder.getProductID(),subOrder.getNumProduct())
                ));
        subOrders.setHgap(10);
    }

    private VBox createProductBox (int productID, int NumProduct) {
        VBox box = new VBox();
        ObservableList list = box.getChildren();
        Label id = new Label("ProductID: " + productID);
        Label num = new Label("Num: " + NumProduct);
        list.addAll(id, num);
        return box;
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
                && order.equals(card.order);
    }
}

