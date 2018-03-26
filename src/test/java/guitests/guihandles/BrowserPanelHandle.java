package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    //private static final String ROLE_FIELD_ID = "#role";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String SUBJECT_FIELD_ID = "#subject";
    private static final String LEVEL_FIELD_ID = "#level";
    private static final String PRICE_FIELD_ID = "#price";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    //private final Label roleLabel;
    private final Label statusLabel;
    private final Label subjectLabel;
    private final Label levelLabel;
    private final Label priceLabel;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        //this.roleLabel = getChildNode(ROLE_FIELD_ID);
        this.statusLabel = getChildNode(STATUS_FIELD_ID);
        this.subjectLabel = getChildNode(SUBJECT_FIELD_ID);
        this.levelLabel = getChildNode(LEVEL_FIELD_ID);
        this.priceLabel = getChildNode(PRICE_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    /*public String getRole() {
        return roleLabel.getText();
    }*/

    public String getStatus() {
        return statusLabel.getText();
    }

    public String getSubject() {
        return subjectLabel.getText();
    }

    public String getLevel() {
        return levelLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }
}
