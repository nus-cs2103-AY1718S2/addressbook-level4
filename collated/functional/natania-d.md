# natania-d
###### \java\seedu\organizer\logic\commands\DeleteRecurredTasksCommand.java
``` java
/**
 * Deletes a group of recurred tasks (both the original task and recurred versions of it)
 * identified using it's last displayed index from the organizer.
 */
public class DeleteRecurredTasksCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleter";
    public static final String COMMAND_ALIAS = "dr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task at the specified index and all its recurred versions.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task & its Recurred Versions: %1$s";
    public static final String MESSAGE_NOT_RECURRED_TASK = "This task is not recurring and cannot be deleted "
            + "using this command. Use 'delete' instead.";

    private final Index targetIndex;

    private Task taskToDelete;

    public DeleteRecurredTasksCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(taskToDelete);
        try {
            model.deleteRecurredTasks(taskToDelete);
        } catch (DuplicateTaskException pnfe) {
            throw new AssertionError("The target task cannot be a duplicate");
        } catch (TaskNotRecurringException tnre) {
            throw new CommandException(MESSAGE_NOT_RECURRED_TASK);
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
        return other == this // short circuit if same object
                || (other instanceof DeleteRecurredTasksCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteRecurredTasksCommand) other).targetIndex) // state check
                && Objects.equals(this.taskToDelete, ((DeleteRecurredTasksCommand) other).taskToDelete));
    }
}
```
###### \java\seedu\organizer\logic\commands\RecurWeeklyCommand.java
``` java
/**
 * Recurs a task weekly for the specified number of weeks.
 */
public class RecurWeeklyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "recurw";
    public static final String COMMAND_ALIAS = "rw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Recurs a task weekly for specified number of weeks. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TIMES + "NO. OF TIMES TO RECUR "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIMES + "2 ";

    public static final String MESSAGE_SUCCESS = "Recurring task: %1$s";
    public static final String MESSAGE_RECURRED_TASK = "Task is already recurring";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the organizer.";

    private final Index index;
    private final int times;

    private Task taskToRecur;

    /**
     * @param index of the task in the filtered task list to edit
     * @param times task is to be recurred
     */
    public RecurWeeklyCommand(Index index, int times) {
        requireNonNull(index);

        this.index = index;
        this.times = times;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.recurWeeklyTask(taskToRecur, this.times);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        } catch (TaskAlreadyRecurredException tare) {
            throw new CommandException(MESSAGE_RECURRED_TASK);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToRecur));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToRecur = lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurWeeklyCommand // instanceof handles nulls
                && this.index.equals(((RecurWeeklyCommand) other).index) // state check
                && this.times == (((RecurWeeklyCommand) other).times)); // state check
    }
}
```
###### \java\seedu\organizer\logic\commands\RemoveTagsCommand.java
``` java
/**
 * Removes a specified tag from all tasks in the organizer.
 */
public class RemoveTagsCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tags from all tasks in the organizer.\n"
            + "Parameters: " + PREFIX_TAG + "TAG1 " + PREFIX_TAG + "TAG2\n"
            + "Example: " + COMMAND_WORD + " t/friends t/homework";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tags: %1$s";

    private final Set<Tag> tagList;

    public RemoveTagsCommand(Set<Tag> tagList) {
        this.tagList = tagList;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(tagList);

        for (Tag tag : tagList) {
            model.deleteTag(tag);
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, tagList));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagsCommand // instanceof handles nulls
                && this.tagList.equals(((RemoveTagsCommand) other).tagList)); // state check
    }
}
```
###### \java\seedu\organizer\logic\parser\DeleteRecurredTasksCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteRecurredTasksCommand object
 */
public class DeleteRecurredTasksCommandParser implements Parser<DeleteRecurredTasksCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRecurredTasksCommand
     * and returns a DeleteRecurredTasksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRecurredTasksCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRecurredTasksCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecurredTasksCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\organizer\logic\parser\OrganizerParser.java
