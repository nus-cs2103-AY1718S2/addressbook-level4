package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String taskPriority;
    @XmlElement(required = true)
    private String taskDescription;
    @XmlElement(required = true)
    private String taskDueDate;
    @XmlElement(required = true)
    private String taskStatus;

    @XmlElement
    private List<XmlAdaptedTaskCategory> categorised = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String taskName, String taskPriority, String taskDescription, String taskDueDate,
                          String taskStatus, List<XmlAdaptedTaskCategory> categorised) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskDescription = taskDescription;
        this.taskDueDate = taskDueDate;
        this.taskStatus = taskStatus;
        if (categorised != null) {
            this.categorised = new ArrayList<>(categorised);
        }
    }

    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(Task source) {
        taskName = source.getTaskName().value;
        taskPriority = source.getTaskPriority().value;
        taskDescription = source.getTaskDescription().value;
        taskDueDate = source.getTaskDueDate().value;
        taskStatus = source.getTaskStatus().value;
        categorised = new ArrayList<>();
        for (TaskCategory taskCategory : source.getTaskCategories()) {
            categorised.add(new XmlAdaptedTaskCategory(taskCategory));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<TaskCategory> taskCategories = new ArrayList<>();
        for (XmlAdaptedTaskCategory taskCategory : categorised) {
            taskCategories.add(taskCategory.toModelType());
        }

        if (this.taskName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskName.class.getSimpleName()));
        }
        if (!TaskName.isValidTaskName(this.taskName)) {
            throw new IllegalValueException(TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);
        }
        final TaskName taskName = new TaskName(this.taskName);

        if (this.taskPriority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskPriority.class.getSimpleName()));
        }
        if (!TaskPriority.isValidTaskPriority(this.taskPriority)) {
            throw new IllegalValueException(TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        }
        final TaskPriority taskPriority = new TaskPriority(this.taskPriority);

        if (this.taskDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDescription.class.getSimpleName()));
        }
        if (!TaskDescription.isValidTaskDescription(this.taskDescription)) {
            throw new IllegalValueException(TaskDescription.MESSAGE_TASK_DESCRIPTION_CONSTRAINTS);
        }
        final TaskDescription taskDescription = new TaskDescription(this.taskDescription);

        if (this.taskDueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskDescription.class.getSimpleName()));
        }
        if (!TaskDueDate.isValidTaskDueDate(this.taskDueDate)) {
            throw new IllegalValueException(TaskDueDate.MESSAGE_TASK_DUE_DATE_CONSTRAINTS);
        }
        final TaskDueDate taskDueDate = new TaskDueDate(this.taskDueDate);

        if (this.taskStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TaskStatus.class.getSimpleName()));
        }
        if (!TaskStatus.isValidTaskStatus(this.taskStatus)) {
            throw new IllegalValueException(TaskStatus.MESSAGE_TASK_STATUS_CONSTRAINTS);
        }
        final TaskStatus taskStatus = new TaskStatus(this.taskStatus);

        final Set<TaskCategory> categories = new HashSet<>(taskCategories);
        return new Task(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, categories);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherTask = (XmlAdaptedTask) other;
        return Objects.equals(taskName, otherTask.taskName)
                && Objects.equals(taskPriority, otherTask.taskPriority)
                && Objects.equals(taskDescription, otherTask.taskDescription)
                && Objects.equals(taskDueDate, otherTask.taskDueDate)
                && Objects.equals(taskStatus, otherTask.taskStatus)
                && categorised.equals(otherTask.categorised);
    }

}
