package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;
import seedu.address.model.util.SampleDataUtil;

//@@author CYX28
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TASK_NAME = "TASK ONE";
    public static final String DEFAULT_TASK_PRIORITY = "medium";
    public static final String DEFAULT_TASK_DESCRIPTION = "Sample task description";
    public static final String DEFAULT_TASK_DUE_DATE = "2018-10-10";
    public static final String DEFAULT_TASK_STATUS = "undone";
    public static final String DEFAULT_TASK_CATEGORIES = "work";

    private TaskName taskName;
    private TaskPriority taskPriority;
    private TaskDescription taskDescription;
    private TaskDueDate taskDueDate;
    private TaskStatus taskStatus;
    private Set<TaskCategory> taskCategories;

    public TaskBuilder() {
        taskName = new TaskName(DEFAULT_TASK_NAME);
        taskPriority = new TaskPriority(DEFAULT_TASK_PRIORITY);
        taskDescription = new TaskDescription(DEFAULT_TASK_DESCRIPTION);
        taskDueDate = new TaskDueDate(DEFAULT_TASK_DUE_DATE);
        taskStatus = new TaskStatus(DEFAULT_TASK_STATUS);
        taskCategories = SampleDataUtil.getTaskCategorySet(DEFAULT_TASK_CATEGORIES);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        taskName = taskToCopy.getTaskName();
        taskPriority = taskToCopy.getTaskPriority();
        taskDescription = taskToCopy.getTaskDescription();
        taskDueDate = taskToCopy.getTaskDueDate();
        taskStatus = taskToCopy.getTaskStatus();
        taskCategories = new HashSet<>(taskToCopy.getTaskCategories());
    }

    /**
     * Sets the {@code TaskName} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskName(String taskName) {
        this.taskName = new TaskName(taskName);
        return this;
    }

    /**
     * Sets the {@code TaskPriority} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskPriority(String taskPriority) {
        this.taskPriority = new TaskPriority(taskPriority);
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDescription(String taskDescription) {
        this.taskDescription = new TaskDescription(taskDescription);
        return this;
    }

    /**
     * Sets the {@code TaskDueDate} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskDueDate(String taskDueDate) {
        this.taskDueDate = new TaskDueDate(taskDueDate);
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code Task} that we are building.
     */
    public TaskBuilder withTaskStatus(String taskStatus) {
        this.taskStatus = new TaskStatus(taskStatus);
        return this;
    }

    /**
     * Parses the {@code taskCategories} into a {@code Set<TaskCategory>} and
     * set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTaskCategories(String ... taskCategories) {
        this.taskCategories = SampleDataUtil.getTaskCategorySet(taskCategories);
        return this;
    }

    public Task build() {
        return new Task(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, taskCategories);
    }

}