``` java
        case DeleteRecurredTasksCommand.COMMAND_WORD:
            return new DeleteRecurredTasksCommandParser().parse(arguments);

        case DeleteRecurredTasksCommand.COMMAND_ALIAS:
            return new DeleteRecurredTasksCommandParser().parse(arguments);

```
###### \java\seedu\organizer\logic\parser\OrganizerParser.java
``` java
        case RecurWeeklyCommand.COMMAND_WORD:
            return new RecurWeeklyCommandParser().parse(arguments);

        case RecurWeeklyCommand.COMMAND_ALIAS:
            return new RecurWeeklyCommandParser().parse(arguments);

        case RemoveTagsCommand.COMMAND_WORD:
            return new RemoveTagsCommandParser().parse(arguments);

        case RemoveTagsCommand.COMMAND_ALIAS:
            return new RemoveTagsCommandParser().parse(arguments);
```
###### \java\seedu\organizer\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String times} into an int.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static int parseTimes(String times) throws IllegalValueException {
        requireNonNull(times);
        String trimmedTimes = times.trim();
        int time = Integer.parseInt(trimmedTimes);
        if (time <= 0) {
            throw new IllegalValueException("This is an invalid number of times.");
        }
        return time;
    }

    /**
     * Parses a {@code Optional<String> times} into an {@code Optional<Integer>} if times is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Integer> parseTimes(Optional<String> times) throws IllegalValueException {
        requireNonNull(times);
        return times.isPresent() ? Optional.of(parseTimes(times.get())) : Optional.empty();
    }
```
###### \java\seedu\organizer\logic\parser\RecurWeeklyCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RecurWeeklyCommand object
 */
public class RecurWeeklyCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the RecurWeeklyCommand
     * and returns an RecurWeeklyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecurWeeklyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIMES);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurWeeklyCommand.MESSAGE_USAGE));
        }

        Optional<Integer> times;
        try {
            times = ParserUtil.parseTimes(argMultimap.getValue(PREFIX_TIMES));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        int time = -1;
        if (times.isPresent()) {
            time = times.get();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurWeeklyCommand.MESSAGE_USAGE));
        }

        return new RecurWeeklyCommand(index, time);
    }
}
```
###### \java\seedu\organizer\logic\parser\RemoveTagsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagsCommand object
 */
public class RemoveTagsCommandParser implements Parser<RemoveTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagsCommand
     * and returns an RemoveTagsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        try {
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new RemoveTagsCommand(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\organizer\model\Model.java
``` java
    /** Recurs the given task for the given number of times */
    void recurWeeklyTask(Task task, int times)
            throws DuplicateTaskException, TaskAlreadyRecurredException, TaskNotFoundException;

    /** Deletes the given task and all its recurred versions. */
    void deleteRecurredTasks(Task target) throws DuplicateTaskException, TaskNotRecurringException;
}
```
###### \java\seedu\organizer\model\ModelManager.java
``` java
    @Override
    public synchronized void recurWeeklyTask(Task task, int times)
            throws DuplicateTaskException, TaskAlreadyRecurredException, TaskNotFoundException {
        organizer.recurWeeklyTask(task, times);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void deleteRecurredTasks(Task target) throws DuplicateTaskException, TaskNotRecurringException {
        organizer.removeRecurredTasks(target);
        indicateOrganizerChanged();
    }
```
###### \java\seedu\organizer\model\Organizer.java
``` java
    /**
     * Recurs a task weekly in the organizer for the given number of times, which is done
     * by adding new tasks with the same parameters as the task to be recurred,
     * except for deadline, which is changed to be on the same day as the task to be recurred,
     * but on later weeks, and priority, which is set to the base priority.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void recurWeeklyTask(Task taskToRecur, int times)
            throws DuplicateTaskException, TaskAlreadyRecurredException, TaskNotFoundException {
        Task task = createRecurredTask(taskToRecur);
        updateTask(taskToRecur, task);
        addRecurringWeeklyTasks(task, times);
    }

    /**
     * Adds versions of a {@code task} that are recurred weekly.
     */
    public void addRecurringWeeklyTasks(Task task, int times) throws DuplicateTaskException {
        LocalDate oldDeadline = task.getDeadline().date;
        for (int i = 1; i <= times; i++) {
            LocalDate newDeadline = oldDeadline.plusWeeks(i);
            tasks.addRecurringTask(task, newDeadline.toString());
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToRecur}
     */
    public Task createRecurredTask(Task task) throws TaskAlreadyRecurredException {
        Recurrence updatedRecurrence = new Recurrence(task.getRecurrence().getIsRecurring(),
                task.hashCode(), true);
        return new Task(
                task.getName(), task.getUpdatedPriority(), task.getBasePriority(), task.getDeadline(),
                task.getDateAdded(), task.getDateCompleted(), task.getDescription(), task.getStatus(),
                task.getTags(), task.getSubtasks(), task.getUser(), updatedRecurrence);
    }

    /**
     * Removes {@code key} and its recurred versions from this {@code Organizer}.
     *
     * @throws DuplicateTaskException
     * @throws TaskNotRecurringException if the {@code key} is not recurring.
     */
    public void removeRecurredTasks(Task key) throws DuplicateTaskException, TaskNotRecurringException {
        if (!key.getRecurrence().getIsRecurring()) {
            throw new TaskNotRecurringException();
        } else {
            List<Task> newTaskList = makeNewTaskListWithoutTags(key);
            setTasks(newTaskList);
            removeUnusedTags();
        }
    }

    /**
     * Removes {@code tag} from all tasks in this {@code Organizer}.
     */
    public List<Task> makeNewTaskListWithoutTags(Task key) {
        int recurrenceGroup = key.getRecurrence().getRecurrenceGroup();
        List<Task> newTaskList = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getRecurrence().getRecurrenceGroup() != recurrenceGroup) {
                newTaskList.add(task);
            }
        }
        return newTaskList;
    }
