package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
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

    private Date dueDate;
    private UniqueTaskList taskList;
    private Progress progress;
    private String description;

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
     * Adds a new {@code Task} to the {@code Milestone} we are building.
     *
     * @throws DuplicateTaskException if the new task is a duplicate of an existing task in the milestone.
     */
    public MilestoneBuilder withNewTask(Task newTask) throws DuplicateTaskException {
        taskList.add(newTask);
        progress = new ProgressBuilder(progress).withOneNewIncompletedTaskToTotal().build();

        return this;
    }

    /**
     * Removes the {@code Task} from the {@code Milestone} we are building.
     *
     * @throws TaskNotFoundException if the task is not found in the milestone.
     */
    public MilestoneBuilder withoutTask(Task task) throws TaskNotFoundException {
        taskList.remove(task);
        if (task.isCompleted()) {
            progress = new ProgressBuilder(progress).withOneLessCompletedTaskFromTotal().build();
        } else {
            progress = new ProgressBuilder(progress).withOneLessIncompletedTaskFromTotal().build();
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
}
