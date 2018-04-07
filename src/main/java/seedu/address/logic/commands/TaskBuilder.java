package seedu.address.logic.commands;


//@@author yapni

import seedu.address.model.student.dashboard.Task;

/**
 * A utility class to help with building Milestone objects.
 */
public class TaskBuilder {

    private String name;
    private String description;
    private boolean isCompleted;

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        description = taskToCopy.getDescription();
        isCompleted = taskToCopy.isCompleted();
    }

    /**
     * Sets the {@code isCompleted} of the {@code Task} we are building as true
     */
    public TaskBuilder asCompleted() {
        this.isCompleted = true;
        return this;
    }

    /**
     * Creates and returns the Task object with the current attributes.
     */
    public Task build() {
        return new Task(name, description, isCompleted);
    }
}
