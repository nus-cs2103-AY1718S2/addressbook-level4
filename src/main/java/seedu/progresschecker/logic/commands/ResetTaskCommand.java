package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_ID;
import static seedu.progresschecker.model.task.TaskUtil.undoTask;

import javafx.util.Pair;
import seedu.progresschecker.logic.LogicManager;
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
    public static final String MESSAGE_INDEX_CONSTRAINTS = "The index should be an index in the task list displayed"
            + "to you. It must be an integer that does not exceed the number of tasks in the list.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark task with the given index in the list as incompleted.\n"
            + "Parameters: INDEX (an index in the task list)\n "
            + "Example: " + COMMAND_WORD + 1;

    public static final String MESSAGE_SUCCESS = "Reset task: %1$s";
    public static final String MESSAGE_NO_ACTION = "This task is not completed yet: %1$s";
    public static final String RESET_FAILURE = "Error. Failed to mark it as incompleted. Index: %1$s";

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
            Pair<Integer, String> result = undoTask(index, DEFAULT_LIST_ID);

            if (result.getKey() == -1) {
                return new CommandResult(String.format(result.getValue()));
            }

            String titleWithCode = result.getValue();     // full file name
            String[] parts = titleWithCode.split("&#"); // String array, each element is text between dots

            String title = parts[0];

            if (result.getKey() == 0) {
                return new CommandResult(String.format(MESSAGE_NO_ACTION, index + ". " + title));
            }

            ViewTaskListCommand view = LogicManager.getCurrentViewTask();;
            view.updateView();

            return new CommandResult(String.format(MESSAGE_SUCCESS, index + ". " + title));
        } catch (CommandException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CommandException(RESET_FAILURE + index);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResetTaskCommand // instanceof handles nulls
                && index == (((ResetTaskCommand) other).index));
    }
}
