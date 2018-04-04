package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String DATEADDED_FIELD_ID = "#dateadded";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String STATUS_FIELD_ID = "#status";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label descriptionLabel;
    private final Label statusLabel;
    private final Label priorityLabel;
    private final Label deadlineLabel;
    private final Label dateaddedLabel;
    private final List<Label> tagLabels;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.statusLabel = getChildNode(STATUS_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
        this.dateaddedLabel = getChildNode(DATEADDED_FIELD_ID);

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

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public String getDateAdded() {
        return dateaddedLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
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
