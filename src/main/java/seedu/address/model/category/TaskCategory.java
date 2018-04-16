package seedu.address.model.category;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author CYX28
/**
 * Represents a TaskCategory in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTaskCategoryName(String)}
 */
public class TaskCategory {

    public static final String MESSAGE_TASK_CATEGORY_CONSTRAINTS =
            "Task category names should be alphanumeric, and does not contain any whitespaces";
    public static final String TASK_CATEGORY_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String taskCategoryName;

    /**
     * Constructs a {@code TaskCategory}.
     *
     * @param taskCategoryName A valid task category name.
     */
    public TaskCategory(String taskCategoryName) {
        requireNonNull(taskCategoryName);
        checkArgument(isValidTaskCategoryName(taskCategoryName), MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        this.taskCategoryName = taskCategoryName;
    }

    /**
     * Returns true if a given string is a valid task category name.
     */
    public static boolean isValidTaskCategoryName(String test) {
        return test.matches(TASK_CATEGORY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskCategory
                && this.taskCategoryName.equals(((TaskCategory) other).taskCategoryName));
    }

    @Override
    public int hashCode() {
        return taskCategoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + taskCategoryName + ']';
    }

}
