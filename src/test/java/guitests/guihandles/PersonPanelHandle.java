package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.layout.Region;

/**
 * Provides a handle to the Person Panel.
 */
public class PersonPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_PANEL_ID = "#personPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String GROUPS_FIELD_ID = "#groups";
    private static final String PREFERENCES_FIELD_ID = "#preferences";

    private Label name;
    private Label phone;
    private Label address;
    private Label email;
    private List<Label> groupLabels;
    private List<Label> preferenceLabels;

    public PersonPanelHandle(Node personPanelNode) {
        super(personPanelNode);

        this.name = getChildNode(NAME_FIELD_ID);
        this.phone = getChildNode(PHONE_FIELD_ID);
        this.address = getChildNode(ADDRESS_FIELD_ID);
        this.email = getChildNode(EMAIL_FIELD_ID);
    }

    public String getName() {
        return name.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    /**
    * Update groups of each person
    */
    public void updateGroups() {
        Region groupsContainer = getChildNode(GROUPS_FIELD_ID);

        this.groupLabels = groupsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public List<String> getGroups() {
        return groupLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Update preferences of each person
     */
    public void updatePreferences() {
        Region preferenceContainer = getChildNode(PREFERENCES_FIELD_ID);

        this.preferenceLabels = preferenceContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public List<String> getPreferences() {
        return preferenceLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
