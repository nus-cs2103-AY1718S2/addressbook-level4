package seedu.address.model.queue;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;



/**
 * The implementation adopts {@code UniquePersonList }
 * A list of Tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 */
public class TaskList implements Iterable<Task> {

    // represents no. of chefs total, can be changed by user
    private static int maxCapacity = 5;

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<Task> mappedList = EasyBind.map(internalList, (task) -> task);

    /**
     *
     * @return current max capacity
     */
    public static int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     *
     * @param maxCapacity new max capacity
     */
    public static void setMaxCapacity(int maxCapacity) {
        TaskList.maxCapacity = maxCapacity;
    }

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     * @throws DuplicateTaskException
     * if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(new Task(toAdd));
        Collections.sort(internalList);
    }

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

        internalList.set(index, new Task(editedTask));
    }

    /**
     * Removes the equivalent Task from the list.
     * * @throws TaskNotFoundException if {@code toRemove} could not be found in the list.
     */
    public boolean remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;

    }

    public void setTasks(TaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<Task> tasks) throws DuplicateTaskException {
        requireAllNonNull(tasks);
        final TaskList replacement = new TaskList();
        for (final Task task : tasks) {
            replacement.add(task);
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.internalList.equals(((TaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

