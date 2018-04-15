# agus
###### /java/seedu/organizer/commons/core/Messages.java
``` java
    public static final String MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX = "The subtask index provided is invalid";
```
###### /java/seedu/organizer/logic/commands/AddSubtaskCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;
import static seedu.organizer.model.subtask.UniqueSubtaskList.DuplicateSubtaskException;

import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.DateAdded;
import seedu.organizer.model.task.DateCompleted;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;

/**
 * Add a subtask into a task
 */
public class AddSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "adds";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a subtask to a task. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Submit report ";

    public static final String MESSAGE_SUCCESS = "New subtask added: %1$s";
    public static final String MESSAGE_DUPLICATED = "Subtask already exist";

    private final Subtask toAdd;
    private final Index index;

    private Task taskToEdit;
    private Task editedTask;

    public AddSubtaskCommand(Index index, Subtask toAdd) {
        requireNonNull(toAdd);
        requireNonNull(index);
        this.index = index;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            editedTask = createEditedTask(taskToEdit, toAdd);
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new AssertionError("Task duplication should not happen");
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        } catch (DuplicateSubtaskException dse) {
            throw new CommandException(MESSAGE_DUPLICATED);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(index.getZeroBased());
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     */
    private static Task createEditedTask(Task taskToEdit, Subtask toAdd) throws DuplicateSubtaskException {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted oldDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(taskToEdit.getSubtasks());
        Status updatedStatus = taskToEdit.getStatus();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        updatedSubtasks.add(toAdd);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded, oldDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(), getCurrentlyLoggedInUser(),
                updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSubtaskCommand // instanceof handles nulls
                && this.index.equals(((AddSubtaskCommand) other).index) // state check
                && this.toAdd.equals(((AddSubtaskCommand) other).toAdd)); // state check
    }
}
```
###### /java/seedu/organizer/logic/commands/DeleteSubtaskCommand.java
``` java
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.DateAdded;
import seedu.organizer.model.task.DateCompleted;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.User;

/**
 * Deletes a subtask of a task
 */
