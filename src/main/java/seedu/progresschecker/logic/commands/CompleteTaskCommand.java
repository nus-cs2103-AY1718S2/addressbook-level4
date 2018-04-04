package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_ID;
import static seedu.progresschecker.model.task.MyTask.completeTask;

import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author EdwardKSG
/**
 * Sets a task with given index as completed.
 */
public class CompleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String COMMAND_ALIAS = "ct"; // short for "complete task"
    public static final String DATA_FOLDER = "data/";
    public static final String TASK_PAGE = "tasklist.html";
    public static final String FILE_FAILURE = "Something is wrong with the file system.";
    public static final String COMMAND_FORMAT = COMMAND_WORD + "INDEX";
    public static final String MESSAGE_TITLE_CONSTRAINTS = "The index should be an index in the task list displayed"
            + "to you. It must be an integer that does not exceed the number of tasks in the list.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark task with the given index in the list as completed.\n"
            + "Parameters: INDEX (an index in the task list)\n "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Completed task list: %1$s";
    public static final String COMPLETE_FAILURE = "Failed to mark it as completed. Index: %1$s";

    private int index;

    /**
     * Complete the task with index {@code int}
     */
    public CompleteTaskCommand(int index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            String title = completeTask(index, DEFAULT_LIST_ID);

            ViewTaskListCommand view = new ViewTaskListCommand();
            view.updateView();

            return new CommandResult(String.format(MESSAGE_SUCCESS, index + ". " + title));
        } catch (CommandException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CommandException(COMPLETE_FAILURE + index);
        }
    }
}
