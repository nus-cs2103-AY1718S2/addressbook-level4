package seedu.progresschecker.model.task;

import java.io.IOException;
import java.util.List;

import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

import seedu.progresschecker.logic.apisetup.ConnectTasksApi;
import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author EdwardKSG
/**
 * Include customized methods (based on Google Tasks API) to manipulate task lists.
 */
public class MyTaskList {

    public static final String AUTHORIZE_FAILURE = "Failed to authorize tasks api client credentials";
    public static final String ADD_FAILURE = "Failed to add new task list to account";
    public static final String LOAD_FAILURE = "Failed to load this task list (might be wrong title)";

    /**
     * Creates a new task list with title {@code String} and adds to the current list of task lists
     *
     * @param listTitle title of the task list we intend to create
     */
    public static void createTaskList(String listTitle) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            service.tasklists().insert(
                    new TaskList().setTitle(listTitle)
            ).execute();
        } catch (IOException ioe) {
            throw new CommandException(ADD_FAILURE);
        }
    }

    /**
     * Finds the task list with title {@code String} from the current list of task lists
     *
     * @param listTitle title of the task list we look for
     * @return the List instances containing all tasks in the specified task list
     */
    public static List<Task> searchTaskList(String listTitle) throws CommandException {
        List<Task> list = null;

        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            TaskLists taskLists = service.tasklists().list().execute();
            TaskList taskList = taskLists.getItems().stream()
                    .filter(t -> t.getTitle().equals(listTitle))
                    .findFirst()
                    .orElse(null);
            String id = taskList.getId();
            Tasks tasks = service.tasks().list(id).execute();
            list = tasks.getItems();
        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

        return list;
    }

    /**
     * Finds the task list with ID {@code String} from the current list of task lists
     *
     * @param listId title of the task list we look for
     * @return the List instances containing all tasks in the specified task list
     */
    public static List<Task> searchTaskListById(String listId) throws CommandException {
        List<Task> list = null;

        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            Tasks tasks = service.tasks().list(listId).execute();
            list = tasks.getItems();
        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

        return list;
    }

    /**
     * Changes the name of task list with id {@code String} to {@code String}
     *
     * @param listId identifier of the target task list whose name will be changed
     * @param listTitle title of the task list we look for
     */
    public static void setTaskListTitle(String listId, String listTitle) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            TaskList taskList = service.tasklists().get(listId).execute();

            taskList.setTitle(listTitle);

            TaskList result = service.tasklists().update(
                    taskList.getId(),
                    taskList
            ).execute();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Copies tasks in the task list with id {@code String} to the task list with title {@code String}
     *
     * @param listId identifier of the target task list whose name will be changed
     * @param listTitle title of the task list we look for
     */
    public static void copyTaskList(String listTitle, String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            TaskLists taskLists = service.tasklists().list().execute();

            Tasks baseTasks = service.tasks().list(listId).execute();

            TaskList targetTaskList = taskLists.getItems().stream()
                    .filter(t -> t.getTitle().equals(listTitle))
                    .findFirst()
                    .orElse(null);
            String id = targetTaskList.getId();

            for (Task task : baseTasks.getItems()) {
                Task t = new Task();
                t.setTitle(task.getTitle());
                t.setStatus(task.getStatus());
                t.setDue(task.getDue());
                t.setNotes(task.getNotes());
                service.tasks().insert(id, t).execute();
            }


        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Removes all tasks in the task list with id {@code String}
     *
     * @param listId identifier of the target task list whose content will be removed
     */
    public static void clearTaskList(String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            Tasks tasks = service.tasks().list(listId).execute();
            for (Task task : tasks.getItems()) {
                service.tasks().delete(listId, task.getId()).execute();
            }

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }
}
