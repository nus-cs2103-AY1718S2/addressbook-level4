# CYX28
###### \java\seedu\address\commons\events\model\TaskBookChangedEvent.java
``` java
/** Indicates the TaskBook in the model has changed */
public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public TaskBookChangedEvent(ReadOnlyTaskBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of categories "
                + data.getTaskCategoryList().size();
    }

}
```
###### \java\seedu\address\commons\util\DateUtil.java
``` java
    /**
     * Get today's date in LocalDate format
     * @return today's date in LocalDate format
     */
    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    /**
     * Parse given date into LocalDate format
     * @param dateToParse date must be in String format with hyphens e.g. 2018-05-12
     * @return date in LocalDate format
     */
    public static LocalDate getParsedDate(String dateToParse) {
        return LocalDate.parse(dateToParse);
    }

    /**
     * Parse given dateTime into LocalDate format
     * @param dateTimeToParse date must be in String format with hyphens e.g. 2018-05-12 1800
     * @return date in LocalDate format
     */
    public static LocalDate getParsedDateTime(String dateTimeToParse) {
        return LocalDate.parse(dateTimeToParse.substring(0, 10));
    }

    /**
     * Get the number of days between the start and end date
     * @param startDate a date in LocalDate format
     * @param endDate a date in LocalDate format
     * @return number of days in between the two dates
     */
    public static int getDayCountBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
        return (int) DAYS.between(startDate, endDate);
    }

```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts alphabetically by name and lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "sn";
    public static final String MESSAGE_SUCCESS = "Listed all persons sorted alphabetically by name";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\TaskAddCommand.java
``` java
/**
 * Adds a task to the address book.
 */
public class TaskAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task-add";
    public static final String COMMAND_ALIAS = "ta";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: "
            + PREFIX_TASK_NAME + "NAME "
            + PREFIX_TASK_PRIORITY + "PRIORITY "
            + PREFIX_TASK_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TASK_DUE_DATE + "DUE DATE "
            + PREFIX_TASK_STATUS + "STATUS "
            + "[" + PREFIX_TASK_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_NAME + "Agenda for meeting "
            + PREFIX_TASK_PRIORITY + "high "
            + PREFIX_TASK_DESCRIPTION + "Discuss proposal details "
            + PREFIX_TASK_DUE_DATE + "2018-04-29 "
            + PREFIX_TASK_STATUS + "undone "
            + PREFIX_TASK_CATEGORY + "meeting";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Creates a TaskAddCommand to add the specified {@code Task}
     */
    public TaskAddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskAddCommand
                && toAdd.equals(((TaskAddCommand) other).toAdd));
    }

}
```
###### \java\seedu\address\logic\commands\TaskClearCommand.java
``` java
/**
 * Clears the task book.
 */
public class TaskClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task-clear";
    public static final String COMMAND_ALIAS = "tc";
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new TaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\address\logic\commands\TaskDeleteCommand.java
``` java
/**
 * Deletes a task identified using its last displayed index from the address book.
 */
public class TaskDeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task-delete";
    public static final String COMMAND_ALIAS = "td";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    private Task taskToDelete;

    public TaskDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(taskToDelete);
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskDeleteCommand
                && this.targetIndex.equals(((TaskDeleteCommand) other).targetIndex)
                && Objects.equals(this.taskToDelete, ((TaskDeleteCommand) other).taskToDelete));
    }

}
```
###### \java\seedu\address\logic\commands\TaskEditCommand.java
``` java
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
```
###### \java\seedu\address\logic\commands\TaskFindCommand.java
``` java
/**
 * Finds and lists all tasks in address book whose task name contains any of the argument keywords.
 * Keyword matching is case insensitive and allows partial word matching.
 */
public class TaskFindCommand extends Command {

    public static final String COMMAND_WORD = "task-find";
    public static final String COMMAND_ALIAS = "tf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of the "
            + "specified keywords (case-insensitive and partial word matching) and displays them as a list with "
            + "index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " agenda buy meeting";

    private final TaskNameContainsKeywordsPredicate predicate;

    public TaskFindCommand(TaskNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(predicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskFindCommand
                && this.predicate.equals(((TaskFindCommand) other).predicate));
    }

}
```
###### \java\seedu\address\logic\commands\TaskListCommand.java
``` java
/**
 * Lists all tasks in the address book to the user.
 */
public class TaskListCommand extends Command {

