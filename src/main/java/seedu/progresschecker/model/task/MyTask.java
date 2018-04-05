package seedu.progresschecker.model.task;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.api.services.tasks.model.Tasks;

import seedu.progresschecker.logic.apisetup.ConnectTasksApi;
import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author EdwardKSG
/**
 * Include customized methods (based on Google Tasks API) to manipulate tasks.
 */
public class MyTask {

    public static final String AUTHORIZE_FAILURE = "Failed to authorize tasks api client credentials";
    public static final String LOAD_FAILURE = "Failed to load this task list";
    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm";
    public static final String COMPLETED = "completed";
    public static final String NEEDS_ACTION = "needsAction";


    /**
     * Finds the task with title {@code String} in the tasklist with title {@code String}
     *
     * @param taskTitle title of the task we look for
     * @param listTitle the title of the list to which the task belongs
     * @return the Task instances
     */
    public static Task searchTask(String taskTitle, String listTitle) throws CommandException {
        Task task = null;

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

            Tasks tasks = service.tasks().list(taskList.getId()).execute();
            task = tasks.getItems().stream()
                    .filter(t -> t.getTitle().equals(taskTitle))
                    .findFirst()
                    .orElse(null);

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

        return task;
    }

    /**
     * Creates a task with title {@code String} to the tasklist with title {@code String}
     *
     * @param taskTitle title of the task we want to create
     * @param listId the identifier of the list to which the task will be added
     * @param notes description or relevant URL link to this task
     * @param due the date and time of the deadline, in format "MM/dd/yyyy HH:mm"
     */
    public static void createTask(String taskTitle, String listId, String notes, String due)
            throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            TaskLists taskLists = service.tasklists().list().execute();

            Task task = service.tasks().insert(
                    listId,
                    new Task().setTitle(taskTitle)
                              .setDue(getDate(due))
                              .setNotes(notes)
                              .setStatus(NEEDS_ACTION)
            ).execute();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

    }

    /**
     * Converts a string in format "MM/dd/yyyy HH:mm" to a DateTime object
     *
     * @param s string in format "MM/dd/yyyy HH:mm", representing a date
     * @return the DateTime instances, or null if encountered error when parsing
     */
    public static DateTime getDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = simpleDateFormat.parse(s);
            return new DateTime(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * Marks the task with title {@code String} in the tasklist with ID {@code String} as completed
     *
     * @param index title of the task we look for
     * @param listId the identifier of the list to which the task belongs
     * @return title the title of the task with index {@code int}
     */
    public static String completeTask(int index, String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            Tasks tasks = service.tasks().list(listId).execute();
            List<Task> list = tasks.getItems();
            Task task = list.get(index - 1);

            task.setStatus(COMPLETED);
            task = service.tasks().update(
                    listId,
                    task.getId(),
                    task
            ).execute();

            return task.getTitle();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Marks the task with title {@code String} in the tasklist with ID {@code String} as incompleted
     *
     * @param index title of the task we look for
     * @param listId the identifier of the list to which the task belongs
     * @return title the title of the task with index {@code int}
     */
    public static String undoTask(int index, String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            Tasks tasks = service.tasks().list(listId).execute();
            List<Task> list = tasks.getItems();
            Task task = list.get(index - 1);

            task.setCompleted(null);
            task.setStatus(NEEDS_ACTION);
            task = service.tasks().update(
                    listId,
                    task.getId(),
                    task
            ).execute();

            return task.getTitle();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }
}
