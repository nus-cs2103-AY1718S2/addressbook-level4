package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

//@@author Livian1107
/**
 * Provides a handle to profile panel.
 */
public class ProfilePanelHandle extends NodeHandle<Node> {

    private static final String NAME_FIELD_ID = "#name";
    private static final String MAJOR_FIELD_ID = "#major";
    private static final String YEAR_FIELD_ID = "#year";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String USERNAME_FIELD_ID = "#username";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label nameLabel;
    private final Label majorLabel;
    private final Label yearLabel;
    private final Label phoneLabel;
    private final Label usernameLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    private String lastRememberedName;

    public ProfilePanelHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.majorLabel = getChildNode(MAJOR_FIELD_ID);
        this.yearLabel = getChildNode(YEAR_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.usernameLabel = getChildNode(USERNAME_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getUsername() {
        return usernameLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return majorLabel.getText();
    }

    public String getYear() {
        return yearLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<Label> getTagLabels() {
        return tagLabels;
    }
}
