package seedu.progresschecker.model.task;

import java.io.IOException;
import java.util.List;

import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;

import seedu.progresschecker.logic.apisetup.ConnectTasksApi;
import seedu.progresschecker.logic.commands.exceptions.CommandException;

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
            com.google.api.services.tasks.model.Tasks tasks = service.tasks().list(id).execute();
            list = tasks.getItems();
        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

        return list;
    }
}
