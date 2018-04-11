package seedu.organizer.model;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.organizer.commons.core.ComponentManager;
import seedu.organizer.commons.core.LogsCenter;
import seedu.organizer.commons.events.model.OrganizerChangedEvent;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.task.predicates.TaskByUserPredicate;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

/**
 * Represents the in-memory model of the organizer data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    private static User currentlyLoggedInUser;

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Organizer organizer;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given organizer and userPrefs.
     */
    public ModelManager(ReadOnlyOrganizer organizer, UserPrefs userPrefs) {
        super();
        requireAllNonNull(organizer, userPrefs);

        logger.fine("Initializing with organizer: " + organizer + " and user prefs " + userPrefs);

        this.organizer = new Organizer(organizer);
        currentlyLoggedInUser = this.organizer.getCurrentLoggedInUser();
        filteredTasks = new FilteredList<>(this.organizer.getTaskList());
        updateFilteredTaskList(PREDICATE_SHOW_NO_TASKS);
    }

    public ModelManager() {
        this(new Organizer(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyOrganizer newData) {
        organizer.resetData(newData);
        indicateOrganizerChanged();
    }

    @Override
    public ReadOnlyOrganizer getOrganizer() {
        return organizer;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateOrganizerChanged() {
        raise(new OrganizerChangedEvent(organizer));
    }

    @Override
    public synchronized void deleteTask(Task target) throws TaskNotFoundException {
        organizer.removeTask(target);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        organizer.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateOrganizerChanged();
    }

    //@@author dominickenn

    public static User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    @Override
    public synchronized void addUser(User user) throws DuplicateUserException {
        requireNonNull(user);
        organizer.addUser(user);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void loginUser(User user)
            throws UserNotFoundException,
            UserPasswordWrongException,
            CurrentlyLoggedInException {
        requireNonNull(user);
        organizer.loginUser(user);
        currentlyLoggedInUser = organizer.getCurrentLoggedInUser();
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void logout() {
        organizer.logout();
        currentlyLoggedInUser = organizer.getCurrentLoggedInUser();
        updateFilteredTaskList(PREDICATE_SHOW_NO_TASKS);
        indicateOrganizerChanged();

    }

    @Override
    public synchronized void addQuestionAnswerToUser(User toRemove, UserWithQuestionAnswer toAdd) {
        requireAllNonNull(toRemove, toAdd);
        organizer.updateUserToUserWithQuestionAnswer(toRemove, toAdd);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void deleteCurrentUserTasks() {
        organizer.deleteUserTasks(getCurrentlyLoggedInUser());
        indicateOrganizerChanged();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        requireNonNull(username);
        return organizer.getUserbyUsername(username);
    }
    //@@author

    @Override
    public void updateTask(Task target, Task editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        organizer.updateTask(target, editedTask);
        indicateOrganizerChanged();
    }

    //@@author natania
    @Override
    public void deleteTag(Tag tag) {
        organizer.removeTag(tag);
    }

    @Override
    public synchronized void recurTask(Task task, int times) throws DuplicateTaskException {
        organizer.recurTask(task, times);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateOrganizerChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code organizer}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        if (getCurrentlyLoggedInUser() == null) {
            filteredTasks.setPredicate(PREDICATE_SHOW_NO_TASKS);
        } else {
            Predicate<Task> newPredicate = predicate.and(new TaskByUserPredicate(getCurrentlyLoggedInUser()));
            filteredTasks.setPredicate(newPredicate);
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return organizer.equals(other.organizer)
                && filteredTasks.equals(other.filteredTasks);
    }

}
