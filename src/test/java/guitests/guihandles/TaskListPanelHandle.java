package guitests.guihandles;

import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.task.Task;
import seedu.address.ui.TaskCard;

/**
 * Provides a handle for {@code TaskListPanel} containing the list of {@code TaskCard}.
 */
public class TaskListPanelHandle extends NodeHandle<ListView<TaskCard>> {
    public static final String TASK_LIST_VIEW_ID = "#taskListView";

    public TaskListPanelHandle(ListView<TaskCard> taskListPanelNode) {
        super(taskListPanelNode);
    }

    /**
     * Returns the task card handle of a task associated with the {@code index} in the list.
     */
    public TaskCardHandle getTaskCardHandle(int index) {
        return getTaskCardHandle(getRootNode().getItems().get(index).task);
    }

    /**
     * Returns the {@code TaskCardHandle} of the specified {@code task} in the list.
     */
    public TaskCardHandle getTaskCardHandle(Task task) {
        Optional<TaskCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.task.equals(task))
                .map(card -> new TaskCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Task does not exist."));
    }
}
