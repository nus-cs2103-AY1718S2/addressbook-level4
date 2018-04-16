package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class to help with building TaskBook objects.
 * Example usage: <br>
 *     {@code TaskBook tb = new TaskBookBuilder().withTask("taskone").build();}
 */
public class TaskBookBuilder {

    private TaskBook taskBook;

    public TaskBookBuilder() {
        taskBook = new TaskBook();
    }

    public TaskBookBuilder(TaskBook taskBook) {
        this.taskBook = taskBook;
    }

    /**
     * Adds a new {@code Task} to the {@code TaskBook} that we are building.
     */
    public TaskBookBuilder withTask(Task task) {
        try {
            taskBook.addTask(task);
        } catch (DuplicateTaskException dte) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code taskCategoryName} into a {@code TaskCategory} and adds it to the {@code TaskBook}
     * that we are building.
     * @param taskCategory
     * @return
     */
    public TaskBookBuilder withTaskCategory(String taskCategory) {
        try {
            taskBook.addTaskCategory(new TaskCategory(taskCategory));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("taskCategoryName is expected to be valid.");
        }
        return this;
    }

    public TaskBook build() {
        return taskBook;
    }

}
