package seedu.organizer.model.task;

//@@author agus
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

    //@@author dominickenn
    /**
     * Adds a task to the list.
     * Updates priority level if task is not completed
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        toAdd = updatePriority(toAdd);
        internalList.add(toAdd);
        sortTasks();
    }
    //@@author

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(Task target, Task editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, editedTask);
        sortTasks();
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

    //@@author dominickenn
    /**
     * Deletes all tasks by {@code user} from internalList
     */
    public void deleteUserTasks(User user) {
        requireNonNull(user);
        FilteredList<Task> tasksToDelete = new FilteredList<>(internalList);
        tasksToDelete.setPredicate(new TaskByUserPredicate(user));
        internalList.removeAll(tasksToDelete);
    }
    //@@author

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

    //@@author dominickenn
    /**
     * Returns a list of tasks by a user as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Task> userTasksAsObservableList(User user) {
        FilteredList<Task> filteredList = new FilteredList<>(internalList);
        filteredList.setPredicate(new TaskByUserPredicate(user));
        return FXCollections.unmodifiableObservableList(filteredList);
    }
    //@@author

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

    //@@author dominickenn
    /**
     * Sorts all tasks in uniqueTaskList according to priority
     * Higher priority tasks are given preference
     */
    private void sortTasks() {
        internalList.sort(Task.priorityComparator());
    }

    /**
     * Updates task with updated priority level with respect to deadline
     * Priority level remains the same if task has just been created
     * Priority level is at maximum if current date is the deadline
     */
    public Task updatePriority(Task task) {
        Task newTask;
        Priority newPriority;
        LocalDate currentDate = LocalDate.now();
        LocalDate dateAdded = task.getDateAdded().date;
        LocalDate deadline = task.getDeadline().date;
        Priority curPriority = task.getUpdatedPriority();

        int priorityDifferenceFromMax = Integer.parseInt(Priority.HIGHEST_SETTABLE_PRIORITY_LEVEL)
                                        - Integer.parseInt(curPriority.value);
        long dayDifferenceCurrentToDeadline = Duration.between(currentDate.atStartOfDay(),
                                                            deadline.atStartOfDay()).toDays();
        long dayDifferenceAddedToDeadline = Duration.between(dateAdded.atStartOfDay(),
                                                            deadline.atStartOfDay()).toDays();

        if (dateAdded.isEqual(currentDate) && dayDifferenceCurrentToDeadline >= 0) {
            newTask = new Task(task.getName(), task.getUpdatedPriority(), task.getBasePriority(), task.getDeadline(),
                    task.getDateAdded(), task.getDateCompleted(), task.getDescription(), task.getStatus(),
                    task.getTags(), task.getSubtasks(), task.getUser(), task.getRecurrence());

        } else if (currentDate.isBefore(deadline)) {
            newPriority = calculateNewPriority(curPriority,
                    priorityDifferenceFromMax, dayDifferenceCurrentToDeadline, dayDifferenceAddedToDeadline);
            newTask = new Task(task.getName(), newPriority, task.getBasePriority(), task.getDeadline(),
                    task.getDateAdded(), task.getDateCompleted(), task.getDescription(), task.getStatus(),
                    task.getTags(), task.getSubtasks(), task.getUser(), task.getRecurrence());
        } else {
            newPriority = new Priority(Priority.HIGHEST_SETTABLE_PRIORITY_LEVEL);
            newTask = new Task(task.getName(), newPriority, task.getBasePriority(), task.getDeadline(),
                    task.getDateAdded(), task.getDateCompleted(), task.getDescription(), task.getStatus(),
                    task.getTags(), task.getSubtasks(), task.getUser(), task.getRecurrence());
        }

        requireNonNull(newTask);
        return newTask;
    }

    /**
     * Calculate a new priority level for updatePriority method
     */
    private Priority calculateNewPriority(Priority curPriority, int priorityDifferenceFromMax,
                                          long dayDifferenceCurrentToDeadline, long dayDifferenceAddedToDeadline) {
        requireAllNonNull(curPriority, priorityDifferenceFromMax,
                dayDifferenceCurrentToDeadline, dayDifferenceAddedToDeadline);
        Priority newPriority;
        int priorityToIncrease = (int) (priorityDifferenceFromMax
                * ((double) (dayDifferenceAddedToDeadline - dayDifferenceCurrentToDeadline)
                / (double) dayDifferenceAddedToDeadline));
        newPriority = new Priority(String.valueOf(Integer.parseInt(curPriority.value) + priorityToIncrease));
        return newPriority;
    }

    //@@author

    //@author natania
    /**
     * Adds recurred versions of a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void addRecurringTask(Task task, String newDeadline) throws DuplicateTaskException {
        requireNonNull(task);
        Task recurredTask = createRecurredTask(task, newDeadline);
        if (contains(recurredTask)) {
            throw new DuplicateTaskException();
        }
        internalList.add(recurredTask);
        sortTasks();
    }

    /**
     * Makes the {@code Status} of all the subtasks of {@code Task} not done.
     */
    private static Task createRecurredTask(Task task, String newDeadline) {
        assert task != null;

        Name updatedName = task.getName();
        Priority updatedPriority = task.getBasePriority();
        Priority basePriority = task.getBasePriority();
        Deadline updatedDeadline = new Deadline(newDeadline);
        DateAdded oldDateAdded = new DateAdded();
        DateCompleted updatedDateCompleted = new DateCompleted();
        Description updatedDescription = task.getDescription();
        Status updatedStatus = new Status(false);
        Set<Tag> updatedTags = task.getTags();
        List<Subtask> originalSubtasks = new ArrayList<>(task.getSubtasks());
        User user = task.getUser();
        Recurrence updatedRecurrence = task.getRecurrence();

        for (int i = 0; i < originalSubtasks.size(); i++) {
            Subtask originalSubtask = originalSubtasks.get(i);
            if (originalSubtask.getStatus().value) {
                Name subtaskName = originalSubtask.getName();
                Status subtaskStatus = originalSubtask.getStatus().getInverse();
                Subtask editedSubtask = new Subtask(subtaskName, subtaskStatus);
                originalSubtasks.set(i, editedSubtask);
            }
        }

        UniqueSubtaskList updatedSubtasks = new UniqueSubtaskList(originalSubtasks);

        return new Task(updatedName, updatedPriority, basePriority, updatedDeadline, oldDateAdded,
                updatedDateCompleted, updatedDescription, updatedStatus, updatedTags, updatedSubtasks.toList(),
                user, updatedRecurrence);
    }
}
