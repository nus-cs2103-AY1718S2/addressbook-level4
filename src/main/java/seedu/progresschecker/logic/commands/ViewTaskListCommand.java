package seedu.progresschecker.logic.commands;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_ID;
import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_TITLE;
import static seedu.progresschecker.logic.commands.TaskCommandUtil.readFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.services.tasks.model.Task;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.ui.LoadBarEvent;
import seedu.progresschecker.commons.events.ui.LoadTaskEvent;
import seedu.progresschecker.commons.events.ui.TabLoadChangedEvent;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.task.TaskListUtil;
import seedu.progresschecker.ui.CommandBox;

//@@author EdwardKSG
/**
 * View the web view of a particular TaskList (with the name provided).
 */
public class ViewTaskListCommand extends Command {

    public static final String COMMAND_WORD = "viewtask";
    public static final String COMMAND_ALIAS = "vt";
    public static final String DATA_FOLDER = "data/";
    public static final String TASK_PAGE = "tasklist.html";
    public static final String BAR_PAGE = "bar.html";
    public static final String CHECKER_PAGE = "progresschecker.html";
    public static final String PROGRESS_CHECKER_PAGE = "/view/progresschecker.html";
    public static final String FILE_FAILURE = "Something is wrong with the file system.";
    public static final String BAR_FAILURE = "Fail to get the progress bar.";
    public static final String UNKNOWN_ERROR = "Unknow error in the system occurred";
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String MESSAGE_TITLE_CONSTRAINTS = "The title of a task list should not exceed "
            + "49 characters (as specified by Google Task.";
    public static final String TASK_TAB = "task";
    public static final int MAX_TITLE_LENGTH = 49;
    public static final int MAX_WEEK = 13;
    public static final int ALL_WEEK = 0;
    public static final int COMPULSORY = -13; // parser returns -13 for compulsory tasks
    public static final int SUBMISSION = -20; // parser returns -10 for tasks need submission
    public static final String COMPULSORY_STR = "  [Compulsory]";
    public static final String SUBMISSION_STR = "  [Submission]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            // TODO: change description and parameter range when appropriate
            + ": View tasks in the default task list, filtered to show only tasks at the input week or the"
            + " input category. Only ONE filter keyword is allowed.\n"
            + "Parameters: FILTER_KEYWORD (filter by week: must be an integer ranging from 1 to 13, or an asterisk (*)"
            + " which means all weeks\n"
            + "                            filter by category: \"compulsory\" or \"com\" means compulsory. "
            + "\"submission\" or \"sub\" means to get the task that needs submission."
            + "Example: " + COMMAND_WORD + "3\n"
            + "Example: " + COMMAND_WORD + "sub";

    public static final String MESSAGE_SUCCESS = "Viewing task list: %1$s";

    // when the value of it is -13 or -20, it means the command is filtering compulsory or needsSubmission tasks
    private final int targetWeek;

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    public ViewTaskListCommand(int targetWeek) {
        this.targetWeek = targetWeek;
    }

    @Override
    public CommandResult execute() throws CommandException {
        updateView();

        String result = chooseResult();
        return new CommandResult(result);
    }

    /**
     * Updates the HTML file and refresh the browser panel
     *
     * @throws CommandException
     */
    public void updateView() throws CommandException {
        List<Task> list = TaskListUtil.searchTaskListById(DEFAULT_LIST_ID);
        List<Task> filteredList = new LinkedList<Task>();
        List<Integer> indexList = new LinkedList<Integer>();

        try {
            applyFilter (filteredList, indexList, list);
        } catch (CommandException ce) {
            throw ce;
        }

        TaskCommandUtil util = new TaskCommandUtil();
        File htmlFile = new File(DATA_FOLDER + TASK_PAGE);
        int progressInt = util.writeToHtml(filteredList, indexList, htmlFile, targetWeek);
        File htmlBarFile = new File(DATA_FOLDER + BAR_PAGE);
        util.writeToHtmlBar(progressInt, htmlBarFile, targetWeek);
        File htmlCheckerFile = new File(DATA_FOLDER + CHECKER_PAGE);
        util.writeToHtmlChecker(htmlCheckerFile);
        try {
            EventsCenter.getInstance().post(new LoadBarEvent(readFile(htmlBarFile.getAbsolutePath(),
                    StandardCharsets.UTF_8)));
            EventsCenter.getInstance().post(new LoadTaskEvent(readFile(htmlFile.getAbsolutePath(),
                    StandardCharsets.UTF_8)));
            EventsCenter.getInstance().post(new TabLoadChangedEvent(TASK_TAB));
        } catch (IOException ioe) {
            logger.info(FILE_FAILURE);
            throw new CommandException(FILE_FAILURE);
        }
    }

    /**
     * Applies the filter argument to get the filtered list.
     *
     * @param filteredList the resulting list with filters applied.
     * @param indexList the corresponding indices for {@code List<Task> filteredList}.
     * @param list the raw list before processing.
     * @throws CommandException
     */
    private void applyFilter (List<Task> filteredList, List<Integer> indexList, List<Task> list)
            throws CommandException {
        if (targetWeek > 0) {
            int count = 1;
            for (Task task : list) {
                if (task.getTitle().contains("LO[W" + targetWeek)) {
                    filteredList.add(task);
                    indexList.add(count);
                }
                count++;
            }
        } else if (targetWeek == ALL_WEEK) {
            int count = 1;
            for (Task task : list) {
                if (task.getTitle().contains("LO[W")) {
                    filteredList.add(task);
                    indexList.add(count);
                }
                count++;
            }
        } else if (targetWeek == COMPULSORY) {
            int count = 1;
            for (Task task : list) {
                if (task.getTitle().contains("[Compulsory]")) {
                    filteredList.add(task);
                    indexList.add(count);
                }
                count++;
            }
        } else if (targetWeek == SUBMISSION) {
            int count = 1;
            for (Task task : list) {
                if (task.getTitle().contains("[Submission]")) {
                    filteredList.add(task);
                    indexList.add(count);
                }
                count++;
            }
        } else {
            throw new CommandException(UNKNOWN_ERROR);
        }
    }

    /**
     * Choose a proper response message according to the filter argument.
     *
     * @return String the response message.
     * @throws CommandException
     */
    private String chooseResult () throws CommandException {
        if (targetWeek > 0) {
            return String.format(MESSAGE_SUCCESS,
                    DEFAULT_LIST_TITLE + "  Week: " + targetWeek);
        } else if (targetWeek == ALL_WEEK) {
            return String.format(MESSAGE_SUCCESS, DEFAULT_LIST_TITLE);
        } else if (targetWeek == COMPULSORY) {
            return String.format(MESSAGE_SUCCESS,
                    DEFAULT_LIST_TITLE + COMPULSORY_STR);
        } else if (targetWeek == SUBMISSION) {
            return String.format(MESSAGE_SUCCESS,
                    DEFAULT_LIST_TITLE + SUBMISSION_STR);
        } else {
            // the command parser could never pass any value other than the above 4, thus we say "unknown error".
            throw new CommandException(UNKNOWN_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTaskListCommand // instanceof handles nulls
                && targetWeek == (((ViewTaskListCommand) other).targetWeek));
    }
}