    public static final String COMMAND_WORD = "task-list";
    public static final String COMMAND_ALIAS = "tl";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks in Address Book.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_TASK_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TASK_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_TASK_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TASK_DUE_DATE = new Prefix("dd/");
    public static final Prefix PREFIX_TASK_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_TASK_CATEGORY = new Prefix("c/");

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String taskName} into a {@code TaskName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskName} is invalid.
     */
    public static TaskName parseTaskName(String taskName) throws IllegalValueException {
        requireNonNull(taskName);
        String trimmedTaskName = taskName.trim();
        if (!TaskName.isValidTaskName(trimmedTaskName)) {
            throw new IllegalValueException(TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);
        }
        return new TaskName(trimmedTaskName);
    }

    /**
     * Parses a {@code Optional<String> taskName} into an {@code Optional<TaskName>} if {@code taskName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskName> parseTaskName(Optional<String> taskName) throws IllegalValueException {
        requireNonNull(taskName);
        return taskName.isPresent() ? Optional.of(parseTaskName(taskName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskPriority} into a {@code TaskPriority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskPriority} is invalid.
     */
    public static TaskPriority parseTaskPriority(String taskPriority) throws IllegalValueException {
        requireNonNull(taskPriority);
        String trimmedTaskPriority = taskPriority.trim();
        if (!TaskPriority.isValidTaskPriority(trimmedTaskPriority)) {
            throw new IllegalValueException(TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        }
        return new TaskPriority(trimmedTaskPriority);
    }

    /**
     * Parses a {@code Optional<String> taskPriority} into an {@code Optional<TaskPriority>}
     * if {@code taskPriority} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskPriority> parseTaskPriority(Optional<String> taskPriority) throws IllegalValueException {
        requireNonNull(taskPriority);
        return taskPriority.isPresent() ? Optional.of(parseTaskPriority(taskPriority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskDescription} into a {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskDescription} is invalid.
     */
    public static TaskDescription parseTaskDescription(String taskDescription) throws IllegalValueException {
        requireNonNull(taskDescription);
        String trimmedTaskDescription = taskDescription.trim();
        if (!TaskDescription.isValidTaskDescription(trimmedTaskDescription)) {
            throw new IllegalValueException(TaskDescription.MESSAGE_TASK_DESCRIPTION_CONSTRAINTS);
        }
        return new TaskDescription(trimmedTaskDescription);
    }

    /**
     * Parses a {@code Optional<String> taskDescription} into an {@code Optional<TaskDescription>}
     * if {@code taskDescription} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskDescription> parseTaskDescription(Optional<String> taskDescription)
            throws IllegalValueException {
        requireNonNull(taskDescription);
        return taskDescription.isPresent()
                ? Optional.of(parseTaskDescription(taskDescription.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskDueDate} into a {@code TaskDueDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskDueDate} is invalid.
     */
    public static TaskDueDate parseTaskDueDate(String taskDueDate) throws IllegalValueException {
        requireNonNull(taskDueDate);
        String trimmedTaskDueDate = taskDueDate.trim();
        if (!TaskDueDate.isValidTaskDueDate(trimmedTaskDueDate)) {
            throw new IllegalValueException(TaskDueDate.MESSAGE_TASK_DUE_DATE_CONSTRAINTS);
        }
        return new TaskDueDate(trimmedTaskDueDate);
    }

    /**
     * Parses a {@code Optional<String> taskDueDate} into an {@code Optional<TaskDueDate>}
     * if {@code taskDueDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskDueDate> parseTaskDueDate(Optional<String> taskDueDate) throws IllegalValueException {
        requireNonNull(taskDueDate);
        return taskDueDate.isPresent() ? Optional.of(parseTaskDueDate(taskDueDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskStatus} into a {@code TaskStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskStatus} is invalid.
     */
    public static TaskStatus parseTaskStatus(String taskStatus) throws IllegalValueException {
        requireNonNull(taskStatus);
        String trimmedTaskStatus = taskStatus.trim();
        if (!TaskStatus.isValidTaskStatus(trimmedTaskStatus)) {
            throw new IllegalValueException(TaskStatus.MESSAGE_TASK_STATUS_CONSTRAINTS);
        }
        return new TaskStatus(trimmedTaskStatus);
    }

    /**
     * Parses a {@code Optional<String> taskStatus} into an {@code Optional<TaskStatus>}
     * if {@code taskStatus} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TaskStatus> parseTaskStatus(Optional<String> taskStatus) throws IllegalValueException {
        requireNonNull(taskStatus);
        return taskStatus.isPresent() ? Optional.of(parseTaskStatus(taskStatus.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String taskCategory} into a {@code TaskCategory}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code taskCategory} is invalid.
     */
    public static TaskCategory parseTaskCategory(String taskCategory) throws IllegalValueException {
        requireNonNull(taskCategory);
        String trimmedTaskCategory = taskCategory.trim().toLowerCase();
        if (!TaskCategory.isValidTaskCategoryName(trimmedTaskCategory)) {
            throw new IllegalValueException(TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        }
        return new TaskCategory(trimmedTaskCategory);
    }

    /**
     * Parses {@code Collection<String> taskCategories} into a {@code Set<TaskCategory>}.
     */
    public static Set<TaskCategory> parseTaskCategories(Collection<String> taskCategories)
            throws IllegalValueException {
        requireNonNull(taskCategories);
        final Set<TaskCategory> taskCategorySet = new HashSet<>();
        for (String taskCategoryName : taskCategories) {
            taskCategorySet.add(parseTaskCategory(taskCategoryName));
        }
        return taskCategorySet;
    }

```
###### \java\seedu\address\logic\parser\TaskAddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TaskAddCommand object
 */
public class TaskAddCommandParser implements Parser<TaskAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskAddCommand
     * and returns an TaskAddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TaskAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_PRIORITY,
                PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DUE_DATE, PREFIX_TASK_STATUS, PREFIX_TASK_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME, PREFIX_TASK_PRIORITY, PREFIX_TASK_DESCRIPTION,
                PREFIX_TASK_DUE_DATE, PREFIX_TASK_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE));
        }

        try {
            TaskName taskName =
                    ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME)).get();
            TaskPriority taskPriority =
                    ParserUtil.parseTaskPriority(argMultimap.getValue(PREFIX_TASK_PRIORITY)).get();
            TaskDescription taskDescription =
                    ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX_TASK_DESCRIPTION)).get();
            TaskDueDate taskDueDate =
                    ParserUtil.parseTaskDueDate(argMultimap.getValue(PREFIX_TASK_DUE_DATE)).get();
            TaskStatus taskStatus =
                    ParserUtil.parseTaskStatus(argMultimap.getValue(PREFIX_TASK_STATUS)).get();
            Set<TaskCategory> taskCategoryList =
                    ParserUtil.parseTaskCategories(argMultimap.getAllValues(PREFIX_TASK_CATEGORY));
            Task task = new Task(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, taskCategoryList);

            return new TaskAddCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\TaskDeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TaskDeleteCommand object.
 */
public class TaskDeleteCommandParser implements Parser<TaskDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskDeleteCommand
     * and returns a TaskDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TaskDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TaskDeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDeleteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\TaskEditCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TaskEditCommand object
 */
public class TaskEditCommandParser implements Parser<TaskEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskEditCommand
     * and returns a TaskEditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public TaskEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_PRIORITY,
                PREFIX_TASK_DESCRIPTION, PREFIX_TASK_DUE_DATE, PREFIX_TASK_STATUS, PREFIX_TASK_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskEditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_TASK_NAME))
                    .ifPresent(editTaskDescriptor::setTaskName);
            ParserUtil.parseTaskPriority(argMultimap.getValue(PREFIX_TASK_PRIORITY))
                    .ifPresent(editTaskDescriptor::setTaskPriority);
            ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX_TASK_DESCRIPTION))
                    .ifPresent(editTaskDescriptor::setTaskDescription);
            ParserUtil.parseTaskDueDate(argMultimap.getValue(PREFIX_TASK_DUE_DATE))
                    .ifPresent(editTaskDescriptor::setTaskDueDate);
            ParserUtil.parseTaskStatus(argMultimap.getValue(PREFIX_TASK_STATUS))
                    .ifPresent(editTaskDescriptor::setTaskStatus);
            parseTaskCategoriesForEdit(argMultimap.getAllValues(PREFIX_TASK_CATEGORY))
                    .ifPresent(editTaskDescriptor::setTaskCategories);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TaskEditCommand.MESSAGE_NOT_EDITED);
        }

        return new TaskEditCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> taskCategories} into a {@code Set<TaskCategory>}
     * if {@code taskCategories} is non-empty.
     * If {@code taskCategories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<TaskCategory>} containing zero taskCategories.
     */
    private Optional<Set<TaskCategory>> parseTaskCategoriesForEdit(Collection<String> taskCategories)
            throws IllegalValueException {
        assert taskCategories != null;

        if (taskCategories.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> taskCategorySet =
                taskCategories.size() == 1 && taskCategories.contains("") ? Collections.emptySet() : taskCategories;
        return Optional.of(ParserUtil.parseTaskCategories(taskCategorySet));
    }

}
```
###### \java\seedu\address\logic\parser\TaskFindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TaskFindCommand object
 */
public class TaskFindCommandParser implements Parser<TaskFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskFindCommand
     * and returns a TaskFindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TaskFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskFindCommand.MESSAGE_USAGE));
        }

        String[] taskNameKeywords = trimmedArgs.split("\\s+");

        return new TaskFindCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList(taskNameKeywords)));
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the person list alphabetically by name
     */
    public void sortPersons() {
        persons.sort();
    }

