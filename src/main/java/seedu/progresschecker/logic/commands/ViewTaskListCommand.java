package seedu.progresschecker.logic.commands;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_ID;
import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_TITLE;
import static seedu.progresschecker.model.task.TaskUtil.NOTE_TOKEN;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.google.api.services.tasks.model.Task;

import seedu.progresschecker.commons.core.EventsCenter;
import seedu.progresschecker.commons.events.ui.LoadBarEvent;
import seedu.progresschecker.commons.events.ui.LoadTaskEvent;
import seedu.progresschecker.commons.events.ui.TabLoadChangedEvent;
import seedu.progresschecker.commons.util.FileUtil;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.task.TaskListUtil;

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
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String MESSAGE_TITLE_CONSTRAINTS = "The title of a task list should not exceed "
            + "49 characters (as specified by Google Task.";
    public static final String TASK_TAB = "task";
    public static final int MAX_TITLE_LENGTH = 49;
    public static final int MAX_WEEK = 13;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            // TODO: change description and parameter range when appropriate
            + ": View tasks in the default task list, filtered to show only tasks at the input week.\n"
            + "Parameters: WEEK (must be an integer ranging from 1 to 13, or an asterisk (*) "
            + "which means all weeks\n"
            + "Example: " + COMMAND_WORD + "3";

    public static final String MESSAGE_SUCCESS = "Viewing task list: %1$s";

    private final int targetWeek;

    public ViewTaskListCommand(int targetWeek) {
        this.targetWeek = targetWeek;
    }

    @Override
    public CommandResult execute() throws CommandException {
        updateView();

        if (targetWeek > 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    DEFAULT_LIST_TITLE + "  Week: " + targetWeek));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, DEFAULT_LIST_TITLE));
        }
    }

    /**
     * Updates the HTML file and refresh the browser panel
     * @throws CommandException
     */
    public void updateView() throws CommandException {
        List<Task> list = TaskListUtil.searchTaskListById(DEFAULT_LIST_ID);
        List<Task> filteredList = new LinkedList<Task>();
        List<Integer> indexList = new LinkedList<Integer>();
        if (targetWeek > 0) {
            int count = 1;
            for (Task task : list) {
                if (task.getTitle().contains("LO[W" + targetWeek)) {
                    filteredList.add(task);
                    indexList.add(count);
                }
                count++;
            }
        } else {
            filteredList = list;
            int size = list.size();
            for (int i = 1; i <= size; i++) {
                indexList.add(i);
            }
        }

        File htmlFile = new File(DATA_FOLDER + TASK_PAGE);
        int progressInt = writeToHtml(filteredList, indexList, htmlFile);
        File htmlBarFile = new File(DATA_FOLDER + BAR_PAGE);
        writeToHtmlBar(progressInt, htmlBarFile);
        File htmlCheckerFile = new File(DATA_FOLDER + CHECKER_PAGE);
        writeToHtmlChecker(htmlCheckerFile);
        try {
            EventsCenter.getInstance().post(new TabLoadChangedEvent(TASK_TAB));
            EventsCenter.getInstance().post(new LoadBarEvent(readFile(htmlBarFile.getAbsolutePath(),
                    StandardCharsets.UTF_8)));
            EventsCenter.getInstance().post(new LoadTaskEvent(readFile(htmlFile.getAbsolutePath(),
                    StandardCharsets.UTF_8)));
        } catch (IOException ioe) {
            throw new CommandException(FILE_FAILURE);
        }
    }


    /**
     * Writes the loaded task list to an html file. Loads the tasks.
     *
     * @param list task list serialized in a java List.
     * @param indexList stores the corresponding index in the full list (the current showing list is a filtered
     *                  result.
     * @param file File object of the html file.
     * @return progressInt the percentage of task completed (without the "%" sign)
     */
    int writeToHtml(List<Task> list, List<Integer> indexList, File file) throws CommandException {
        double countCompleted = 0;
        double countIncomp = 0;

        int size = list.size();

        try {
            FileUtil.createIfMissing(file);

            FileWriter fw1 = new FileWriter(file, false);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter out1 = new PrintWriter(bw1);

            out1.print("");

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.print("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <title>Task List</title>\n"
                    + "    <meta charset=\"utf-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7"
                    + "/css/bootstrap.min.css\">\n"
                    + "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1"
                    + "/jquery.min.js\"></script>\n"
                    + "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7"
                    + "/js/bootstrap.min.js\"></script>\n"
                    + "</head>\n"
                    + "<body  style=\"background-color:grey;\">"
                    + "<div class=\"container\"  style=\"background-color:grey;\">");
            if (targetWeek > 0) {
                out.print("<h2 style=\"font-family:verdana; color:white\">"
                        + DEFAULT_LIST_TITLE + "  Week: " + targetWeek + "</h2>\n<br>\n");
            } else {
                out.print("<h2 style=\"font-family:verdana; color:white\">"
                        + DEFAULT_LIST_TITLE + "</h2>\n<br>\n");
            }

            for (int i = 0; i < size; i++) {
                Task task = list.get(i);
                int index = indexList.get(i);

                String status = task.getStatus();
                String notesWithUrl = task.getNotes();
                String[] parts = notesWithUrl.split(NOTE_TOKEN);

                String notes = parts[0];
                String url = parts[1];

                if (status.length() >= 11) {
                    out.print("        <div class=\"panel panel-danger\">\n"
                            + "            <div class=\"panel-heading\">"
                            + index + ". " + task.getTitle() + "</div>\n"
                            + "            <div class=\"panel-body\">\n"
                            + "                <dd style=\"font-family:verdana; color:black;\">&#9888; &nbsp;"
                            + task.getDue().toString().substring(0, 10) + "</dd>\n"
                            + "                <dd style=\"font-family:verdana; color:red;\">&#9873;"
                            + " &nbsp;Please work on it :) &#9744;</dd>\n");
                    countIncomp++;
                } else {
                    out.print("        <div class=\"panel panel-success\">\n"
                            + "            <div class=\"panel-heading\">"
                            + index + ". " + task.getTitle() + "</div>\n"
                            + "            <div class=\"panel-body\">\n"
                            + "                <dd style=\"font-family:verdana; color:black;\">&#9888; &nbsp;"
                            + task.getDue().toString().substring(0, 10) + "</dd>\n"
                            + "                <dd style=\"font-family:verdana; color:darkseagreen;\">&#9873;"
                            + " &nbsp;Completed! &#9745;</dd>\n");
                    countCompleted++;
                }

                out.print("                <dd style=\"font-family:verdana; color:black;\">&#9998; &nbsp;"
                        + notes + "</dd>\n"
                        + "                <p><a href=\""
                        + url
                        + "\">"
                        + url
                        + "</a></p>\n"
                        + "            </div>\n"
                        + "        </div>\n");
            }

            out.print("    </div>\n"
                    + "</div>\n"
                    + "\n");

            double percent = countCompleted / (countCompleted + countIncomp);
            int progressInt = (int) (percent * 100);
            String progressDevision = (int) countCompleted + "/" + (int) (countCompleted + countIncomp);

            out.print("</dl>\n");

            out.print("<h2 style=\"font-family:verdana; color:white\">" + "You have completed " + progressDevision
                    + " !" + "</h2>");

            out.print("</body>\n"
                    + "</html>");

            out1.close();
            out.close();

            return progressInt;

        } catch (IOException e) {
            throw new CommandException(FILE_FAILURE);

        }
    }

    /**
     * Writes the progress bar to an html file.
     *
     * @param percentage the percentage of completed tasks.
     * @param file File object of the html file.
     */
    void writeToHtmlBar(int percentage, File file) throws CommandException {
        try {
            FileUtil.createIfMissing(file);

            FileWriter fw1 = new FileWriter(file, false);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter out1 = new PrintWriter(bw1);

            out1.print("");

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.print("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head>\n"
                    + "    <title>progresschecker</title>\n"
                    + "    <meta charset=\"utf-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    <link rel=\"stylesheet\" "
                    + "href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n"
                    + "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\">"
                    + "</script>\n"
                    + "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\">"
                    + "</script>\n"
                    + "</head>\n"
                    + "<body  style=\"background-color:grey;\">\n"
                    + "<div class=\"container\">\n"
                    + "    <h2 style = \"font-size: x-large; color: white;\">Your Progress: "
                    + percentage + "%</h2>\n"
                    + "    <br>\n"
                    + "    <div class=\"progress\">\n"
                    + "        <div class=\"progress-bar progress-bar-striped active\" role=\"progressbar\" "
                    + "aria-valuenow=\"" + percentage + "\" aria-valuemin=\"0\" aria-valuemax=\"100\" "
                    + "style=\"width:" + percentage + "%\">\n"
                    + "            " + percentage + "% (on the way)\n"
                    + "        </div>\n"
                    + "    </div>\n"
                    + "</div>\n"
                    + "</body>\n"
                    + "</html>\n");

            out1.close();
            out.close();

        } catch (IOException e) {
            throw new CommandException(BAR_FAILURE);

        }
    }

    /**
     * Writes an html file to involve both the progress bar and task list (the file is for backup,
     * preview and debugging purposes.
     *
     * @param file File object of the html file.
     */
    void writeToHtmlChecker(File file) throws CommandException {
        try {
            FileUtil.createIfMissing(file);

            FileWriter fw1 = new FileWriter(file, false);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter out1 = new PrintWriter(bw1);

            out1.print("");

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            out.print("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<frameset rows=\"20%,80%\">\n"
                    + "    <frame src=\"bar.html\" />\n"
                    + "    <frame src=\"tasklist.html\" />\n"
                    + "</frameset>\n"
                    + "</html>\n");

            out1.close();
            out.close();

        } catch (IOException e) {
            throw new CommandException(BAR_FAILURE);

        }
    }

    /**
     * Reads the content of a text file to a String.
     *
     * @param path file path
     * @param encoding the encoding standard, such as StandardCharsets.UTF_8.
     */
    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * Reads the relative path to the resource directory from the <code>RESOURCE_PATH</code> file located in
     * <code>src/main/resources</code>
     * @return the relative path to the <code>resources</code> in the file system, or
     *         <code>null</code> if there was an error
     */
    private static String getResourcePath(String resourcePaths) {
        try {
            URI resourcePathFile = System.class.getResource(resourcePaths).toURI();
            String resourcePath = Files.readAllLines(Paths.get(resourcePathFile)).get(0);
            URI rootUri = new File("").toURI();
            URI resourceUri = new File(resourcePath).toURI();
            URI relativeResourceUri = rootUri.relativize(resourceUri);
            return relativeResourceUri.getPath();
        } catch (Exception e) {
            return null;
        }
    }
}
