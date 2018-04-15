package seedu.address.testutil;

import seedu.address.model.student.dashboard.Task;

//@@author yapni
/**
 * A utility class to help with building Milestone objects.
 */
public class TaskBuilder {

    private final String defaultName = "Learn syntax";
    private final String defaultDescription = "Refer to coding website";
    private final boolean defaultIsCompleted = false;

    private String name;
    private String description;
    private boolean isCompleted;

    public TaskBuilder() {
        name = defaultName;
        description = defaultDescription;
        isCompleted = defaultIsCompleted;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        description = taskToCopy.getDescription();
        isCompleted = taskToCopy.isCompleted();
    }

    /**
     * Sets the {@code name} of the {@code Task} we are building
     */
    public TaskBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} we are building
     */
    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
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
