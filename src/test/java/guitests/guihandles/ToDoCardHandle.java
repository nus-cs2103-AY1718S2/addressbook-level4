package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a to-do card in the to-do list panel.
 */
public class ToDoCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String CONTENT_FIELD_ID = "#content";
    private static final String STATUS_FIELD_ID = "#status";

    private final Label idLabel;
    private final Label contentLabel;
    private final Label statusLabel;

    public ToDoCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.contentLabel = getChildNode(CONTENT_FIELD_ID);
        this.statusLabel = getChildNode(STATUS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getContent() {
        return contentLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }
}
