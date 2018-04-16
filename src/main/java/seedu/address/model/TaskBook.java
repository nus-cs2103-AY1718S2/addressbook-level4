package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.category.UniqueTaskCategoryList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author CYX28
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
