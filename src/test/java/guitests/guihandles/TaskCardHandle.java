package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author jasmoon
/**
 * Handler for task card
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DUEDATE_FIELD_ID = "#dueDate";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label dueDateLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.dueDateLabel = getChildNode(DUEDATE_FIELD_ID);
        /*this.remarkLabel = getChildNode(REMARK_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());*/
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getDateTime() {
        return dueDateLabel.getText();
    }

    /*public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }*/
}
