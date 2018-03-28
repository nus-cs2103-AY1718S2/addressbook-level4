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
    private static final String TASK_ID_FIELD_ID = "#id";
    private static final String TASK_NAME_FIELD_ID = "#name";
    private static final String TASK_PRIORITY_FIELD_ID = "#priority";
    private static final String TASK_DESCRIPTION_FIELD_ID = "#description";
    private static final String TASK_DUE_DATE_FIELD_ID = "#dueDate";
    private static final String TASK_STATUS_FIELD_ID = "#status";
    private static final String TASK_CATEGORIES_FIELD_ID = "#categories";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label priorityLabel;
    private final Label descriptionLabel;
    private final Label dueDateLabel;
    private final Label statusLabel;
    private final List<Label> categoriesLabels;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(TASK_ID_FIELD_ID);
        this.nameLabel = getChildNode(TASK_NAME_FIELD_ID);
        this.priorityLabel = getChildNode(TASK_PRIORITY_FIELD_ID);
        this.descriptionLabel = getChildNode(TASK_DESCRIPTION_FIELD_ID);
        this.dueDateLabel = getChildNode(TASK_DUE_DATE_FIELD_ID);
        this.statusLabel = getChildNode(TASK_STATUS_FIELD_ID);

        Region taskCategoriesContainer = getChildNode(TASK_CATEGORIES_FIELD_ID);
        this.categoriesLabels = taskCategoriesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getTaskId() {
        return idLabel.getText();
    }

    public String getTaskName() {
        return nameLabel.getText();
    }

    public String getTaskPriority() {
        return priorityLabel.getText();
    }

    public String getTaskDescription() {
        return descriptionLabel.getText();
    }

    public String getTaskDueDate() {
        return dueDateLabel.getText();
    }

    public String getTaskStatus() {
        return statusLabel.getText();
    }

    public List<String> getTaskCategories() {
        return categoriesLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
