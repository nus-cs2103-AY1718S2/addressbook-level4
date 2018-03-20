package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a patient card in the patient list panel.
 */
public class PatientCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String NRIC_FIELD_ID = "#nric";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String DOB_FIELD_ID = "#dob";
    private static final String BLOODTYPE_FIELD_ID = "#bloodType";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label nricLabel;
    private final Label addressLabel;
    private final Label dobLabel;
    private final Label bloodTypeLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label remarkLabel;
    private final List<Label> tagLabels;

    public PatientCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.nricLabel = getChildNode(NRIC_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.dobLabel = getChildNode(DOB_FIELD_ID);
        this.bloodTypeLabel = getChildNode(BLOODTYPE_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() { return nameLabel.getText(); }

    public String getNric() { return nricLabel.getText(); }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getDob() { return dobLabel.getText(); }

    public String getBloodType() { return bloodTypeLabel.getText(); }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    //@@author yamgent-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/798/files
    public List<String> getTagColors(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
}