```
###### \java\seedu\organizer\model\recurrence\exceptions\TaskAlreadyRecurredException.java
``` java
/**
 * Signals that the specified task is already recurred.
 */
public class TaskAlreadyRecurredException extends Exception{}
```
###### \java\seedu\organizer\model\recurrence\exceptions\TaskNotRecurringException.java
``` java
/**
 * Signals that the specified task is not recurred.
 */
public class TaskNotRecurringException extends Exception {}
```
###### \java\seedu\organizer\model\recurrence\Recurrence.java
``` java
/**
 * Represents a Task's recurrence in the organizer book.
 */

public class Recurrence {

    public final boolean isRecurring;
    public final int recurrenceGroup; // original task and its recurred versions have the same group

    /**
     * Constructs a default {@code Recurrence} with task not recurring.
     */
    public Recurrence() {
        this.isRecurring = false; // false when task is not recurring, true when otherwise
        this.recurrenceGroup = 0; // dummy group
    }

    /**
     * Constructs a {@code Recurrence} for task that is recurring.
     *
     * @param index A valid identifying index for a group of recurring tasks.
     */
    public Recurrence(boolean prevIsRecurring, int index, boolean recurCommand) throws TaskAlreadyRecurredException {
        if (prevIsRecurring && recurCommand) {
            throw new TaskAlreadyRecurredException();
        }
        this.isRecurring = true; // false when task is not recurring, true when otherwise
        this.recurrenceGroup = index; // unique group of recurring tasks
    }

    /**
     * Constructs a {@code Recurrence} for task that is recurring.
     *
     * @param isRecurring A boolean that shows whether task is recurring
     * @param index A valid identifying index for a group of recurring tasks.
     */
    public Recurrence(boolean isRecurring, int index) {
        this.isRecurring = isRecurring; // false when task is not recurring, true when otherwise
        this.recurrenceGroup = index; // unique group of recurring tasks
    }

    public boolean getIsRecurring() {
        return isRecurring;
    }

    public int getRecurrenceGroup() {
        return recurrenceGroup;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                && this.isRecurring == (((Recurrence) other).isRecurring))
                && this.recurrenceGroup == (((Recurrence) other).recurrenceGroup); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRecurring, recurrenceGroup);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.valueOf(recurrenceGroup);
    }

}


