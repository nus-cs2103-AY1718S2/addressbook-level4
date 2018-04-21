package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a calendar task card in the calendar view.
 */
public class CalendarTaskCardHandle extends NodeHandle<Node> {
    private static final String TITLE_FIELD_ID = "#title";

    private final Label titleLabel;

    public CalendarTaskCardHandle(Node cardNode) {
        super(cardNode);

        this.titleLabel = getChildNode(TITLE_FIELD_ID);
    }

    public String getTitle() {
        return titleLabel.getText();
    }
}
