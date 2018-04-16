package seedu.address.ui;

import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

//@@author x3tsunayh

/**
 * Creates anchor pane to store additional data.
 */
public class AnchorPaneNode extends AnchorPane {
    // Date associated with this pane
    private LocalDate date;
    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */

    public AnchorPaneNode(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        //this.setOnMouseClicked(e -> System.out.println("This pane's date is: " + date));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