```
###### \java\seedu\organizer\model\subtask\UniqueSubtaskList.java
``` java
    /**
     * Makes the {@code Status} of all the subtasks in list not done.
     */
    public void makeAllSubtasksUndone(List<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            ;
        }
    }

}
```
###### \java\seedu\organizer\model\task\DateCompleted.java
``` java
/**
 * Represents a Task's dateCompleted in the organizer.
 * Guarantees: immutable;
 */
public class DateCompleted {

    public static final String MESSAGE_DATECOMPLETED_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD, and it should not be blank";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     * Format of string is YYYY-MM-DD.
     */
    public static final String DATECOMPLETED_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])";
    public static final String TASK_NOTCOMPLETED = "not completed";

    public final LocalDate date;

    /**
     * Constructs a {@code DateCompleted}.
     *
     * @param dateCompleted A valid date.
     */
    public DateCompleted(String dateCompleted) {
        requireNonNull(dateCompleted);
        checkArgument(isValidDateCompleted(dateCompleted), MESSAGE_DATECOMPLETED_CONSTRAINTS);
        if (dateCompleted.equals(TASK_NOTCOMPLETED)) {
            this.date = null;
        } else {
            this.date = LocalDate.parse(dateCompleted);
        }
    }

    /**
     * Constructs a DateCompleted based on the current date
     */
    public DateCompleted() {
        LocalDate currentDate = LocalDate.now();
        requireNonNull(currentDate);
        this.date = currentDate;
    }

    /**
     * Constructs a DateCompleted that is empty
     */
    public DateCompleted(boolean isCompleted) {
        if (!isCompleted) {
            this.date = null;
        } else {
            LocalDate currentDate = LocalDate.now();
            requireNonNull(currentDate);
            this.date = currentDate;
        }
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDateCompleted(String test) {
        return test.matches(DATECOMPLETED_VALIDATION_REGEX) || test.matches(TASK_NOTCOMPLETED);
    }

    /**
     * Returns String representation of DateCompleted depending on whether task is completed
     */
    public String toString() {
        if (date == null) {
            return TASK_NOTCOMPLETED;
        }
        return date.toString();
    }

    /**
     * Updates DateCompleted when status is toggled
     */
    public DateCompleted toggle() {
        if (date == null) {
            return new DateCompleted();
        }
        return new DateCompleted(false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateCompleted // instanceof handles nulls
                && this.date.equals(((DateCompleted) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
```
###### \java\seedu\organizer\storage\XmlAdaptedRecurrence.java
``` java
/**
 * JAXB-friendly adapted version of the Recurrence.
 */
public class XmlAdaptedRecurrence {

    @XmlElement(required = true)
    private boolean isRecurring;
    @XmlElement(required = true)
    private int recurrenceGroup;

    /**
     * Constructs an XmlAdaptedRecurrence.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRecurrence() {}

    /**
     * Constructs a {@code XmlAdaptedRecurrence} with the given {@code isRecurring} and {@code recurrenceGroup}.
     */
    public XmlAdaptedRecurrence(boolean isRecurring, int recurrenceGroup) {
        this.isRecurring = isRecurring;
        this.recurrenceGroup = recurrenceGroup;
    }

    /**
     * Converts a given Recurrence into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedRecurrence(Recurrence source) {
        isRecurring = source.isRecurring;
        recurrenceGroup = source.recurrenceGroup;
    }

    public boolean getIsRecurring() {
        return isRecurring;
    }

    public int getRecurrenceGroup() {
        return recurrenceGroup;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Recurrence object.
     */
    public Recurrence toModelType() {
        return new Recurrence(isRecurring, recurrenceGroup);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRecurrence)) {
            return false;
        }

        return isRecurring == (((XmlAdaptedRecurrence) other).isRecurring)
                && recurrenceGroup == (((XmlAdaptedRecurrence) other).recurrenceGroup);
    }
}
```
