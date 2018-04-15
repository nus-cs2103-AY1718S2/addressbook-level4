//@@author jas5469
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a group card in the group list panel.
 */
public class GroupCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String INFORMATION_FIELD_INFORMATION = "#information";
    private final Label idLabel;
    private final Label information;

    public GroupCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.information = getChildNode(INFORMATION_FIELD_INFORMATION);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getInformation() {
        return information.getText();
    }
}
