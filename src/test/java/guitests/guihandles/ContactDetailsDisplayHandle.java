package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * A handle to the {@code ContactDetailsDisplay} in the GUI.
 */
public class ContactDetailsDisplayHandle extends NodeHandle<Node> {

    private static final String NAME_FIELD_ID = "#name";
    private static final String LIST_ID = "#values";
    //TODO: include Tags

    private final Label nameLabel;
    private final ListView<Label> valuesList;

    public ContactDetailsDisplayHandle(Node contactDetailsDisplayNode) {
        super(contactDetailsDisplayNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.valuesList = getChildNode(LIST_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getFullName() {
        return valuesList.getItems().get(0).getText();
    }

    public String getPhone() {
        return valuesList.getItems().get(1).getText();
    }

    public String getEmail() {
        return valuesList.getItems().get(2).getText();
    }

    public String getAddress() {
        return valuesList.getItems().get(3).getText();
    }
}
