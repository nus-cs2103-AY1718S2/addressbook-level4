package seedu.progresschecker.logic.commands;

import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_TITLE;
import static seedu.progresschecker.model.task.TaskUtil.NOTE_TOKEN;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.services.tasks.model.Task;

import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.util.FileUtil;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.ui.CommandBox;

//@@author EdwardKSG
/**
 * Some util methods to be used by task-relevant commands.
 */
public class TaskCommandUtil {

    public static final String COMMAND_WORD = "viewtask";
    public static final String COMMAND_ALIAS = "vt";
    public static final String FILE_FAILURE = "Something is wrong with the file system.";
    public static final String BAR_FAILURE = "Fail to get the progress bar.";
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final int COMPULSORY = -13; // parser returns -13 for compulsory tasks
    public static final int SUBMISSION = -20; // parser returns -10 for tasks need submission
    public static final String COMPULSORY_STR = "  [Compulsory]";
    public static final String SUBMISSION_STR = "  [Submission]";
    public static final String TITLE_COLOR_DAY = "grey";
    public static final String BACKGROUND_COLOR_DAY = "white";
    public static final String TITLE_COLOR_NIGHT = "white";
    public static final String BACKGROUND_COLOR_NIGHT = "#202226";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    /**
     * Writes the loaded task list to an html file. Loads the tasks.
     *
     * @param list task list serialized in a java List.
     * @param indexList stores the corresponding index in the full list (the current showing list is a filtered
     *                  result.
     * @param file File object of the html file.
     * @param targetWeek indicates the filter argument received for viewing task list
     * @return progressInt the percentage of task completed (without the "%" sign)
     */
    int writeToHtml(List<Task> list, List<Integer> indexList, File file, int targetWeek) throws CommandException {
        String backgroundColor = BACKGROUND_COLOR_DAY;
        String titleColor = TITLE_COLOR_DAY;
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
                    + "<body  style=\"background-color:" + backgroundColor + ";\">"
                    + "<div class=\"container\"  style=\"background-color:" + backgroundColor + ";\">");
            if (targetWeek > 0) {
                out.print("<h2 style=\"font-family:verdana; color:" + titleColor + "\">"
                        + DEFAULT_LIST_TITLE + "  Week: " + targetWeek + "</h2>\n<br>\n");
            } else if (targetWeek == COMPULSORY) {
                out.print("<h2 style=\"font-family:verdana; color:" + titleColor + "\">"
                        + DEFAULT_LIST_TITLE + COMPULSORY_STR + "</h2>\n<br>\n");
            } else if (targetWeek == SUBMISSION) {
                out.print("<h2 style=\"font-family:verdana; color:" + titleColor + "\">"
                        + DEFAULT_LIST_TITLE + SUBMISSION_STR + "</h2>\n<br>\n");
            } else {
                out.print("<h2 style=\"font-family:verdana; color:" + titleColor + "\">"
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
            logger.info(FILE_FAILURE);
            throw new CommandException(FILE_FAILURE);

        }
    }

    /**
     * Writes the progress bar to an html file.
     *
     * @param percentage the percentage of completed tasks.
     * @param file File object of the html file.
     * @param targetWeek indicates the filter argument received for viewing task list
     */
    void writeToHtmlBar(int percentage, File file, int targetWeek) throws CommandException {
        String backgroundColor = BACKGROUND_COLOR_DAY;
        String titleColor = TITLE_COLOR_DAY;

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
                    + "<body  style=\"background-color:" + backgroundColor + ";\">\n"
                    + "<div class=\"container\">\n"
                    + "    <h2 style = \"font-size: x-large; color: " + titleColor + ";\">Your Progress");

            if (targetWeek > 0) {
                out.print("(Week" + targetWeek + ")");
            } else if (targetWeek == COMPULSORY) {
                out.print("([Compulsory])");
            } else if (targetWeek == SUBMISSION) {
                out.print("([Submission])");
            }

            out.print(": "
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
            logger.info(FILE_FAILURE);
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
            logger.info(FILE_FAILURE);
            throw new CommandException(FILE_FAILURE);

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
}
