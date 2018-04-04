package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.progresschecker.model.task.MyTask.createTask;
import static seedu.progresschecker.model.task.MyTaskList.clearTaskList;
import static seedu.progresschecker.model.task.MyTaskList.copyTaskList;
import static seedu.progresschecker.model.task.MyTaskList.createTaskList;
import static seedu.progresschecker.model.task.MyTaskList.setTaskListTitle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author EdwardKSG
/**
 * Adds a default task list to the user's Google account.
 */
public class AddDefaultTasksCommand extends Command {

    public static final String COMMAND_WORD = "newtasklist";
    public static final String COMMAND_ALIAS = "nl"; // short for "new list"
    public static final String SOURCE_FILE_FOLDER = "/view";
    public static final String SOURCE_FILE = "/defaultTasks.txt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new task list. "
            + "Parameters: "
            + "LISTNAME "
            + "Example: " + COMMAND_WORD + " "
            + "CS2103 LOs";

    public static final String MESSAGE_SUCCESS = "New task list added: %1$s";
    public static final String DEFAULT_LIST_TITLE = "CS2103 LOs";
    public static final String FIRST_LIST_TITLE = "My List";
    public static final String DEFAULT_LIST_ID = "@default";
    public static final String CREATE_FAILURE = "Failed to create task list: ";

    private String listTitle;

    /**
     * Creates a AddDefaultTasksCommand to add the default task list with title {@code Sting}
     */
    public AddDefaultTasksCommand(String title) {
        requireNonNull(title);
        listTitle = title;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            setTaskListTitle(DEFAULT_LIST_ID, listTitle);
            createTaskList(FIRST_LIST_TITLE);
            copyTaskList(FIRST_LIST_TITLE, DEFAULT_LIST_ID);
            clearTaskList(DEFAULT_LIST_ID);

            InputStream in =
                    AddDefaultTasksCommand.class.getResourceAsStream(SOURCE_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                String title = line;

                if (title.equals((""))) {
                    break;
                } else {
                    String notes = reader.readLine();
                    String due = reader.readLine();
                    createTask(title, DEFAULT_LIST_ID, notes, due);
                }
            }

            reader.close();
            in.close();

            return new CommandResult(String.format(MESSAGE_SUCCESS, listTitle));
        } catch (CommandException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CommandException(CREATE_FAILURE + DEFAULT_LIST_TITLE);
        }
    }
}
