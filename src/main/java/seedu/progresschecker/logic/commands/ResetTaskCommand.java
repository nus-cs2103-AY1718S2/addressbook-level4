package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_ID;
import static seedu.progresschecker.model.task.MyTask.undoTask;

import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author EdwardKSG
/**
 * Sets a task with given index as incompleted.
 */
public class ResetTaskCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String COMMAND_ALIAS = "rt"; // short for "reset task"
    public static final String DATA_FOLDER = "data/";
    public static final String TASK_PAGE = "tasklist.html";
    public static final String FILE_FAILURE = "Something is wrong with the file system.";
    public static final String COMMAND_FORMAT = COMMAND_WORD + "INDEX";
    public static final String MESSAGE_TITLE_CONSTRAINTS = "The index should be an index in the task list displayed"
            + "to you. It must be an integer that does not exceed the number of tasks in the list.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark task with the given index in the list as incompleted.\n"
            + "Parameters: INDEX (an index in the task list)\n "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Reset task list: %1$s";
    public static final String RESET_FAILURE = "Failed to mark it as incompleted. Index: %1$s";

    private int index;

    /**
     * Reset the task with index {@code int}
     */
    public ResetTaskCommand(int index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String title = undoTask(index, DEFAULT_LIST_ID);

            ViewTaskListCommand view = new ViewTaskListCommand();
            view.updateView();

            return new CommandResult(String.format(MESSAGE_SUCCESS, index + ". " + title));
        } catch (CommandException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CommandException(RESET_FAILURE + index);
        }
    }
}
