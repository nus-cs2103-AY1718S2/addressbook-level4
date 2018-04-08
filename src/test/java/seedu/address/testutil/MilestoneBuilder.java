package seedu.address.testutil;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TaskBuilder;
import seedu.address.model.student.dashboard.Date;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Progress;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.UniqueTaskList;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.TaskNotFoundException;

//@@author yapni
/**
 * A utility class to help with building Milestone objects.
 */
public class MilestoneBuilder {
    
    private final Date DEFAULT_DATE = new Date("23/05/2018 23:59");
    private final UniqueTaskList DEFAULT_TASK_LIST = setDefaultTaskList();
    private final Progress DEFAULT_PROGRESS = new ProgressBuilder().build();
    private final String DEFAULT_DESCRIPTION = "Arrays";

    private Date dueDate;
    private UniqueTaskList taskList;
    private Progress progress;
    private String description;

    public MilestoneBuilder() {
        dueDate = DEFAULT_DATE;
        taskList = DEFAULT_TASK_LIST;
        progress = DEFAULT_PROGRESS;
        description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the MilestoneBuilder with the data of {@code milestoneToCopy}.
     */
    public MilestoneBuilder(Milestone milestoneToCopy) {
        dueDate = milestoneToCopy.getDueDate();
        taskList = milestoneToCopy.getTaskList();
        progress = milestoneToCopy.getProgress();
        description = milestoneToCopy.getDescription();
    }

    /**
     * Sets the {@code dueDate} of the milestone we are building.
     */
    public MilestoneBuilder withDueDate(Date date) {
        this.dueDate = date;
        return this;
    }

    /**
     * Sets the {@code progress} of the milestone we are building.
     */
    public MilestoneBuilder withProgress(Progress progress) {
        this.progress = progress;
        return this;
    }

    /**
     * Sets the {@code tasksList} of the milestone we are building.
     */
    public MilestoneBuilder withTaskList(List<Task> taskList) throws DuplicateTaskException {
        UniqueTaskList modelTaskList = new UniqueTaskList();
        for (Task task : taskList) {
            modelTaskList.add(task);
        }
        this.taskList = modelTaskList;

        return this;
    }

    /**
     * Sets the {@code description} of the milestone we are building.
     */
    public MilestoneBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Adds a new {@code Task} to the {@code Milestone} we are building.
     *
     * @throws DuplicateTaskException if the new task is a duplicate of an existing task in the milestone.
     */
    public MilestoneBuilder withNewTask(Task newTask) throws DuplicateTaskException {
        taskList.add(newTask);
        progress = new seedu.address.logic.commands.ProgressBuilder(progress).withOneNewIncompletedTaskToTotal().build();

        return this;
    }

    public MilestoneBuilder withoutTask(Task task) throws TaskNotFoundException {
        taskList.remove(task);
        if (task.isCompleted()) {
            progress = new seedu.address.logic.commands.ProgressBuilder(progress).withOneLessCompletedTaskFromTotal().build();
        } else {
            progress = new seedu.address.logic.commands.ProgressBuilder(progress).withOneLessIncompletedTaskFromTotal().build();
        }

        return this;
    }

    /**
     * Marks the specified {@code Task} in the {@code taskList} of the {@code Milestone} we are building as completed.
     */
    public MilestoneBuilder withTaskCompleted(Index taskIndex) throws DuplicateTaskException, TaskNotFoundException {
        Task targetTask = taskList.get(taskIndex);

        if (!targetTask.isCompleted()) {
            Task completedTargetTask = new TaskBuilder(targetTask).asCompleted().build();
            taskList.setTask(targetTask, completedTargetTask);
            progress = new ProgressBuilder(progress).withOneMoreCompletedTask().build();
        }

        return this;
    }

    /**
     * Creates and returns the Milestone object with the current attributes.
     */
    public Milestone build() {
        return new Milestone(dueDate, taskList, progress, description);
    }

    /**
     * Sets the default {@code UniqueTasklist}
     */
    private static UniqueTaskList setDefaultTaskList() {
        UniqueTaskList taskList = new UniqueTaskList();

        try {
            taskList.add(TypicalTasks.TASK_1);
            taskList.add(TypicalTasks.TASK_2);
            taskList.add(TypicalTasks.TASK_3);
        } catch(DuplicateTaskException e) {
            throw new AssertionError("Cannot have duplicate task in test");
        }

        return taskList;
    }
}
