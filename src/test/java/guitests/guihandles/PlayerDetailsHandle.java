package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a player details pane.
 */
//@@author Codee
public class PlayerDetailsHandle extends NodeHandle<Node> {

    public static final String PLAYER_DETAILS_DISPLAY_ID = "#playerDetails";

    private static final String NAME_FIELD_ID = "#name";
    private static final String JERSEY_FIELD_ID = "#jerseyNumber";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String REMARK_FIELD_ID = "#remark";

    private final Label nameLabel;
    private final Label jerseyLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label remarkLabel;

    public PlayerDetailsHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);
        this.jerseyLabel = getChildNode(JERSEY_FIELD_ID);

    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getJerseyNumber() {
        return jerseyLabel.getText().substring(15);
    }

    public String getRemarks() {
        return remarkLabel.getText().substring(9);
    }

    public String getEmail() {
        return emailLabel.getText();
    }

}
