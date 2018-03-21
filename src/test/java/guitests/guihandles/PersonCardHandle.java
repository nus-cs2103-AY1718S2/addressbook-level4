package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {


    private static final String NAME_FIELD_ID = "#cardPersonName";
    private static final String UNIVERSITY_FIELD_ID = "#cardPersonUniversity";
    private static final String EMAIL_FIELD_ID = "#cardPersonEmail";
    private static final String PHONE_FIELD_ID = "#cardPersonContact";
    private static final String RATING_FIELD_ID = "#cardPersonRating";
    private static final String STATUS_FIELD_ID = "#cardPersonStatus";
    private static final String ID_FIELD_ID = "#cardPersonNumber";

    private final Label nameLabel;
    private final Label universityLabel;
    private final Label emailLabel;
    private final Label phoneLabel;
    private final Label ratingLabel;
    private final Label statusLabel;
    private final Label idLabel;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.universityLabel = getChildNode(UNIVERSITY_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.ratingLabel = getChildNode(RATING_FIELD_ID);
        this.statusLabel = getChildNode(STATUS_FIELD_ID);
        this.idLabel = getChildNode(ID_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getUniversity() {
        return universityLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getRating() {
        return ratingLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    public String getId() {
        return idLabel.getText();
    }
}
