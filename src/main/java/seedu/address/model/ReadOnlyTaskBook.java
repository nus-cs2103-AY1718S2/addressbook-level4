package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;

//@@author CYX28
/**
 * Unmodifiable view of a task book
 */
public interface ReadOnlyTaskBook {

    /**
     * Returns an unmodifiable view of the tasks list before sorting.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getOriginalTaskList();

    /**
     * Returns an unmodifiable view of the tasks list sorted by task status (i.e. undone and done)
     * followed by taskDueDate.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns an unmodifiable view of the taskCategories list.
     * This list will not contain any duplicate taskCategories.
     */
    ObservableList<TaskCategory> getTaskCategoryList();

}
