
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

//@@author yeggasd
/**
 * Provides a handle to a person details card in the main window.
 */
public class PersonDetailsCardHandle extends NodeHandle<Node> {
    public static final String PERSON_DETAILS_CARD_PLACEHOLDER = "#personDetailsCard";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String TIMETABLE_FIELD_ID = "#timeTable";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;
    private final TableView timeTable;

    public PersonDetailsCardHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
        this.timeTable = getChildNode(TIMETABLE_FIELD_ID);
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

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public TableView getTimeTable() {
        return timeTable;
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
}