```
###### \java\seedu\address\model\category\TaskCategory.java
``` java
/**
 * Represents a TaskCategory in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTaskCategoryName(String)}
 */
public class TaskCategory {

    public static final String MESSAGE_TASK_CATEGORY_CONSTRAINTS =
            "Task category names should be alphanumeric, and does not contain any whitespaces";
    public static final String TASK_CATEGORY_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String taskCategoryName;

    /**
     * Constructs a {@code TaskCategory}.
     *
     * @param taskCategoryName A valid task category name.
     */
    public TaskCategory(String taskCategoryName) {
        requireNonNull(taskCategoryName);
        checkArgument(isValidTaskCategoryName(taskCategoryName), MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        this.taskCategoryName = taskCategoryName;
    }

    /**
     * Returns true if a given string is a valid task category name.
     */
    public static boolean isValidTaskCategoryName(String test) {
        return test.matches(TASK_CATEGORY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskCategory
                && this.taskCategoryName.equals(((TaskCategory) other).taskCategoryName));
    }

    @Override
    public int hashCode() {
        return taskCategoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + taskCategoryName + ']';
    }

}
```
###### \java\seedu\address\model\category\UniqueTaskCategoryList.java
``` java
/** A list of categories that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see TaskCategory#equals(Object)
 */
public class UniqueTaskCategoryList implements Iterable<TaskCategory> {

    private final ObservableList<TaskCategory> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TaskCategoryList.
     */
    public UniqueTaskCategoryList() {}

