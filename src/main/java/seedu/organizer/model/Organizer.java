package seedu.organizer.model;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.tag.UniqueTagList;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.UniqueTaskList;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.UniqueUserList;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

/**
 * Wraps all data at the organizer-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class Organizer implements ReadOnlyOrganizer {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private final UniqueUserList users;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
        users = new UniqueUserList();
    }

    public Organizer() {}

    /**
     * Creates an Organizer using the Tasks and Tags in the {@code toBeCopied}
     */
    public Organizer(ReadOnlyOrganizer toBeCopied) {
        this();
        requireNonNull(toBeCopied);
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<Task> tasks) throws DuplicateTaskException {
        requireNonNull(tasks);
        this.tasks.setTasks(tasks);
    }

    public void setTags(Set<Tag> tags) {
        requireNonNull(tags);
        this.tags.setTags(tags);
    }

    //@@author dominickenn
    public void setUsers(List<User> users) {
        requireNonNull(users);
        this.users.setUsers(users);
    }
    //@@author

    /**
     * Resets the existing data of this {@code Organizer} with {@code newData}.
     */
    public void resetData(ReadOnlyOrganizer newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        setUsers(newData.getUserList());
        List<Task> syncedTaskList = newData.getTaskList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setTasks(syncedTaskList);
        } catch (DuplicateTaskException e) {
            throw new AssertionError("PrioriTask should not have duplicate tasks");
        }
    }

    //@@author dominickenn
    //// user-level operations

    /**
     * Adds {@code user} to the organizer
     */
    public void addUser(User user) throws DuplicateUserException {
        requireNonNull(user);
        users.add(user);
    }

    /**
     * Sets {@code user} as the currentLoggedInUser of the organizer
     */
    public void loginUser(User user)
            throws UserNotFoundException,
            UserPasswordWrongException,
            CurrentlyLoggedInException {
        requireNonNull(user);
        users.setCurrentLoggedInUser(user);
    }

    /**
     * Replaces {@code toRemove} with {@code toAdd} in users
     */
    public void updateUserToUserWithQuestionAnswer(
            User toRemove, UserWithQuestionAnswer toAdd) {
        requireAllNonNull(toRemove, toAdd);
        try {
            users.updateUserToUserWithQuestionAnswer(toRemove, toAdd);
        } catch (UserNotFoundException e) {
            throw new AssertionError("User does not exist");
        }
    }

    public void logout() {
        users.setCurrentLoggedInUserToNull();
    }

    @Override
    public User getCurrentLoggedInUser() {
        return users.getCurrentLoggedInUser();
    }

    /**
     * Deletes all tasks by {@code user} from tasks
     */
    public void deleteUserTasks(User user) {
        requireNonNull(user);
        tasks.deleteUserTasks(user);
    }

    /**
     * Returns a user in users containing the {@code username}
     * There can only be one such user
     */
    public User getUserbyUsername(String username) throws UserNotFoundException {
        requireNonNull(username);
        return users.getUserByUsername(username);
    }
    //@@author

    //// task-level operations

    /**
     * Adds a task to the organizer.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws DuplicateTaskException {
        Task task = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.add(task);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code Organizer}'s tag list will be updated with the tags of {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *                                another existing task in the list.
     * @throws TaskNotFoundException  if {@code target} could not be found in the list.
     * @see #syncWithMasterTagList(Task)
     */
    public void updateTask(Task target, Task editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        Task syncedEditedTask = syncWithMasterTagList(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.setTask(target, syncedEditedTask);
        removeUnusedTags();
    }

    /**
     * Removes all {@code Tag}s that are not used by any {@code Task} in this {@code Organizer}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInTasks = tasks.asObservableList().stream()
                .map(Task::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInTasks);
    }

    /**
     * Updates the master tag list to include tags in {@code task} that are not in the list.
     *
     * @return a copy of this {@code task} such that every tag in this task points to a Tag object in the master
     * list.
     */
    private Task syncWithMasterTagList(Task task) {
        final UniqueTagList taskTags = new UniqueTagList(task.getTags());
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Task(
                task.getName(), task.getUpdatedPriority(), task.getBasePriority(), task.getDeadline(),
                task.getDateAdded(), task.getDateCompleted(), task.getDescription(), task.getStatus(),
                correctTagReferences, task.getSubtasks(), task.getUser(), task.getRecurrence());
    }

    /**
     * Removes {@code key} from this {@code Organizer}.
     *
     * @throws TaskNotFoundException if the {@code key} is not in this {@code Organizer}.
     */
    public boolean removeTask(Task key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author natania
    /**
     * Removes {@code tag} from {@code task} in this {@code Organizer}.
     * @throws TaskNotFoundException if the {@code task} is not in this {@code Organizer}.
     */
    private void removeTagFromTask(Tag tag, Task task) throws TaskNotFoundException {
        Set<Tag> newTags = new HashSet<>(task.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Task newTask =
                new Task(task.getName(), task.getUpdatedPriority(), task.getBasePriority(), task.getDeadline(),
                        task.getDateAdded(), task.getDateCompleted(), task.getDescription(), task.getStatus(),
                        newTags, task.getSubtasks(), task.getUser(), task.getRecurrence());

        try {
            updateTask(task, newTask);
        } catch (DuplicateTaskException dte) {
            throw new AssertionError("Modifying a task's tags only should not result in a duplicate. "
                    + "See Task#equals(Object).");
        }
    }

    /**
     * Removes {@code tag} from all tasks in this {@code Organizer}.
     */
    public void removeTag(Tag tag) {
        try {
            for (Task task : tasks) {
                removeTagFromTask(tag, task);
            }
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("Impossible: original task is obtained from PrioriTask.");
        }
    }

    /**
     * Recurs a task weekly in the organizer for the given number of times.
     * Recurs by adding new tasks with the same parameters as the task to be recurred,
     * except for deadline, which is changed to be on the same day as the task to be recurred,
     * but on later weeks.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void recurTask(Task task, int times) throws DuplicateTaskException {
        LocalDate oldDeadline = task.getDeadline().date;
        for (int i = 1; i <= times; i++) {
            LocalDate newDeadline = oldDeadline.plusWeeks(i);
            tasks.addRecurringTask(task, newDeadline.toString());
        }
    }
    //@@author

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asObservableList();
    }

    @Override
    public ObservableList<Task> getCurrentUserTaskList() {
        return tasks.userTasksAsObservableList(getCurrentLoggedInUser());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<User> getUserList() {
        return users.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Organizer // instanceof handles nulls
                && this.tasks.equals(((Organizer) other).tasks)
                && this.tags.equalsOrderInsensitive(((Organizer) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
