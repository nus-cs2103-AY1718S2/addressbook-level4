package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_ID;
import static seedu.progresschecker.model.task.TaskUtil.INDEX_OUT_OF_BOUND;
import static seedu.progresschecker.model.task.TaskUtil.getTaskUrl;

import javafx.util.Pair;
import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.events.ui.LoadUrlEvent;
import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author EdwardKSG
/**
 * Goes to the webpage of task with given index.
 */
public class GoToTaskUrlCommand extends Command {

    public static final String COMMAND_WORD = "goto";
    public static final String COMMAND_ALIAS = "go";
    public static final String COMMAND_FORMAT = COMMAND_WORD + "INDEX";
    public static final String MESSAGE_INDEX_CONSTRAINTS = "The index should be an index in the task list displayed"
            + "to you. It must be an integer that does not exceed the number of tasks in the list.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Open the URL of task with the given index in the list.\n"
            + "Parameters: INDEX (an index in the task list)\n "
            + "Example: " + COMMAND_WORD + 1;

    public static final String MESSAGE_SUCCESS = "Viewing webpage of task: %1$s";
    public static final String MESSAGE_NO_URL = "This task does not have a webpage: %1$s";
    public static final String VIEW_URL_FAILURE = "Error. Cannot open the URL. Index: %1$s";

    private int index;

    /**
     * Gets URL of the task with index {@code int}
     */
    public GoToTaskUrlCommand(int index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Pair<String, String> result  = getTaskUrl(index, DEFAULT_LIST_ID);

            String url = result.getKey();

            String titleWithCode = result.getValue();     // full file name
            String[] parts = titleWithCode.split("&#"); // String array, each element is text between dots
            String title = parts[0];

            if (url.equals("")) {
                return new CommandResult(String.format(INDEX_OUT_OF_BOUND));
            }

            EventsCenter.getInstance().post(new LoadUrlEvent(url));

            return new CommandResult(String.format(MESSAGE_SUCCESS, index + ". " + title));
        } catch (CommandException ce) {
            throw ce;
        } catch (Exception e) {
            throw new CommandException(VIEW_URL_FAILURE + index);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoToTaskUrlCommand // instanceof handles nulls
                && index == (((GoToTaskUrlCommand) other).index));
    }
}
