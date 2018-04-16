package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author x3tsunayh

/**
 * Provides a handle to an event card in the event list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DUEDATE_FIELD_ID = "#dueDate";
    private static final String PRIORITY_FIELD_ID = "#priority";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label descriptionLabel;
    private final Label dueDateLabel;
    private final Label priorityLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.dueDateLabel = getChildNode(DUEDATE_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTaskTitle() {
        return titleLabel.getText();
    }

    public String getTaskDescription() {
        return descriptionLabel.getText();
    }

    public String getTaskDueDate() {
        return dueDateLabel.getText();
    }

    public String getTaskPriority() {
        return priorityLabel.getText();
    }

}