public class DeleteSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete-subtask";
    public static final String COMMAND_ALIAS = "ds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete a subtask of a task. "
            + "Parameters: TASK_INDEX (must be a positive integer) SUBTASK_INDEX (must be a positive integer)";

    public static final String MESSAGE_EDIT_SUBTASK_SUCCESS = "Subtask Deleted: %1$s";

    public final Index taskIndex;
    public final Index subtaskIndex;

    private Task taskToEdit;
    private Task editedTask;
    private Subtask deletedSubtask;

    /**
     * @param taskIndex index of the task in the filtered task list to edit
     * @param subtaskIndex index of the subtask of the task to edit
     */
    public DeleteSubtaskCommand(Index taskIndex, Index subtaskIndex) {
        this.taskIndex = taskIndex;
        this.subtaskIndex = subtaskIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException("This exception should not happen (duplicated task while toggling)");
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("This exception should not happen (task missing while toggling)");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_SUBTASK_SUCCESS, deletedSubtask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        taskToEdit = lastShownList.get(taskIndex.getZeroBased());

        if (subtaskIndex.getZeroBased() >= taskToEdit.getSubtasks().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX);
        }
        deletedSubtask = taskToEdit.getSubtasks().get(subtaskIndex.getZeroBased());
        editedTask = createEditedTask(taskToEdit, subtaskIndex);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     */
    private static Task createEditedTask(Task taskToEdit, Index subtaskIndex) {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted oldDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        User user = taskToEdit.getUser();
        Set<Tag> updatedTags = taskToEdit.getTags();
        List<Subtask> originalSubtasks = new ArrayList<>(taskToEdit.getSubtasks());
        Status updatedStatus = taskToEdit.getStatus();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        originalSubtasks.remove(subtaskIndex.getZeroBased());
        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(originalSubtasks);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded, oldDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(), user, updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSubtaskCommand // instanceof handles nulls
                && this.taskIndex.equals(((DeleteSubtaskCommand) other).taskIndex) // state check
                && this.subtaskIndex.equals(((DeleteSubtaskCommand) other).subtaskIndex)); // state check
    }
}
```
###### /java/seedu/organizer/logic/commands/EditSubtaskCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;
import static seedu.organizer.model.subtask.UniqueSubtaskList.DuplicateSubtaskException;

import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.DateAdded;
import seedu.organizer.model.task.DateCompleted;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;

/**
 * Edit a subtask
 */
public class EditSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edits";
    public static final String COMMAND_ALIAS = "es";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit a subttask. "
            + "Parameters: TASK_INDEX (must be a positive integer) SUBTASK_INDEX (must be a positive integer)"
            + PREFIX_NAME + "NAME ";

    public static final String MESSAGE_SUCCESS = "Subtask edited: %1$s";
    public static final String MESSAGE_DUPLICATED = "Subtask already exist";

    private final Subtask toEdit;
    private final Index taskIndex;
    private final Index subtaskIndex;

    private Task taskToEdit;
    private Task editedTask;

    public EditSubtaskCommand(Index taskIndex, Index subtaskIndex, Subtask toEdit) {
        requireNonNull(toEdit);
        requireNonNull(taskIndex);
        requireNonNull(subtaskIndex);
        this.taskIndex = taskIndex;
        this.subtaskIndex = subtaskIndex;
        this.toEdit = toEdit;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            editedTask = createEditedTask(taskToEdit, subtaskIndex, toEdit);
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new AssertionError("Task duplication should not happen");
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        } catch (DuplicateSubtaskException dse) {
            throw new CommandException(MESSAGE_DUPLICATED);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToEdit = lastShownList.get(taskIndex.getZeroBased());

        if (subtaskIndex.getZeroBased() >= taskToEdit.getSubtasks().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX);
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     */
    private static Task createEditedTask(Task taskToEdit, Index subtaskIndex,
                                         Subtask toAdd) throws DuplicateSubtaskException {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted oldDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        Status updatedStatus = taskToEdit.getStatus();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(taskToEdit.getSubtasks());
        updatedSubtasks.set(subtaskIndex, toAdd);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded, oldDateCompleted,
                updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(), getCurrentlyLoggedInUser(),
                updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditSubtaskCommand // instanceof handles nulls
                && this.toEdit.equals(((EditSubtaskCommand) other).toEdit) // state check
                && this.subtaskIndex.equals(((EditSubtaskCommand) other).subtaskIndex) // state check
                && this.taskIndex.equals(((EditSubtaskCommand) other).taskIndex)); // state check
    }
}

```
###### /java/seedu/organizer/logic/commands/ToggleCommand.java
``` java
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;

import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.DateAdded;
import seedu.organizer.model.task.DateCompleted;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;

/**
 * Inverse the value of task status (Done or Not done)
 */
public class ToggleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "toggle";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggle task status. "
            + "Parameters: INDEX (must be a positive integer).\n"
            + "Example: " + COMMAND_WORD + " " + "1";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the organizer.";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Toggled Task: %1$s";

    public final Index index;

    private Task taskToEdit;
    private Task editedTask;

    /**
     * @param index                of the task in the filtered task list to edit
     */
    public ToggleCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
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
        editedTask = createEditedTask(taskToEdit);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit} with status inversed
     */
    private static Task createEditedTask(Task taskToEdit) {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted updatedDateCompleted = taskToEdit.getDateCompleted().toggle();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        List<Subtask> updatedSubtasks = taskToEdit.getSubtasks();
        Status updatedStatus = taskToEdit.getStatus().getInverse();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded,
                updatedDateCompleted, updatedDescription, updatedStatus,
                updatedTags, updatedSubtasks, getCurrentlyLoggedInUser(), updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToggleCommand // instanceof handles nulls
                && this.index.equals(((ToggleCommand) other).index)); // state check
    }
}
```
###### /java/seedu/organizer/logic/commands/ToggleSubtaskCommand.java
``` java
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.DateAdded;
import seedu.organizer.model.task.DateCompleted;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.User;

/**
 * Inverse the value of task status
 */
public class ToggleSubtaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "toggle-subtask";
    public static final String COMMAND_ALIAS = "ts";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggle a task's subtask status. "
            + "Parameters: TASK_INDEX (must be a positive integer) SUBTASK_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + "1 1";

    public static final String MESSAGE_EDIT_SUBTASK_SUCCESS = "Toogled Subtask: %1$s";

    public final Index taskIndex;
    public final Index subtaskIndex;

    private Task taskToEdit;
    private Task editedTask;
    private Subtask editedSubtask;

    /**
     * @param taskIndex index of the task in the filtered task list to edit
     * @param subtaskIndex index of the subtask of the task to edit
     */
    public ToggleSubtaskCommand(Index taskIndex, Index subtaskIndex) {
        this.taskIndex = taskIndex;
        this.subtaskIndex = subtaskIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException("This exception should not happen (duplicated task while toggling)");
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("This exception should not happen (task missing while toggling)");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_SUBTASK_SUCCESS, editedSubtask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        taskToEdit = lastShownList.get(taskIndex.getZeroBased());

        if (subtaskIndex.getZeroBased() >= taskToEdit.getSubtasks().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX);
        }
        editedTask = createEditedTask(taskToEdit, subtaskIndex);
        editedSubtask = editedTask.getSubtasks().get(subtaskIndex.getZeroBased());
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit} with status inversed
     */
    private static Task createEditedTask(Task taskToEdit, Index subtaskIndex) {
        assert taskToEdit != null;

        Name updatedName = taskToEdit.getName();
        Priority updatedPriority = taskToEdit.getUpdatedPriority();
        Priority basePriority = taskToEdit.getBasePriority();
        Deadline updatedDeadline = taskToEdit.getDeadline();
        DateAdded oldDateAdded = taskToEdit.getDateAdded();
        DateCompleted updatedDateCompleted = taskToEdit.getDateCompleted();
        Description updatedDescription = taskToEdit.getDescription();
        Set<Tag> updatedTags = taskToEdit.getTags();
        List<Subtask> originalSubtasks = new ArrayList<>(taskToEdit.getSubtasks());
        Status updatedStatus = taskToEdit.getStatus();
        User user = taskToEdit.getUser();
        Recurrence updatedRecurrence = taskToEdit.getRecurrence();

        Subtask originalSubtask = originalSubtasks.get(subtaskIndex.getZeroBased());
        Name subtaskName = originalSubtask.getName();
        Status subtaskStatus = originalSubtask.getStatus().getInverse();

        Subtask editedSubtask = new Subtask(subtaskName, subtaskStatus);
        originalSubtasks.set(subtaskIndex.getZeroBased(), editedSubtask);

        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(originalSubtasks);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded,
                updatedDateCompleted, updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(),
                user, updatedRecurrence);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToggleSubtaskCommand // instanceof handles nulls
                && this.taskIndex.equals(((ToggleSubtaskCommand) other).taskIndex) // state check
                && this.subtaskIndex.equals(((ToggleSubtaskCommand) other).subtaskIndex)); // state check
    }
}
```
###### /java/seedu/organizer/logic/commands/util/EditTaskDescriptor.java
``` java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.organizer.commons.util.CollectionUtil;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Status;

/**
 * Stores the details to edit the task with. Each non-empty field value will replace the
 * corresponding field value of the task.
 */
public class EditTaskDescriptor {
    private Name name;
    private Priority priority;
    private Deadline deadline;
    private Description description;
    private Status status;
    private Set<Tag> tags;
    private List<Subtask> subtasks;

    public EditTaskDescriptor() {
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditTaskDescriptor(EditTaskDescriptor toCopy) {
        setName(toCopy.name);
        setPriority(toCopy.priority);
        setDeadline(toCopy.deadline);
        setDescription(toCopy.description);
        setStatus(toCopy.status);
        setTags(toCopy.tags);
        setSubtasks(toCopy.subtasks);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(this.name, this.priority, this.deadline, this.description, this.status,
                this.tags);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Optional<Priority> getPriority() {
        return Optional.ofNullable(priority);
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public Optional<Deadline> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Optional<Status> getStatus() {
        return Optional.ofNullable(status);
    }

    /**
     * Sets {@code subtasks} to this object's {@code subtasks}.
     * A defensive copy of {@code subtasks} is used internally.
     */
    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = (subtasks != null) ? new ArrayList<>(subtasks) : null;
    }

    /**
     * Returns an unmodifiable subtask set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code subtasks} is null.
     */
    public Optional<List<Subtask>> getSubtasks() {
        return (subtasks != null) ? Optional.of(Collections.unmodifiableList(subtasks)) : Optional.empty();
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskDescriptor)) {
            return false;
        }

        // state check
        EditTaskDescriptor e = (EditTaskDescriptor) other;

        return getName().equals(e.getName())
                && getPriority().equals(e.getPriority())
                && getDeadline().equals(e.getDeadline())
                && getDescription().equals(e.getDescription())
                && getTags().equals(e.getTags());
    }
}
```
###### /java/seedu/organizer/logic/parser/AddSubtaskCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.AddSubtaskCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;

