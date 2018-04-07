package seedu.organizer.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /** {@code Predicate} that always evaluate to false */
    Predicate<Task> PREDICATE_SHOW_NO_TASKS = unused -> false;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyOrganizer newData);

    /** Returns the Organizer */
    ReadOnlyOrganizer getOrganizer();

    /** Deletes the given task. */
    void deleteTask(Task target) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws DuplicateTaskException;

    /** Adds a user */
    void addUser(User user) throws DuplicateUserException;

    /** Login a user */
    void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException, UserPasswordWrongException;

    /** Logout from organizer */
    void logout();

    /** Deletes all current user tasks */
    void deleteCurrentUserTasks();

    /** Updates a User with QuestionAnswer */
    void addQuestionAnswerToUser(User toRemove, UserWithQuestionAnswer toAdd);

    /** Returns a user with the {@code username} */
    User getUserByUsername(String username) throws UserNotFoundException;

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updateTask(Task target, Task editedTask)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /** Removes the given {@code tag} from all {@code Task}s. */
    void deleteTag(Tag tag);
}