    /**
     * Creates a UniqueTaskCategoryList using given taskCategories.
     * Enforces no nulls.
     */
    public UniqueTaskCategoryList(Set<TaskCategory> categories) {
        requireAllNonNull(categories);
        internalList.addAll(categories);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all taskCategories in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<TaskCategory> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the TaskCategories in this list with those in the argument taskCategory list.
     */
    public void setTaskCategories(Set<TaskCategory> taskCategories) {
        requireAllNonNull(taskCategories);
        internalList.setAll(taskCategories);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every taskCategory in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTaskCategoryList from) {
        final Set<TaskCategory> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(taskCategory -> !alreadyInside.contains(taskCategory))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent TaskCategory as the given argument.
     */
    public boolean contains(TaskCategory toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a TaskCategory to the list.
     *
     * @throws DuplicateTaskCategoryException if the TaskCategory to add
     * is a duplicate of an existing TaskCategory in the list.
     */
    public void add(TaskCategory toAdd) throws DuplicateTaskCategoryException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskCategoryException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<TaskCategory> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TaskCategory> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this
                || (other instanceof UniqueTaskCategoryList
                && this.internalList.equals(((UniqueTaskCategoryList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueTaskCategoryList other) {
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
    public static class DuplicateTaskCategoryException extends DuplicateDataException {
        protected DuplicateTaskCategoryException() {
            super("Operation would result in duplicate taskCategories");
        }
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPersons() {
        this.addressBook.sortPersons();
        indicateAddressBookChanged();
    }

    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void deleteTask(Task target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public void updateTask(Task target, Task editedTask) throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        taskBook.updateTask(target, editedTask);
        indicateTaskBookChanged();
    }

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts all persons in list alphabetically by name
     */
    public void sort() {
        internalList.sort((person1, person2) -> person1.getName().toString()
                .compareToIgnoreCase(person2.getName().toString()));
    }

```
###### \java\seedu\address\model\ReadOnlyTaskBook.java
``` java
/**
 * Unmodifiable view of a task book
 */
public interface ReadOnlyTaskBook {

    /**
     * Returns an unmodifiable view of the tasks list before sorting.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getOriginalTaskList();

    /**
     * Returns an unmodifiable view of the tasks list sorted by task status (i.e. undone and done)
     * followed by taskDueDate.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns an unmodifiable view of the taskCategories list.
     * This list will not contain any duplicate taskCategories.
     */
    ObservableList<TaskCategory> getTaskCategoryList();

}
```
###### \java\seedu\address\model\task\Task.java
``` java
/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {
    private final TaskName taskName;
    private final TaskPriority taskPriority;
    private final TaskDescription taskDescription;
    private final TaskDueDate taskDueDate;
    private final TaskStatus taskStatus;

    private final UniqueTaskCategoryList taskCategories;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, TaskPriority taskPriority, TaskDescription taskDescription, TaskDueDate taskDueDate,
                TaskStatus taskStatus, Set<TaskCategory> taskCategories) {
        requireAllNonNull(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, taskCategories);
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskDescription = taskDescription;
        this.taskDueDate = taskDueDate;
        this.taskStatus = taskStatus;
        // protect internal categories from changes in the arg list
        this.taskCategories = new UniqueTaskCategoryList(taskCategories);
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public TaskDescription getTaskDescription() {
        return taskDescription;
    }

    public TaskDueDate getTaskDueDate() {
        return taskDueDate;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<TaskCategory> getTaskCategories() {
        return Collections.unmodifiableSet(taskCategories.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTaskName().equals(this.getTaskName())
                && otherTask.getTaskPriority().equals(this.getTaskPriority())
                && otherTask.getTaskDescription().equals(this.getTaskDescription())
                && otherTask.getTaskDueDate().equals(this.getTaskDueDate())
                && otherTask.getTaskStatus().equals(this.getTaskStatus())
                && otherTask.getTaskCategories().equals(this.getTaskCategories());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, taskPriority, taskDescription, taskDueDate, taskStatus, taskCategories);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Priority: ")
                .append(getTaskPriority())
                .append(" Description: ")
                .append(getTaskDescription())
                .append(" Due Date: ")
                .append(getTaskDueDate())
                .append(" Status: ")
                .append(getTaskStatus())
                .append(" Categories: ");
        getTaskCategories().forEach(builder::append);
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\task\TaskDescription.java
``` java
/**
 * Represents a Task's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskDescription(String)}
 */
public class TaskDescription {

    public static final String MESSAGE_TASK_DESCRIPTION_CONSTRAINTS =
            "Task descriptions can contain any character, but it should not be blank";

    /**
     * The first character of the task description must not be a whitespace,
     * otherwise " " ( a blank string) becomes a valid input.
     */
    public static final String TASK_DESCRIPTION_VALIDATION_REGEX = "^\\S+[\\s\\S ]*";

    public final String value;

    /**
     * Constructs a {@code TaskDescription}.
     *
     * @param taskDescription A valid task description.
     */
    public TaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        checkArgument(isValidTaskDescription(taskDescription), MESSAGE_TASK_DESCRIPTION_CONSTRAINTS);
        this.value = taskDescription;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidTaskDescription(String test) {
        return test.matches(TASK_DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskDescription
                && this.value.equals(((TaskDescription) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\task\TaskDueDate.java
``` java
/**
 * Represents a Task's due date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskDueDate(String)}
 */
public class TaskDueDate {

    public static final String MESSAGE_TASK_DUE_DATE_CONSTRAINTS =
            "Task due dates must be a valid date in the format yyyy-MM-dd, and it should not be empty";

    /**
     * The first character of the task due date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * This regex checks for years from 1900 to 9999 and also leap year.
     */
    public static final String TASK_DUE_DATE_VALIDATION_REGEX =
            "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$|"
                    + "^(((19|[2-9][0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$|"
                    + "^(((19|[2-9][0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$|"
                    + "^(((19|[2-9][0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";

    public final String value;

    /**
     * Constructs a {@code TaskDueDate}.
     *
     * @param taskDueDate A valid task due date.
     */
    public TaskDueDate(String taskDueDate) {
        requireNonNull(taskDueDate);
        checkArgument(isValidTaskDueDate(taskDueDate), MESSAGE_TASK_DUE_DATE_CONSTRAINTS);
        this.value = taskDueDate;
    }

    /**
     * Returns true if a given string is a valid task due date.
     */
    public static boolean isValidTaskDueDate(String test) {
        return test.matches(TASK_DUE_DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskDueDate
                && this.value.equals(((TaskDueDate) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\task\TaskName.java
``` java
/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskName(String)}
 */
public class TaskName {

    public static final String MESSAGE_TASK_NAME_CONSTRAINTS =
            "Task names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * The first character of the task name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TASK_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code TaskName}.
     *
     * @param taskName A valid task name.
     */
    public TaskName(String taskName) {
        requireNonNull(taskName);
        checkArgument(isValidTaskName(taskName), MESSAGE_TASK_NAME_CONSTRAINTS);
        this.value = taskName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidTaskName(String test) {
        return test.matches(TASK_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskName
                && this.value.equals(((TaskName) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\task\TaskNameContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code Task}'s {@code Name} matches any of the keywords given.
 */
public class TaskNameContainsKeywordsPredicate implements Predicate<Task> {

    private final List<String> keywords;

    public TaskNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskNameContainsKeywordsPredicate
                && this.keywords.equals(((TaskNameContainsKeywordsPredicate) other).keywords));
    }

}
```
###### \java\seedu\address\model\task\TaskPriority.java
``` java
/**
 * Represents a Task's priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskPriority(String)}
 */
public class TaskPriority {

    public static final String TASK_PRIORITY_HIGH = "high";
    public static final String TASK_PRIORITY_MEDIUM = "medium";
    public static final String TASK_PRIORITY_LOW = "low";

    public static final List<String> PRIORITY_ORDER =
            Arrays.asList(TASK_PRIORITY_HIGH, TASK_PRIORITY_MEDIUM, TASK_PRIORITY_LOW);

    public static final String MESSAGE_TASK_PRIORITY_CONSTRAINTS =
            "Task priority can only be either high, medium or low, and it should not be empty";

    /**
     * The first character of the task priority must not be a whitespace,
     * otherwise " " ( a blank string) becomes a valid input.
     * The valid values for task priority can start with or without capital letter.
     */
    public static final String TASK_PRIORITY_VALIDATION_REGEX = "^(high|medium|low)$";

    public final String value;

    /**
     * Constructs a {@code TaskPriority}
     *
     * @param taskPriority A valid task priority.
     */
    public TaskPriority(String taskPriority) {
        requireNonNull(taskPriority);
        checkArgument(isValidTaskPriority(taskPriority), MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        this.value = taskPriority;
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidTaskPriority(String test) {
        return test.matches(TASK_PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskPriority
                && this.value.equals(((TaskPriority) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\task\TaskStatus.java
``` java
/**
 * Represents a Task's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskStatus(String)}
 */
public class TaskStatus {

    public static final String TASK_STATUS_DONE = "done";
    public static final String TASK_STATUS_UNDONE = "undone";

    public static final List<String> STATUS_ORDER =
            Arrays.asList(TASK_STATUS_UNDONE, TASK_STATUS_DONE);

    public static final String MESSAGE_TASK_STATUS_CONSTRAINTS =
            "Task status can only be either done or undone, and it should not be empty.";

    /**
     * The first character of the task status must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The valid values for task status can start with or without capital letter.
     */
    public static final String TASK_STATUS_VALIDATION_REGEX = "^(done|undone)$";

    public final String value;

    /**
     * Constructs a {@code TaskStatus}
     *
     * @param taskStatus A valid task status.
     */
    public TaskStatus(String taskStatus) {
        requireNonNull(taskStatus);
        checkArgument(isValidTaskStatus(taskStatus), MESSAGE_TASK_STATUS_CONSTRAINTS);
        this.value = taskStatus;
    }

    /**
     * Returns true if a given string is a valid task status.
     */
    public static boolean isValidTaskStatus(String test) {
        return test.matches(TASK_STATUS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskStatus
                && this.value.equals(((TaskStatus) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
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

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(Task target, Task editedTask) throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    /**
     * Sorts the task list by
     * (1) status (i.e. undone to done,
     * (2) due date in ascending order and
     * (3) priority (i.e. high > medium > low)
     */
    public void sortByStatusDueDateAndPriority() {
        internalList.sort(Comparator.comparing(Task::getTaskStatus, (s1, s2) -> {
            return TaskStatus.STATUS_ORDER.indexOf(s1.value.toLowerCase())
                    - TaskStatus.STATUS_ORDER.indexOf(s2.value.toLowerCase());
        }).thenComparing(Comparator.comparing(Task::getTaskDueDate, (dd1, dd2) -> {
            return dd1.value.compareTo(dd2.value);
        })).thenComparing(Comparator.comparing(Task::getTaskPriority, (p1, p2) -> {
            return TaskPriority.PRIORITY_ORDER.indexOf(p1.value.toLowerCase())
                    - TaskPriority.PRIORITY_ORDER.indexOf(p2.value.toLowerCase());
        })));
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<Task> tasks) throws DuplicateTaskException {
        requireAllNonNull(tasks);
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final Task task : tasks) {
            replacement.add(task);
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
```
###### \java\seedu\address\model\TaskBook.java
``` java
/**
 * Wraps all data at the task-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueTaskCategoryList taskCategories;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        taskCategories = new UniqueTaskCategoryList();
    }

    public TaskBook() {}

    /**
     * Creates a TaskBook using the Tasks and TaskCategories in the {@code toBeCopied}
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<Task> tasks) throws DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTaskCategories(Set<TaskCategory> taskCategories) {
        this.taskCategories.setTaskCategories(taskCategories);
    }

    /**
     * Resets the existing data of this {@code TaskBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskBook newData) {
        requireNonNull(newData);
        setTaskCategories(new HashSet<>(newData.getTaskCategoryList()));
        List<Task> syncedTaskList = newData.getTaskList().stream()
                .map(this::syncWithMasterTaskCategoryList)
                .collect(Collectors.toList());

        try {
            setTasks(syncedTaskList);
        } catch (DuplicateTaskException e) {
            throw new AssertionError("Taskbooks should not have duplicate tasks");
        }
    }

    //// task-level operations

    /**
     * Adds a task to the task book.
     * Also checks the new task's categories and updates {@link #taskCategories} with any new taskCategories found,
     * and updates the TaskCategory objects in the task to point to those in {@link #taskCategories}.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws DuplicateTaskException {
        Task task = syncWithMasterTaskCategoryList(t);
        tasks.add(task);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code TaskBook}'s taskCategory list will be updated with the taskCategories of {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     * another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTaskCategoryList(Task)
     */
    public void updateTask(Task target, Task editedTask) throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        Task syncedEditedTask = syncWithMasterTaskCategoryList(editedTask);
        tasks.setTask(target, syncedEditedTask);
    }

    /**
     * Updates the master taskCategory list to include taskCategories in {@code task} that are not in the list.
     * @return a copy of this {@code task} such that every taskCategory in this task points to a TaskCategory object
     * in the master list.
     */
    private Task syncWithMasterTaskCategoryList(Task task) {
        final UniqueTaskCategoryList categories = new UniqueTaskCategoryList(task.getTaskCategories());
        taskCategories.mergeFrom(categories);

        // Create map with values = taskCategory object references in the master list
        // Used for checking task category references
        final Map<TaskCategory, TaskCategory> masterTaskCategoryObjects = new HashMap<>();
        taskCategories.forEach(taskCategory -> masterTaskCategoryObjects.put(taskCategory, taskCategory));

        // Rebuild the list of task categories to point to the relevant taskCategories in the master taskCategory list.
        final Set<TaskCategory> correctTaskCategoryReferences = new HashSet<>();
        categories.forEach(taskCategory -> correctTaskCategoryReferences.add(
                masterTaskCategoryObjects.get(taskCategory)));
        return new Task(task.getTaskName(), task.getTaskPriority(), task.getTaskDescription(), task.getTaskDueDate(),
                task.getTaskStatus(), correctTaskCategoryReferences);
    }

    /**
     * Removes {@code key} from this {@code TaskBook}.
     * @throws TaskNotFoundException if the {@code key} is not in this {@code TaskBook}.
     */
    public boolean removeTask(Task key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Sorts the task list by
     * (1) status (i.e. undone to done,
     * (2) due date in ascending order and
     * (3) priority (i.e. high > medium > low)
     */
    public void sortTasksByStatusDueDateAndPriority() {
        tasks.sortByStatusDueDateAndPriority();
    }

    //// taskCategory-level operations

    public void addTaskCategory(TaskCategory tc) throws UniqueTaskCategoryList.DuplicateTaskCategoryException {
        taskCategories.add(tc);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + taskCategories.asObservableList().size()
                + " task categories";
    }

    @Override
    public ObservableList<Task> getOriginalTaskList() {
        return tasks.asObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        sortTasksByStatusDueDateAndPriority();
        return tasks.asObservableList();
    }

    @Override
    public ObservableList<TaskCategory> getTaskCategoryList() {
        return taskCategories.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks)
                && this.taskCategories.equalsOrderInsensitive(((TaskBook) other).taskCategories));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, taskCategories);
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new TaskName("Programming Project"), new TaskPriority("low"),
                new TaskDescription("(1) Analyse requirements (2) Write programs (3) Testing (4) Documentation "
                    + "(5) Submit to boss"), new TaskDueDate("2018-04-10"), new TaskStatus("undone"),
                getTaskCategorySet("personal", "interest")),
            new Task(new TaskName("Project meeting with the group and department"), new TaskPriority("high"),
                new TaskDescription("Finalise on project features"),
                new TaskDueDate("2018-04-22"), new TaskStatus("undone"),
                getTaskCategorySet("meeting", "ahighprofileprojectthatcannotbedelayedanymore")),
            new Task(new TaskName("Follow up with boss"), new TaskPriority("medium"),
                new TaskDescription("Present proposal to boss regarding project concerns"),
                new TaskDueDate("2018-04-25"), new TaskStatus("undone"),
                getTaskCategorySet("business", "work", "meeting", "boss", "proposal")),
            new Task (new TaskName("Agenda for business meeting"), new TaskPriority("high"),
                new TaskDescription("Discuss proposal details"),
                new TaskDueDate("2018-04-27"), new TaskStatus("undone"),
                getTaskCategorySet("meeting")),
            new Task(new TaskName("Clarify with client"), new TaskPriority("medium"),
                new TaskDescription("Clarify client's expectation of the project"),
                new TaskDueDate("2018-04-28"), new TaskStatus("undone"),
                getTaskCategorySet("work", "meeting", "client")),
            new Task(new TaskName("Buy birthday gift"), new TaskPriority("low"),
                new TaskDescription("Buy a gift for cousin's birthday"),
                new TaskDueDate("2018-04-30"), new TaskStatus("undone"),
                getTaskCategorySet("personal")),
            new Task(new TaskName("Audit department records"), new TaskPriority("high"),
                new TaskDescription("Preparation for monthly report"),
                new TaskDueDate("2018-04-30"), new TaskStatus("undone"),
                getTaskCategorySet("work")),
            new Task(new TaskName("Submit department report"), new TaskPriority("high"),
                new TaskDescription("Monthly report from the department"),
                new TaskDueDate("2018-05-01"), new TaskStatus("undone"),
                getTaskCategorySet("work")),
            new Task(new TaskName("Organizing of department cohesion event"), new TaskPriority("medium"),
                new TaskDescription("Liaising with finance department"),
                new TaskDueDate("2018-05-01"), new TaskStatus("undone"),
                getTaskCategorySet("work")),
            new Task(new TaskName("Collect Taobao Package"), new TaskPriority("low"),
                new TaskDescription("Taobao delivery on this day"),
                new TaskDueDate("2018-05-03"), new TaskStatus("undone"),
                getTaskCategorySet("personal"))
        };
    }

    public static ReadOnlyTaskBook getSampleTaskBook() {
        try {
            TaskBook sampleTb = new TaskBook();
            for (Task sampleTask : getSampleTasks()) {
                sampleTb.addTask(sampleTask);
            }
            return sampleTb;
        } catch (DuplicateTaskException dte) {
            throw new AssertionError("sample data cannot contain duplicate tasks", dte);
        }
    }

    /**
     * Returns a task category set containing the list of strings given.
     */
    public static Set<TaskCategory> getTaskCategorySet(String... strings) {
        HashSet<TaskCategory> taskCategories = new HashSet<>();
        for (String s : strings) {
            taskCategories.add(new TaskCategory(s));
        }

        return taskCategories;
    }

```
###### \java\seedu\address\storage\TaskBookStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.TaskBook}.
 */
public interface TaskBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskBookFilePath();

    /**
     * Returns TaskBook data as a {@link ReadOnlyTaskBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    /**
     * @see #getTaskBookFilePath()
     */
    Optional<ReadOnlyTaskBook> readTaskBook(String filePath)
            throws DataConversionException, IOException, JAXBException;

    /**
     * Saves the given {@link ReadOnlyTaskBook} to the storage.
     * @param taskBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException, InvalidFileException;

    /**
     * @see #saveTaskBook(ReadOnlyTaskBook)
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException;

    /**
     * @see #exportTaskBook()
     */
    void exportTaskBook() throws ParserConfigurationException, IOException, TransformerException;

    /**
     * Backups the given {@link ReadOnlyTaskBook} to the storage.
     * @param taskBook cannot be null.
     * @throws IOException if there was any problem backing up the file.
     */
    void backupTaskBook(ReadOnlyTaskBook taskBook) throws IOException, InvalidFileException;

}
```
###### \java\seedu\address\storage\XmlAdaptedTask.java
``` java
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
```
###### \java\seedu\address\storage\XmlAdaptedTaskCategory.java
``` java
/**
 * JAXB-friendly version of the Task Category.
 */
public class XmlAdaptedTaskCategory {

    @XmlValue
    private String taskCategoryName;

    /**
     * Constructs an XmlAdaptedTaskCategory.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTaskCategory() {}

    /**
     * Constructs a {@code XmlAdaptedTaskCategory} with the given {@code taskCategoryName}.
     */
    public XmlAdaptedTaskCategory(String taskCategoryName) {
        this.taskCategoryName = taskCategoryName;
    }

    /**
     * Converts a given TaskCategory into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTaskCategory.
     */
    public XmlAdaptedTaskCategory(TaskCategory source) {
        taskCategoryName = source.taskCategoryName;
    }

    /**
     * Converts this jaxb-friendly adapted taskCategory object into the model's TaskCategory object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public TaskCategory toModelType() throws IllegalValueException {
        if (!TaskCategory.isValidTaskCategoryName(taskCategoryName)) {
            throw new IllegalValueException(TaskCategory.MESSAGE_TASK_CATEGORY_CONSTRAINTS);
        }
        return new TaskCategory(taskCategoryName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTaskCategory)) {
            return false;
        }

        return taskCategoryName.equals(((XmlAdaptedTaskCategory) other).taskCategoryName);
    }

}
```
###### \java\seedu\address\storage\XmlSerializableTaskBook.java
``` java
/**
 * An Immutable TaskBook that is serializable to XML format
 */
@XmlRootElement(name = "taskbook")
public class XmlSerializableTaskBook {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTaskCategory> taskCategories;

    /**
     * Creates an empty XmlSerializableTaskBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskBook() {
        tasks = new ArrayList<>();
        taskCategories = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskBook(ReadOnlyTaskBook src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        taskCategories.addAll(src.getTaskCategoryList().stream().map(XmlAdaptedTaskCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this taskbook into the model's {@code TaskBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTask} or {@code XmlAdaptedTaskCategory}.
     */
    public TaskBook toModelType() throws IllegalValueException {
        TaskBook taskBook = new TaskBook();
        for (XmlAdaptedTaskCategory c : taskCategories) {
            taskBook.addTaskCategory(c.toModelType());
        }
        for (XmlAdaptedTask task : tasks) {
            taskBook.addTask(task.toModelType());
        }
        return taskBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTaskBook)) {
            return false;
        }

        XmlSerializableTaskBook otherTb = (XmlSerializableTaskBook) other;
        return tasks.equals(otherTb.tasks) && taskCategories.equals(otherTb.taskCategories);
    }

}
```
###### \java\seedu\address\storage\XmlTaskBookStorage.java
``` java
/**
 * A class to access TaskBook data stored as an xml file on the hard disk.
 */
public class XmlTaskBookStorage implements TaskBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskBookStorage.class);

    private String filePath;

    public XmlTaskBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTaskBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(filePath);
    }

    /**
     * Similar to {@link #readTaskBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File taskBookFile = new File(filePath);

        if (!taskBookFile.exists()) {
            logger.info("TaskBook file "  + taskBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableTaskBook xmlTaskBook = XmlFileStorage.loadTaskDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlTaskBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + xmlTaskBook + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        requireNonNull(taskBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskBook(taskBook));
    }

    @Override
    public void exportTaskBook() throws ParserConfigurationException, IOException {
        //TODO BY V2.0
    }

    @Override
    public void backupTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, filePath + "-backup");
    }

}
```
###### \java\seedu\address\ui\TaskCard.java
``` java
/**
 * An UI Component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private FlowPane categories;
    @FXML
    private ImageView statusImage;
    @FXML
    private ImageView dueDateImage;
    @FXML
    private Label dueDate;
    @FXML
    private HBox priorityPane;
    @FXML
    private ImageView priorityImage;
    @FXML
    private Label priority;

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;

        id.setText(displayedIndex + ". ");
        setLabelTextStyle(name, task.getTaskName().value);
        setLabelTextStyle(description, task.getTaskDescription().value);
        setTaskCategoryStyle(task.getTaskCategories());
        setTaskPriorityDueDateAndStatusStyle(task.getTaskPriority().value, task.getTaskDueDate().value,
                task.getTaskStatus().value);
    }

    /**
     * Set the style for different task fields
     * @param labelName label to set the style to
     * @param taskFieldValue value to set
     */
    private void setLabelTextStyle(Label labelName, String taskFieldValue) {
        labelName.setText(taskFieldValue);
        labelName.setWrapText(true);
    }

    /**
     * Set the style for task category field
     * Ensure that longer category label does not get truncated or overlap with other fields
     * @param taskCategories all categories belonging to a task
     */
    private void setTaskCategoryStyle(Set<TaskCategory> taskCategories) {
        for (TaskCategory category : taskCategories) {
            Label categoryLabel = new Label(category.taskCategoryName);
            categoryLabel.setMaxWidth(220);
            categoryLabel.setWrapText(true);
            categories.getChildren().add(categoryLabel);
        }
    }

    /**
     * Set the style for task priority, due date and status fields
     * @param taskPriority priority level
     * @param taskDueDate due date
     * @param taskStatus status
     */
    private void setTaskPriorityDueDateAndStatusStyle(String taskPriority, String taskDueDate, String taskStatus) {
        if (taskStatus.equals("done")) {
            setLabelText(priority, "");
            setLabelText(dueDate, "");
            setImage(statusImage, "images/taskStatusDone.png", 50, 50, "status");
        } else { // undone tasks
            setTaskPriorityStyleForUndoneTasks(taskPriority);
            setTaskDueDateStyleForUndoneTasks(taskDueDate);
        }
    }

    /**
     * Set the task priority field style for undone tasks
     * (1) high: red color display
     * (2) medium: orange color display
     * (3) low: green color display
     * @param taskPriority priority level
     */
    private void setTaskPriorityStyleForUndoneTasks(String taskPriority) {
        if (taskPriority.equals("high")) {
            setTaskPriorityPaneStyle(68, "#FF0000");
            setImage(priorityImage, "images/taskPriorityHigh.png", 15, 15,
                    "priority");

        } else if (taskPriority.equals("medium")) {
            setTaskPriorityPaneStyle(88, "#FFC000");
            setImage(priorityImage, "images/taskPriorityMedium.png", 15, 15,
                    "priority");
        } else {
            setTaskPriorityPaneStyle(65, "#00FF00");
            setImage(priorityImage, "images/taskPriorityLow.png", 15, 15,
                    "priority");
        }
        setLabelText(priority, taskPriority.toUpperCase());
    }

    /**
     * Set the style for the task priority pane
     * @param maxWidth maximum width of the pane
     * @param colorCode color code of the border of the pane
     */
    private void setTaskPriorityPaneStyle(int maxWidth, String colorCode) {
        priorityPane.setMaxWidth(maxWidth);
        priorityPane.setStyle("-fx-border-color: " + colorCode + ";");
    }

    /**
     * Set the task due date field style for undone tasks
     * - Set different text colors based on days left to task due date
     * (1) < 0 day (overdue tasks): red color text with circular exclamation symbol
     * (1) < 3 days: red color text
     * (2) 3 & 4 days: orange color text
     * (3) >= 5 days: green color text
     * - Set the label text of task due date field
     * @param taskDueDate due date of task
     */
    private void setTaskDueDateStyleForUndoneTasks(String taskDueDate) {
        int remainingDays = (int) DateUtil.getDayCountBetweenTwoDates(DateUtil.getTodayDate(),
                DateUtil.getParsedDate(taskDueDate));

        if (remainingDays < 0) { // overdue tasks
            setImage(dueDateImage, "images/taskOverdue.png", 20, 20, "due date");
            dueDate.setStyle("-fx-text-fill: #FF0000;");
        } else if (remainingDays < 3) {
            dueDate.setStyle("-fx-text-fill: #FF0000;");
        } else if (remainingDays < 5) {
            dueDate.setStyle("-fx-text-fill: #FFC000;");
        } else {
            dueDate.setStyle("-fx-text-fill: #00FF00;");
        }

        setLabelText(dueDate, taskDueDate);
    }

    /**
     * Set the text to be display at task field label
     * @param labelName label to set the text to
     * @param valueToSet
     */
    private void setLabelText(Label labelName, String valueToSet) {
        labelName.setText(valueToSet);
    }

    /**
     * Set the image of the task field
     * @param imgViewName image view to set the image to
     * @param taskStatusImageUrl url of the image to be displayed
     * @param width width of image
     * @param height height of image
     * @param fieldName task field name e.g. priority, status
     */
    private void setImage(ImageView imgViewName, String taskStatusImageUrl, int width, int height, String fieldName) {
        try {
            imgViewName.setImage(new Image(taskStatusImageUrl, width, height, true, true));
        } catch (IllegalArgumentException iae) {
            raise(new NewResultAvailableEvent(String.format(MESSAGE_INVALID_ARGUMENT, fieldName)));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText()) && task.equals(card.task);
    }
}
```
###### \java\seedu\address\ui\TaskListPanel.java
``` java
/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    public TaskListPanel(ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Task> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    /**
     * Scrolls to the {@code TaskCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }
}
```
###### \resources\view\DarkTheme.css
``` css
#statusPane {
    -fx-padding: 0 35 0 0;
    -fx-spacing: 2;
}

#statusImage {
    -fx-padding: 0 100 0 0;
}

#dueDatePane {
    -fx-padding: 0 5 5 0;
    -fx-spacing: 2;
}

#priorityPane {
    -fx-padding: 0 8 0 0;
    -fx-border-width: 1.5;
    -fx-border-radius: 5 5 5 5;
    -fx-spacing: 2;
}
```
###### \resources\view\TaskListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="TOP_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="TOP_LEFT" maxWidth="225">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
            </HBox>
            <Label fx:id="description" styleClass="cell_small_label" text="\$description" maxWidth="225" />
            <FlowPane fx:id="categories" />
        </VBox>
        <VBox alignment="CENTER_RIGHT" minHeight="105" GridPane.columnIndex="1">
            <padding>
                <Insets top="25" right="0" bottom="0" left="0" />
            </padding>
            <HBox fx:id="statusPane" alignment="CENTER_RIGHT">
                <ImageView fx:id="statusImage"></ImageView>
            </HBox>
            <HBox fx:id="dueDatePane" alignment="CENTER_RIGHT">
                <ImageView fx:id="dueDateImage"></ImageView>
                <Label fx:id="dueDate" styleClass="cell_small_label" text="\$dueDate" />
            </HBox>
            <HBox fx:id="priorityPane" alignment="CENTER_RIGHT">
                <ImageView fx:id="priorityImage"></ImageView>
                <Label fx:id="priority" styleClass="cell_small_label" text="\$priority" />
            </HBox>
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\TaskListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="taskListView" VBox.vgrow="ALWAYS" />
</VBox>
```
