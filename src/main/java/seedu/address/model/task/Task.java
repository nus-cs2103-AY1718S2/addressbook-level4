package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.category.TaskCategory;
import seedu.address.model.category.UniqueTaskCategoryList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {
    private final TaskName taskName;
    private final TaskPriority taskPriority;
    private final TaskDescription taskDescription;
    private final TaskDueDate taskDueDate;
    private final TaskStatus taskStatus;

    private final UniqueTaskCategoryList taskCategories;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, TaskPriority taskPriority, TaskDescription taskDescription, TaskDueDate taskDueDate,
                TaskStatus taskStatus, Set<TaskCategory> taskCategories) {
        requireAllNonNull(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, taskCategories);
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskDescription = taskDescription;
        this.taskDueDate = taskDueDate;
        this.taskStatus = taskStatus;
        // protect internal categories from changes in the arg list
        this.taskCategories = new UniqueTaskCategoryList(taskCategories);
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public TaskDescription getTaskDescription() {
        return taskDescription;
    }

    public TaskDueDate getTaskDueDate() {
        return taskDueDate;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<TaskCategory> getTaskCategories() {
        return Collections.unmodifiableSet(taskCategories.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTaskName().equals(this.getTaskName())
                && otherTask.getTaskPriority().equals(this.getTaskPriority())
                && otherTask.getTaskDescription().equals(this.getTaskDescription())
                && otherTask.getTaskDueDate().equals(this.getTaskDueDate())
                && otherTask.getTaskStatus().equals(this.getTaskStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, taskCategories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Priority: ")
                .append(getTaskPriority())
                .append(" Description: ")
                .append(getTaskDescription())
                .append(" Due Date: ")
                .append(getTaskDueDate())
                .append(" Status: ")
                .append(getTaskStatus())
                .append(" Categories: ");
        getTaskCategories().forEach(builder::append);
        return builder.toString();
    }
}
