package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.progresschecker.model.task.TaskListUtil.clearTaskList;
import static seedu.progresschecker.model.task.TaskListUtil.copyTaskList;
import static seedu.progresschecker.model.task.TaskListUtil.createTaskList;
import static seedu.progresschecker.model.task.TaskListUtil.setTaskListTitle;
import static seedu.progresschecker.model.task.TaskUtil.addMultipleTask;
import static seedu.progresschecker.storage.DefaultTasks.getDefaultTasks;
import static seedu.progresschecker.storage.TestTasks.getTestTasks;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.events.ui.NewResultAvailableEvent;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.task.SimplifiedTask;

//@@author EdwardKSG
/**
 * Adds a default task list to the user's Google account.
 */
public class AddDefaultTasksCommand extends Command {

    public static final String COMMAND_WORD = "newtasklist";
    public static final String COMMAND_ALIAS = "nl"; // short for "new list"
    public static final String SOURCE_FILE_FOLDER = "/view";
    public static final String SOURCE_FILE = "/defaultTasks.txt";
    public static final String TEST_FILE = "/testTasks.txt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new task list. "
            + "Parameters: "
            + "LISTNAME "
            + "Example: " + COMMAND_WORD + " "
            + "CS2103 LOs";

    public static final String MESSAGE_SUCCESS = "New task list added: %1$s";
    public static final String DEFAULT_LIST_TITLE = "CS2103 LOs";
    public static final String FIRST_LIST_TITLE = "My List";
    public static final String DEFAULT_LIST_ID = "@default";
    public static final String CREATE_FAILURE = "Failed to create task list "
            + "due to unexpected interrupt or API error: ";
    public static final String START_MASSEGE = "We have a lot of tasks to initialize. "
            + "The preparation may take a long time, please be patient :) Thank you!";
    public static final String LIST_FINISH_MASSEGE = "Task List created. Now pushing tasks into it: 0/";
    public static final String PUSH_TASK_ONGOING = "Pushing tasks into your task list: ";

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
            EventsCenter.getInstance().post(new NewResultAvailableEvent(START_MASSEGE));
            setTaskListTitle(DEFAULT_LIST_ID, DEFAULT_LIST_TITLE);
            createTaskList(FIRST_LIST_TITLE);
            copyTaskList(FIRST_LIST_TITLE, DEFAULT_LIST_ID);
            clearTaskList(DEFAULT_LIST_ID);

            SimplifiedTask[] tasklist;
            if (!listTitle.equals(DEFAULT_LIST_TITLE)) {
                tasklist = getTestTasks();
            } else {
                tasklist = getDefaultTasks();
            }

            addMultipleTask(tasklist, DEFAULT_LIST_ID);

            return new CommandResult(String.format(MESSAGE_SUCCESS, DEFAULT_LIST_TITLE));
        } catch (CommandException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CommandException(CREATE_FAILURE + DEFAULT_LIST_TITLE);
        }
    }
}
