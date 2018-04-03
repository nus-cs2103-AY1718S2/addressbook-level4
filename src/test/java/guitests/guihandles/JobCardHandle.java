// @@author kush1509
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a job card in the job list panel.
 */
public class JobCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String POSITION_FIELD_ID = "#position";
    private static final String TEAM_FIELD_ID = "#team";
    private static final String LOCATION_FIELD_ID = "#jobLocation";
    private static final String NUMBER_OF_POSITIONS_FIELD_ID = "#numberOfPositions";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label positionLabel;
    private final Label teamLabel;
    private final Label locationLabel;
    private final Label numberOfPositionsLabel;
    private final List<Label> tagLabels;

    public JobCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.positionLabel = getChildNode(POSITION_FIELD_ID);
        this.teamLabel = getChildNode(TEAM_FIELD_ID);
        this.locationLabel = getChildNode(LOCATION_FIELD_ID);
        this.numberOfPositionsLabel = getChildNode(NUMBER_OF_POSITIONS_FIELD_ID);

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

    public String getPosition() {
        return positionLabel.getText();
    }

    public String getTeam() {
        return teamLabel.getText();
    }

    public String getLocation() {
        return locationLabel.getText();
    }

    public String getNumberOfPositions() {
        return numberOfPositionsLabel.getText();
    }

    public List<String> getSkills() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getSkillStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such skill."));
    }
}
