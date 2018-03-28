package seedu.organizer.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.commons.util.CollectionUtil;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;

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

    //@@author dominickenn
    /**
     * Sorts all tasks in uniqueTaskList according to priority
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
        Priority curPriority = task.getPriority();

        int priorityDifferenceFromMax = Integer.parseInt(Priority.HIGHEST_SETTABLE_PRIORITY_LEVEL)
                                        - Integer.parseInt(curPriority.value);
        long dayDifferenceCurrentToDeadline = Duration.between(currentDate.atStartOfDay(),
                                                            deadline.atStartOfDay()).toDays();
        long dayDifferenceAddedToDeadline = Duration.between(dateAdded.atStartOfDay(),
                                                            deadline.atStartOfDay()).toDays();

        if (dateAdded.isEqual(LocalDate.now())) {
            newTask = new Task(task.getName(), task.getPriority(), task.getDeadline(), task.getDateAdded(),
                    task.getDateCompleted(), task.getDescription(), task.getStatus(), task.getTags(),
                    task.getSubtasks());
        } else if (currentDate.isBefore(deadline)) {
            int priorityToIncrease = (int) (priorityDifferenceFromMax
                    * ((double) (dayDifferenceAddedToDeadline - dayDifferenceCurrentToDeadline)
                    / (double) dayDifferenceAddedToDeadline));
            newPriority = new Priority(String.valueOf(Integer.parseInt(curPriority.value) + priorityToIncrease));
            newTask = new Task(task.getName(), newPriority, task.getDeadline(), task.getDateAdded(),
                    task.getDateCompleted(), task.getDescription(), task.getStatus(), task.getTags(),
                    task.getSubtasks());
        } else {
            newPriority = new Priority(Priority.HIGHEST_SETTABLE_PRIORITY_LEVEL);
            newTask = new Task(task.getName(), newPriority, task.getDeadline(), task.getDateAdded(),
                    task.getDateCompleted(), task.getDescription(), task.getStatus(), task.getTags(),
                    task.getSubtasks());
        }

        requireNonNull(newTask);
        return newTask;
    }
}
