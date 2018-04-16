package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String RATING_FIELD_ID = "#rating";
    private static final String POSITION_FIELD_ID = "#position";
    private static final String TEAMNAME_FIELD_ID = "#teamName";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label positionLabel;
    private final Label ratingLabel;
    private final Label teamNameLabel;

    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.positionLabel = getChildNode(POSITION_FIELD_ID);
        this.ratingLabel = getChildNode(RATING_FIELD_ID);
        this.teamNameLabel = getChildNode(TEAMNAME_FIELD_ID);

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

    public String getName() {
        return nameLabel.getText();
    }

    public String getPosition() {
        return positionLabel.getText();
    }

    public String getRating() {
        return ratingLabel.getText();
    }

    public String getTeamName() {
        return teamNameLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
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
