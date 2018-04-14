package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_ID;
import static seedu.progresschecker.model.task.TaskUtil.completeTask;

import javafx.util.Pair;
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
    public static final String MESSAGE_INDEX_CONSTRAINTS = "The index should be an index in the task list displayed"
            + "to you. It must be an integer that does not exceed the number of tasks in the list.";
    public static final int DUMMY_WEEK = 0;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark task with the given index in the list as completed.\n"
            + "Parameters: INDEX (an index in the task list)\n "
            + "Example: " + COMMAND_WORD + 1;

    public static final String MESSAGE_SUCCESS = "Keep it up! Completed task: %1$s";
    public static final String MESSAGE_NO_ACTION = "This task is already completed: %1$s";
    public static final String COMPLETE_FAILURE = "Error. Failed to mark it as completed. Index: %1$s";

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
            Pair<Integer, String> result = completeTask(index, DEFAULT_LIST_ID);

            if (result.getKey() == -1) {
                return new CommandResult(String.format(result.getValue()));
            }

            String titleWithCode = result.getValue();     // full file name
            String[] parts = titleWithCode.split("&#"); // String array, each element is text between dots

            String title = parts[0];

            if (result.getKey() == 0) {
                return new CommandResult(String.format(MESSAGE_NO_ACTION, index + ". " + title));
            }

            ViewTaskListCommand view = new ViewTaskListCommand(DUMMY_WEEK);
            view.updateView();

            return new CommandResult(String.format(MESSAGE_SUCCESS, index + ". " + title));
        } catch (CommandException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CommandException(COMPLETE_FAILURE + index);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteTaskCommand // instanceof handles nulls
                && index == (((CompleteTaskCommand) other).index));
    }
}
