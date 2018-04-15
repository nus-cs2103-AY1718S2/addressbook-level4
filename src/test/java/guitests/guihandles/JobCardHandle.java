package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

//@@author ChengSashankh
/**
 * Provides a handle to a job card in the job list panel.
 */
public class JobCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String JOBTITLE_FIELD_ID = "#jobTitle";
    private static final String LOCATION_FIELD_ID = "#jobLocation";
    private static final String SKILLS_FIELD_ID = "#jobSkills";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label jobTitleLabel;
    private final Label locationLabel;
    private final Label skillsLabel;
    private final List<Label> tagLabels;

    public JobCardHandle(Node cardNode) {
        super(cardNode);
        this.idLabel = getChildNode(ID_FIELD_ID);
        this.jobTitleLabel = getChildNode(JOBTITLE_FIELD_ID);
        this.locationLabel = getChildNode(LOCATION_FIELD_ID);
        this.skillsLabel = getChildNode(SKILLS_FIELD_ID);

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

    public String getJobTitle() {
        return jobTitleLabel.getText();
    }

    public String getLocation() {
        return locationLabel.getText();
    }

    public String getSkills() {
        return skillsLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
//@@author