/**
 * Parses input arguments and creates a new AddSubtaskCommand object
 */
public class AddSubtaskCommandParser implements Parser<AddSubtaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSubtaskCommand
     * and returns an AddSubtaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSubtaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubtaskCommand.MESSAGE_USAGE));
        }

        Optional<Name> name = Optional.empty();
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        Subtask toAdd = null;
        if (name.isPresent()) {
            toAdd = new Subtask(name.get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubtaskCommand.MESSAGE_USAGE));
        }

        return new AddSubtaskCommand(index, toAdd);
    }
}
```
###### /java/seedu/organizer/logic/parser/DeleteSubtaskCommandParser.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.DeleteSubtaskCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteSubtaskCommandParser implements Parser<DeleteSubtaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSubtaskCommand parse(String args) throws ParseException {
        try {
            Index[] indexs = ParserUtil.parseSubtaskIndex(args);
            return new DeleteSubtaskCommand(indexs[0], indexs[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSubtaskCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/organizer/logic/parser/EditSubtaskCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.EditSubtaskCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;

/**
 * Parses input arguments and creates a new EditSubtaskCommand object
 */
public class EditSubtaskCommandParser implements Parser<EditSubtaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSubtaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Index[] indexs;
        try {
            indexs = ParserUtil.parseSubtaskIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSubtaskCommand.MESSAGE_USAGE));
        }

        Optional<Name> name = Optional.empty();
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        Subtask toAdd = null;
        if (name.isPresent()) {
            toAdd = new Subtask(name.get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSubtaskCommand.MESSAGE_USAGE));
        }

        return new EditSubtaskCommand(indexs[0], indexs[1], toAdd);
    }
}
```
###### /java/seedu/organizer/logic/parser/OrganizerParser.java
``` java
        case EditSubtaskCommand.COMMAND_WORD:
            return new EditSubtaskCommandParser().parse(arguments);

        case EditSubtaskCommand.COMMAND_ALIAS:
            return new EditSubtaskCommandParser().parse(arguments);

        case ToggleCommand.COMMAND_WORD:
            return new ToggleCommandParser().parse(arguments);

        case ToggleCommand.COMMAND_ALIAS:
            return new ToggleCommandParser().parse(arguments);

        case ToggleSubtaskCommand.COMMAND_WORD:
            return new ToggleSubtaskCommandParser().parse(arguments);

        case ToggleSubtaskCommand.COMMAND_ALIAS:
            return new ToggleSubtaskCommandParser().parse(arguments);
```
###### /java/seedu/organizer/logic/parser/OrganizerParser.java
``` java
        case DeleteSubtaskCommand.COMMAND_WORD:
            return new DeleteSubtaskCommandParser().parse(arguments);

        case DeleteSubtaskCommand.COMMAND_ALIAS:
            return new DeleteSubtaskCommandParser().parse(arguments);

        case AddSubtaskCommand.COMMAND_WORD:
            return new AddSubtaskCommandParser().parse(arguments);

        case AddSubtaskCommand.COMMAND_ALIAS:
            return new AddSubtaskCommandParser().parse(arguments);
```
###### /java/seedu/organizer/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code oneBasedIndex} into an array of {@code Index} and returns it. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseIndexAsArray(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        String[] rawIndex = trimmedIndex.split(" +");
        Index[] result = new Index[rawIndex.length];

        for (int i = 0; i < rawIndex.length; i++) {
            if (!StringUtil.isNonZeroUnsignedInteger(rawIndex[i])) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
            result[i] = Index.fromOneBased(Integer.parseInt(rawIndex[i]));
        }

        return result;
    }

    /**
     * Parses {@code oneBasedIndex} into an array of {@code Index} with length 2 and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseSubtaskIndex(String oneBasedIndex) throws IllegalValueException {
        Index[] result = parseIndexAsArray(oneBasedIndex);
        if (result.length != 2) {
            throw new IllegalValueException(MESSAGE_WRONG_PART_COUNT);
        }
        return result;
    }
```
###### /java/seedu/organizer/logic/parser/ToggleCommandParser.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.ToggleCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ToggleCommand object
 */
