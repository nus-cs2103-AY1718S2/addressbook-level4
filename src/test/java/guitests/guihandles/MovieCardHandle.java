package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a movie card in the cinema list panel.
 */
public class MovieCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#movieName";
    private static final String DURATION_FIELD_ID = "#duration";
    private static final String RATING_FIELD_ID = "#rating";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label durationLabel;
    private final Label ratingLabel;
    private final Label startDateLabel;
    private final List<Label> tagLabels;


    public MovieCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.durationLabel = getChildNode(DURATION_FIELD_ID);
        this.ratingLabel = getChildNode(RATING_FIELD_ID);
        this.startDateLabel = getChildNode(STARTDATE_FIELD_ID);

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

    public String getDuration() {
        return durationLabel.getText();
    }

    public String getRating() {
        return ratingLabel.getText();
    }

    public String getStartDate() {
        return startDateLabel.getText();
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
                .orElseThrow(() -> new IllegalArgumentException("Tag does not exist."));
    }
}
