package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskDueDate;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;
import seedu.address.model.task.TaskStatus;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author CYX28
/**
 * Edits the details of an existing task in the address book.
 */
public class TaskEditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task-edit";
    public static final String COMMAND_ALIAS = "te";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TASK_NAME + "NAME] "
            + "[" + PREFIX_TASK_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_TASK_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TASK_DUE_DATE + "DUE_DATE] "
            + "[" + PREFIX_TASK_STATUS + "STATUS] "
            + "[" + PREFIX_TASK_CATEGORY + "CATEGORY]...\n "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TASK_PRIORITY + "medium "
            + PREFIX_TASK_STATUS + "done";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    private Task taskToEdit;
    private Task editedTask;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public TaskEditCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(index.getZeroBased());
        editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        TaskName updatedTaskName =
                editTaskDescriptor.getTaskName().orElse(taskToEdit.getTaskName());
        TaskPriority updatedTaskPriority =
                editTaskDescriptor.getTaskPriority().orElse(taskToEdit.getTaskPriority());
        TaskDescription updatedTaskDescription =
                editTaskDescriptor.getTaskDescription().orElse(taskToEdit.getTaskDescription());
        TaskDueDate updatedTaskDueDate =
                editTaskDescriptor.getTaskDueDate().orElse(taskToEdit.getTaskDueDate());
        TaskStatus updatedTaskStatus =
                editTaskDescriptor.getTaskStatus().orElse(taskToEdit.getTaskStatus());
        Set<TaskCategory> updatedTaskCategories =
                editTaskDescriptor.getTaskCategories().orElse(taskToEdit.getTaskCategories());

        return new Task(updatedTaskName, updatedTaskPriority, updatedTaskDescription, updatedTaskDueDate,
                updatedTaskStatus, updatedTaskCategories);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskEditCommand)) {
            return false;
        }

        // state check
        TaskEditCommand te = (TaskEditCommand) other;
        return index.equals(te.index)
                && editTaskDescriptor.equals(te.editTaskDescriptor)
                && Objects.equals(taskToEdit, te.taskToEdit);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private TaskName taskName;
        private TaskPriority taskPriority;
        private TaskDescription taskDescription;
        private TaskDueDate taskDueDate;
        private TaskStatus taskStatus;
        private Set<TaskCategory> taskCategories;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setTaskName(toCopy.taskName);
            setTaskPriority(toCopy.taskPriority);
            setTaskDescription(toCopy.taskDescription);
            setTaskDueDate(toCopy.taskDueDate);
            setTaskStatus(toCopy.taskStatus);
            setTaskCategories(toCopy.taskCategories);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.taskName, this.taskPriority, this.taskDescription,
                    this.taskDueDate, this.taskStatus, this.taskCategories);
        }

        public void setTaskName(TaskName taskName) {
            this.taskName = taskName;
        }

        public Optional<TaskName> getTaskName() {
            return Optional.ofNullable(taskName);
        }

        public void setTaskPriority(TaskPriority taskPriority) {
            this.taskPriority = taskPriority;
        }

        public Optional<TaskPriority> getTaskPriority() {
            return Optional.ofNullable(taskPriority);
        }

        public void setTaskDescription(TaskDescription taskDescription) {
            this.taskDescription = taskDescription;
        }

        public Optional<TaskDescription> getTaskDescription() {
            return Optional.ofNullable(taskDescription);
        }

        public void setTaskDueDate(TaskDueDate taskDueDate) {
            this.taskDueDate = taskDueDate;
        }

        public Optional<TaskDueDate> getTaskDueDate() {
            return Optional.ofNullable(taskDueDate);
        }

        public void setTaskStatus(TaskStatus taskStatus) {
            this.taskStatus = taskStatus;
        }

        public Optional<TaskStatus> getTaskStatus() {
            return Optional.ofNullable(taskStatus);
        }

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setTaskCategories(Set<TaskCategory> taskCategories) {
            this.taskCategories = (taskCategories != null) ? new HashSet<>(taskCategories) : null;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Set<TaskCategory>> getTaskCategories() {
            return (taskCategories != null) ? Optional.of(Collections.unmodifiableSet(taskCategories))
                    : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handle nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor etd = (EditTaskDescriptor) other;

            return getTaskName().equals(etd.getTaskName())
                    && getTaskPriority().equals(etd.getTaskPriority())
                    && getTaskDescription().equals(etd.getTaskDescription())
                    && getTaskDueDate().equals(etd.getTaskDueDate())
                    && getTaskStatus().equals(etd.getTaskStatus())
                    && getTaskCategories().equals(etd.getTaskCategories());
        }
    }

}