public class ToggleCommandParser implements Parser<ToggleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ToggleCommand
     * and returns an ToggleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ToggleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ToggleCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/organizer/logic/parser/ToggleSubtaskCommandParser.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.ToggleSubtaskCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ToggleCommand object
 */
public class ToggleSubtaskCommandParser implements Parser<ToggleSubtaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ToggleCommand
     * and returns an ToggleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ToggleSubtaskCommand parse(String args) throws ParseException {
        try {
            Index[] indexs = ParserUtil.parseSubtaskIndex(args);
            return new ToggleSubtaskCommand(indexs[0], indexs[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleSubtaskCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/organizer/model/subtask/Subtask.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Status;

/**
 * Represents a Subtask inside the Task.
 */
public class Subtask {
    private final Name name;
    private final Status status;

    /**
     * Contruct a {@code Subtask} with {@code false} status
     */
    public Subtask(Name name) {
        requireNonNull(name);
        this.name = name;
        this.status = null;
    }


    /**
     * Contruct a {@code Subtask}
     */
    public Subtask(Name name, Status status) {
        requireNonNull(name);
        this.name = name;
        this.status = status;
    }

    public Name getName() {
        return name;
    }

    public Status getStatus() {
        if (status == null) {
            return new Status(false);
        }
        return status;
    }

    /**
     * Two subtask is same if and only if the name and parent is the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subtask)) {
            return false;
        }
        Subtask subtask = (Subtask) o;
        return Objects.equals(name, subtask.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" [")
                .append(getStatus())
                .append("]");
        return builder.toString();
    }
}
```
###### /java/seedu/organizer/model/subtask/UniqueSubtaskList.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.DuplicateDataException;
import seedu.organizer.commons.util.CollectionUtil;

/**
 * A list of subtasks that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Subtask#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueSubtaskList implements Iterable<Subtask> {

    private final ObservableList<Subtask> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty SubtaskList.
     */
    public UniqueSubtaskList() {}

    /**
     * Creates a UniqueSubtaskList using given subtasks.
     * Enforces no nulls.
     */
    public UniqueSubtaskList(List<Subtask> subtasks) {
        requireAllNonNull(subtasks);
        internalList.addAll(subtasks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all subtasks in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public List<Subtask> toList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new ArrayList<>(internalList);
    }

    /**
     * Replaces the Subtasks in this list with those in the argument Subtask list.
     */
    public void setSubtasks(List<Subtask> subtasks) {
        requireAllNonNull(subtasks);
        internalList.setAll(subtasks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Subtask as the given argument.
     */
    public boolean contains(Subtask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Subtask to the list.
     *
     * @throws DuplicateSubtaskException if the Subtask to add is a duplicate of an existing Subtask in the list.
     */
    public void add(Subtask toAdd) throws DuplicateSubtaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSubtaskException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Count the number of specified subtask
     * @param subtask to count
     * @return number of specified subtask
     */
    public int count(Subtask subtask) {
        int result = 0;

        for (Subtask i: internalList) {
            if (i.equals(subtask)) {
                result += 1;
            }
        }

        return result;
    }

    /**
     * Set subtask in a specified index
     * @throws DuplicateSubtaskException
     */
    public void set(Index index, Subtask toReplace) throws  DuplicateSubtaskException {
        requireNonNull(toReplace);

        int subtaskCount = this.count(toReplace);
        if (internalList.get(index.getZeroBased()).equals(toReplace)) {
            subtaskCount -= 1;
        }
        if (subtaskCount > 0) {
            throw new DuplicateSubtaskException();
        }

        internalList.set(index.getZeroBased(), toReplace);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Subtask> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Subtask> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueSubtaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueSubtaskList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueSubtaskList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateSubtaskException extends DuplicateDataException {
        protected DuplicateSubtaskException() {
            super("Operation would result in duplicate subtasks");
        }
    }

```
###### /java/seedu/organizer/model/task/Status.java
``` java
/**
 * Represents a Task's status in the organizer.
 */
public class Status {

    public static final String LABEL_FOR_DONE = "Completed";
    public static final String LABEL_FOR_NOT_DONE = "Not Completed";

    public final boolean value;

    /**
     * Constructs an {@code status}.
     *
     * @param newValue a boolean indicating the status of the task.
     *                 - false: not complete
     *                 - true: complete
     */
    public Status(boolean newValue) {
        this.value = newValue;
    }

    public Status getInverse() {
        return new Status(!this.value);
    }

    @Override
    public String toString() {
        if (this.value) {
            return LABEL_FOR_DONE;
        } else {
            return LABEL_FOR_NOT_DONE;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && this.value == ((Status) other).value); // state check
    }

    @Override
    public int hashCode() {
        if (this.value) {
            return 1;
        } else {
            return 0;
        }
    }
}
```
###### /java/seedu/organizer/model/task/Task.java
``` java
    private final UniqueTagList tags;
    private final UniqueSubtaskList subtasks;
```
###### /java/seedu/organizer/model/task/Task.java
``` java
    /**
     * Another constructor with custom status and subtask
     */
    public Task(Name name, Priority updatedPriority, Priority basePriority, Deadline deadline, DateAdded dateAdded,
                DateCompleted dateCompleted, Description description, Status status, Set<Tag> tags,
                List<Subtask> subtasks, User user, Recurrence recurrence) {
        requireAllNonNull(name, updatedPriority, deadline, description, tags);
        this.name = name;
        this.updatedPriority = updatedPriority;
        this.basePriority = basePriority;
        this.deadline = deadline;
        this.dateAdded = dateAdded;
        this.dateCompleted = dateCompleted;
        this.description = description;
        this.status = status;
        this.user = user;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subtasks = new UniqueSubtaskList(subtasks);
        this.recurrence = recurrence;
    }
```
###### /java/seedu/organizer/model/task/Task.java
``` java
    public Status getStatus() {
        if (status == null) {
            return new Status(false);
        }
        return status;
    }
```
###### /java/seedu/organizer/model/task/Task.java
``` java
    public List<Subtask> getSubtasks() {
        return Collections.unmodifiableList(subtasks.toList());
    }
```
###### /java/seedu/organizer/model/task/UniqueTaskList.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.organizer.commons.util.CollectionUtil;
import seedu.organizer.model.recurrence.Recurrence;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.task.predicates.TaskByUserPredicate;
import seedu.organizer.model.user.User;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

```
###### /java/seedu/organizer/storage/XmlAdaptedSubtask.java
``` java
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Status;

/**
 * JAXB-friendly adapted version of the Subtask.
 */
public class XmlAdaptedSubtask {

    @XmlElement(required = true)
    private String subtaskName;
    @XmlElement(required = true)
    private boolean subtaskStatus;

    /**
     * Constructs an XmlAdaptedSubtask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSubtask() {}

    /**
     * Constructs a {@code XmlAdaptedSubtask} with the given {@code subtaskName} and {@code subtaskStatus}.
     */
    public XmlAdaptedSubtask(String subtaskName, Boolean subtaskStatus) {
        this.subtaskName = subtaskName;
        this.subtaskStatus = subtaskStatus;
    }

    /**
     * Converts a given Subtask into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSubtask(Subtask source) {
        subtaskName = source.getName().fullName;
        subtaskStatus = source.getStatus().value;
    }

    /**
     * Converts this jaxb-friendly adapted subtask object into the model's Subtask object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted subtask
     */
    public Subtask toModelType() throws IllegalValueException {
        if (!Name.isValidName(subtaskName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Subtask(new Name(subtaskName), new Status(subtaskStatus));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedSubtask)) {
            return false;
        }

        XmlAdaptedSubtask that = (XmlAdaptedSubtask) o;
        return subtaskStatus == that.subtaskStatus
                && Objects.equals(subtaskName, that.subtaskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtaskName, subtaskStatus);
    }
}
```
###### /java/seedu/organizer/storage/XmlAdaptedTask.java
``` java
        final List<Subtask> taskSubtasks = new ArrayList<>();
        for (XmlAdaptedSubtask subtask : subtasks) {
            taskSubtasks.add(subtask.toModelType());
        }
```
###### /java/seedu/organizer/ui/TaskCard.java
``` java
    /**
     * Creates the subtask for {@code task}.
     */
    private void initSubtask(Task task) {
        task.getSubtasks().forEach(subtask-> {
            Label subtaskLabel = new Label(subtask.toString());
            subtasks.getItems().add(subtaskLabel);
        });
        subtasks.setPrefHeight(10 + CELL_HEIGHT * task.getSubtasks().size());
    }
```
