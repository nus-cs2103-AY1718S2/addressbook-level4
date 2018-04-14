//@@author jaronchan
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class PersonDetailsPanelHandle extends NodeHandle<Node> {

    public static final String PERSON_DETAILS_PANEL_PLACEHOLDER = "#personDetailsPlaceholder";
    private static final String NAME_FIELD_ID = "#nameLabel";
    private static final String ADDRESS_FIELD_ID = "#addressLabel";
    private static final String PHONE_FIELD_ID = "#phoneNumberLabel";
    private static final String EMAIL_FIELD_ID = "#emailLabel";

    private final Label nameLabel;
    private final Label phoneNumberLabel;
    private final Label emailLabel;
    private final Label addressLabel;

    public PersonDetailsPanelHandle(Node personDetailsPanelNode) {
        super(personDetailsPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneNumberLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneNumberLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }
}
