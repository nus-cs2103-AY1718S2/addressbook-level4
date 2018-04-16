package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;

//@@author CYX28
/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details.
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setTaskName((task.getTaskName()));
        descriptor.setTaskPriority(task.getTaskPriority());
        descriptor.setTaskDescription(task.getTaskDescription());
        descriptor.setTaskDueDate(task.getTaskDueDate());
        descriptor.setTaskStatus(task.getTaskStatus());
        descriptor.setTaskCategories(task.getTaskCategories());
    }

    /**
     * Sets the {@code TaskName} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskName(String taskName) {
        descriptor.setTaskName(new TaskName(taskName));
        return this;
    }

    /**
     * Sets the {@code TaskPriority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskPriority(String taskPriority) {
        descriptor.setTaskPriority(new TaskPriority(taskPriority));
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskDescription(String taskDescription) {
        descriptor.setTaskDescription(new TaskDescription(taskDescription));
        return this;
    }

    /**
     * Sets the {@code TaskDueDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskDueDate(String taskDueDate) {
        descriptor.setTaskDueDate(new TaskDueDate(taskDueDate));
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskStatus(String taskStatus) {
        descriptor.setTaskStatus(new TaskStatus(taskStatus));
        return this;
    }

    /**
     * Parses the {@code taskCategories} into a {@code Set<TaskCategory>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTaskCategories(String... taskCategories) {
        Set<TaskCategory> taskCategorySet =
                Stream.of(taskCategories).map(TaskCategory::new).collect(Collectors.toSet());
        descriptor.setTaskCategories(taskCategorySet);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }

}
