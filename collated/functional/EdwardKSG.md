# EdwardKSG
###### \java\seedu\progresschecker\commons\events\ui\LoadBarEvent.java
``` java
/**
 * Represents a page change in the second Browser Panel which shows the progress bar.
 */
public class LoadBarEvent extends BaseEvent {


    public final String content;

    public LoadBarEvent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getContent() {
        return content;
    }

}
```
###### \java\seedu\progresschecker\commons\events\ui\LoadTaskEvent.java
``` java
/**
 * Represents a page change in the Browser Panel
 */
public class LoadTaskEvent extends BaseEvent {


    public final String content;

    public LoadTaskEvent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getContent() {
        return content;
    }

}
```
###### \java\seedu\progresschecker\commons\events\ui\LoadUrlEvent.java
``` java
/**
 * Represents a page change in the Browser Panel
 */
public class LoadUrlEvent extends BaseEvent {


    public final String url;

    public LoadUrlEvent(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getUrl() {
        return url;
    }

}
```
###### \java\seedu\progresschecker\logic\apisetup\ConnectTasksApi.java
``` java
/**
 * Sets up Google Tasks API.
 * i. Authorizes data access based on client credentials.
 * ii. Builds service (initializes API).
 */
public class ConnectTasksApi {
    private static final String appName = "ProgressChecker";
    private JsonFactory jsonFactory;
    private HttpTransport transport;
    private Credential credentials;

    public ConnectTasksApi() {
        this.jsonFactory = new JacksonFactory();
        this.transport = new NetHttpTransport();
        this.credentials = null;
    }

    /**
     * Authorizes the data access requested by Tasks API by loading client secrets file.
     */
    public void authorize() throws Exception {
        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(
                FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        // Sets up files to store access token
        DataStoreFactory datastore = new FileDataStoreFactory(
                new File("tokens")
        );

        InputStream in =
                ConnectTasksApi.class.getResourceAsStream("/client_id.json");

        // Loads Client Secrets file downloaded from Google developer console.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                this.jsonFactory,
                new InputStreamReader(in)
        );

        // Sets Up authorization code flow.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                this.transport,
                this.jsonFactory,
                clientSecrets,
                Collections.singleton(TasksScopes.TASKS)
        ).setDataStoreFactory(datastore).build();

        // Authorizes with client credentials.
        this.credentials = new AuthorizationCodeInstalledApp(
                flow,
                new LocalServerReceiver()
        ).authorize("user");
    }

    /**
     * Builds Google Tasks service.
     */
    public Tasks getTasksService() {
        return new Tasks.Builder(
                this.transport,
                this.jsonFactory,
                this.credentials
        ).setApplicationName(appName).build();
    }
}
```
###### \java\seedu\progresschecker\logic\commands\AddDefaultTasksCommand.java
``` java
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
```
###### \java\seedu\progresschecker\logic\commands\CompleteTaskCommand.java
``` java
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
    public static final String UNKNOWN_ERROR = "Unknow error in the system occurred";

    public static final int ERROR = -1;
    public static final int NO_ACTION = 0;
    public static final int SUCCESS = 1;

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

            if (result.getKey() == ERROR) {
                return new CommandResult(String.format(result.getValue()));
            }

            String titleWithCode = result.getValue();     // full file name
            String[] parts = titleWithCode.split("&#"); // String array, each element is text between dots

            String title = parts[0];

            if (result.getKey() == NO_ACTION) {
                return new CommandResult(String.format(MESSAGE_NO_ACTION, index + ". " + title));
            } else if (result.getKey() == SUCCESS) {
                ViewTaskListCommand view = LogicManager.getCurrentViewTask();
                view.updateView();
                return new CommandResult(String.format(MESSAGE_SUCCESS, index + ". " + title));
            } else {
                // the command parser could never pass any value other than the above 4, thus we say "unknown error".
                throw new CommandException(UNKNOWN_ERROR);
            }

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
```
###### \java\seedu\progresschecker\logic\commands\GoToTaskUrlCommand.java
``` java
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
            EventsCenter.getInstance().post(new TabLoadChangedEvent(TASK_TAB));

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
```
###### \java\seedu\progresschecker\logic\commands\ResetTaskCommand.java
``` java
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
    public static final String UNKNOWN_ERROR = "Unknow error in the system occurred";

    public static final int ERROR = -1;
    public static final int NO_ACTION = 0;
    public static final int SUCCESS = 1;

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

            if (result.getKey() == ERROR) {
                return new CommandResult(String.format(result.getValue()));
            }

            String titleWithCode = result.getValue();     // full file name
            String[] parts = titleWithCode.split("&#"); // String array, each element is text between dots

            String title = parts[0];

            if (result.getKey() == NO_ACTION) {
                return new CommandResult(String.format(MESSAGE_NO_ACTION, index + ". " + title));
            } else if (result.getKey() == SUCCESS) {
                ViewTaskListCommand view = LogicManager.getCurrentViewTask();
                view.updateView();
                return new CommandResult(String.format(MESSAGE_SUCCESS, index + ". " + title));
            } else {
                // the command parser could never pass any value other than the above 4, thus we say "unknown error".
                throw new CommandException(UNKNOWN_ERROR);
            }
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
```
###### \java\seedu\progresschecker\logic\commands\TaskCommandUtil.java
``` java
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
```
###### \java\seedu\progresschecker\logic\commands\ViewTaskListCommand.java
``` java
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
```
###### \java\seedu\progresschecker\logic\parser\CompleteTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CompleteTaskCommand object
 */
public class CompleteTaskCommandParser implements Parser<CompleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompleteTaskCommand
     * and returns an CompleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompleteTaskCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTaskIndex(args);
            return new CompleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_INDEX_OR_FORMAT, CompleteTaskCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\progresschecker\logic\parser\GoToTaskUrlCommandParser.java
``` java
/**
 * Parses input arguments and creates a new GoToTaskUrlCommand object
 */
public class GoToTaskUrlCommandParser implements Parser<GoToTaskUrlCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GoToTaskUrlCommand
     * and returns a GoToTaskUrlCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GoToTaskUrlCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTaskIndex(args);
            return new GoToTaskUrlCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_INDEX_OR_FORMAT, GoToTaskUrlCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code String} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static int parseTaskIndex(String index) throws IllegalValueException {
        String trimmedIndex = index.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Integer.parseInt(trimmedIndex);
    }

    /**
     * Parses {@code String} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified week number is invalid (neither an integer ranging from 1 to 13
     * nor an asterisk(*)).
     */
    public static int parseTaskWeek(String week) throws IllegalValueException {
        String trimmedWeek = week.trim();
        if (trimmedWeek.equals("*")) {
            return ALL_WEEK;
        } else if (trimmedWeek.equals("sub") || trimmedWeek.equals("submission")) {
            return SUBMISSION;
        } else if (trimmedWeek.equals("com") || trimmedWeek.equals("compulsory")) {
            return COMPULSORY;
        } else if (!StringUtil.isNonZeroUnsignedInteger(trimmedWeek)) {
            throw new IllegalValueException(MESSAGE_INVALID_TASK_FILTER);
        }
        int intWeek = Integer.parseInt(trimmedWeek);
        if (intWeek >= 1 && intWeek <= 13) {
            return intWeek;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TASK_FILTER);
        }
    }
```
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String Title} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code Title} is invalid.
     */
    public static String parseTaskTitle(String title) throws IllegalValueException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (trimmedTitle.length() > MAX_TITLE_LENGTH) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        return trimmedTitle;
    }
```
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String username} into a {@code GithubUsername}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code username} is invalid.
     */

    public static GithubUsername parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!GithubUsername.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(GithubUsername.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new GithubUsername(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<GithubUsername>}
     *     if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<GithubUsername> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }
```
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String major} into an {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws IllegalValueException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new IllegalValueException(Major.MESSAGE_MAJOR_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
    }

    /**
     * Parses a {@code Optional<String> major} into an {@code Optional<Major>} if {@code major} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Major> parseMajor(Optional<String> major) throws IllegalValueException {
        requireNonNull(major);
        return major.isPresent() ? Optional.of(parseMajor(major.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String year} into an {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws IllegalValueException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new IllegalValueException(Year.MESSAGE_YEAR_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses a {@code Optional<String> year} into an {@code Optional<Year>} if {@code year} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Year> parseYear(Optional<String> year) throws IllegalValueException {
        requireNonNull(year);
        return year.isPresent() ? Optional.of(parseYear(year.get())) : Optional.empty();
    }
```
###### \java\seedu\progresschecker\logic\parser\ResetTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ResetTaskCommand object
 */
public class ResetTaskCommandParser implements Parser<ResetTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ResetTaskCommand
     * and returns an ResetTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ResetTaskCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseTaskIndex(args);
            return new ResetTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_INDEX_OR_FORMAT, ResetTaskCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\progresschecker\logic\parser\ViewTaskListCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ViewTaskListCommand object
 */
public class ViewTaskListCommandParser implements Parser<ViewTaskListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewTaskListCommand
     * and returns an ViewTaskListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTaskListCommand parse(String args) throws ParseException {
        try {
            int week = ParserUtil.parseTaskWeek(args);
            return new ViewTaskListCommand(week);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_TASK_FILTER, ViewTaskListCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\progresschecker\model\person\GithubUsername.java
``` java
/**
 * Represents a Person's Github username in the ProgressChecker.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class GithubUsername {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Person Github usernames should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String username;

    /**
     * Constructs a {@code GithubUsername}.
     *
     * @param username A valid username.
     */
    public GithubUsername(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        this.username = username;
    }

    /**
     * Returns true if a given string is a valid Github username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GithubUsername // instanceof handles nulls
                && this.username.equals(((GithubUsername) other).username)); // state check
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\person\Major.java
``` java
/**
 * Represents a Person's major in the ProgressChecker.
 * Guarantees: immutable; is valid as declared in {@link #isValidMajor(String)}
 */
public class Major {

    public static final String MESSAGE_MAJOR_CONSTRAINTS =
            "Person majors can take any values, and it should not be blank";

    /*
     * The first character of the major must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MAJOR_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Major}.
     *
     * @param major A valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_MAJOR_CONSTRAINTS);
        this.value = major;
    }

    /**
     * Returns true if a given string is a valid person major.
     */
    public static boolean isValidMajor(String test) {
        return test.matches(MAJOR_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && this.value.equals(((Major) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\person\Person.java
``` java
    public GithubUsername getUsername() {
        return username;
    }

    public Major getMajor() {
        return major;
    }

    public Year getYear() {
        return year;
    }
```
###### \java\seedu\progresschecker\model\person\Year.java
``` java
/**
 * Represents a Person's year of study in the ProgressChecker.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)}
 */
public class Year {

    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "Person years of study can take digits ranging from 1 to 5, it can be left blank";

    /*
     * It accepts single digits ranging from 1 to 5.
     * empty string will be accepted as well, as "year" is an optional field.
     */
    public static final String YEAR_VALIDATION_REGEX = "(^$|^[1-5]$)";

    public final String value;

    /**
     * Constructs an {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        this.value = year;
    }

    /**
     * Returns true if a given string is a valid year of study.
     */
    public static boolean isValidYear(String test) {
        return test.matches(YEAR_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && this.value.equals(((Year) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\progresschecker\model\task\SimplifiedTask.java
``` java
/**
 * Represents a simplified task in the ProgressChecker.
 * It is called "simplified" because it extracts only the attributes we want to use from the Google Tasks
 */
public class SimplifiedTask {
    public final String title;
    public final String notes;
    public final String due;

    public SimplifiedTask (String title, String notes, String due) {
        this.title = title;
        this.notes = notes;
        this.due = due;
    }

    public String getTitle () {
        return this.title;
    }

    public String getNotes () {
        return this.notes;
    }

    public String getDue () {
        return this.due;
    }

}
```
###### \java\seedu\progresschecker\model\task\TaskListUtil.java
``` java
/**
 * Include customized methods (based on Google Tasks API) to manipulate task lists.
 */
public class TaskListUtil {

    public static final String AUTHORIZE_FAILURE = "Failed to authorize tasks api client credentials";
    public static final String ADD_FAILURE = "Failed to add new task list to account";
    public static final String LOAD_FAILURE = "Failed to load this task list (might be wrong title)";

    /**
     * Creates a new task list with title {@code String} and adds to the current list of task lists
     *
     * @param listTitle title of the task list we intend to create
     */
    public static void createTaskList(String listTitle) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            service.tasklists().insert(
                    new TaskList().setTitle(listTitle)
            ).execute();
        } catch (IOException ioe) {
            throw new CommandException(ADD_FAILURE);
        }
    }

    /**
     * Finds the task list with title {@code String} from the current list of task lists
     *
     * @param listTitle title of the task list we look for
     * @return the List instances containing all tasks in the specified task list
     */
    public static List<Task> searchTaskList(String listTitle) throws CommandException {
        List<Task> list = null;

        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            TaskLists taskLists = service.tasklists().list().execute();
            TaskList taskList = taskLists.getItems().stream()
                    .filter(t -> t.getTitle().equals(listTitle))
                    .findFirst()
                    .orElse(null);
            String id = taskList.getId();
            Tasks tasks = service.tasks().list(id).execute();
            list = tasks.getItems();
        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

        return list;
    }

    /**
     * Finds the task list with ID {@code String} from the current list of task lists
     *
     * @param listId title of the task list we look for
     * @return the List instances containing all tasks in the specified task list
     */
    public static List<Task> searchTaskListById(String listId) throws CommandException {
        List<Task> list = null;

        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            Tasks tasks = service.tasks().list(listId).execute();
            list = tasks.getItems();
        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

        return list;
    }

    /**
     * Changes the name of task list with id {@code String} to {@code String}
     *
     * @param listId identifier of the target task list whose name will be changed
     * @param listTitle title of the task list we look for
     */
    public static void setTaskListTitle(String listId, String listTitle) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            TaskList taskList = service.tasklists().get(listId).execute();

            taskList.setTitle(listTitle);

            TaskList result = service.tasklists().update(
                    taskList.getId(),
                    taskList
            ).execute();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Copies tasks in the task list with id {@code String} to the task list with title {@code String}
     *
     * @param listId identifier of the target task list whose name will be changed
     * @param listTitle title of the task list we look for
     */
    public static void copyTaskList(String listTitle, String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            TaskLists taskLists = service.tasklists().list().execute();

            Tasks baseTasks = service.tasks().list(listId).execute();

            TaskList targetTaskList = taskLists.getItems().stream()
                    .filter(t -> t.getTitle().equals(listTitle))
                    .findFirst()
                    .orElse(null);
            String id = targetTaskList.getId();

            for (Task task : baseTasks.getItems()) {
                Task t = new Task();
                t.setTitle(task.getTitle());
                t.setStatus(task.getStatus());
                t.setDue(task.getDue());
                t.setNotes(task.getNotes());
                service.tasks().insert(id, t).execute();
            }


        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Removes all tasks in the task list with id {@code String}
     *
     * @param listId identifier of the target task list whose content will be removed
     */
    public static void clearTaskList(String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {

            Tasks tasks = service.tasks().list(listId).execute();
            for (Task task : tasks.getItems()) {
                service.tasks().delete(listId, task.getId()).execute();
            }

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }
}
```
###### \java\seedu\progresschecker\model\task\TaskUtil.java
``` java
/**
 * Include customized methods (based on Google Tasks API) to manipulate tasks.
 */
public class TaskUtil {

    public static final String AUTHORIZE_FAILURE = "Failed to authorize tasks api client credentials";
    public static final String LOAD_FAILURE = "Failed to load this task list";
    public static final String INDEX_OUT_OF_BOUND = "The index is out of bound";
    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm";
    public static final String COMPLETED = "completed";
    public static final String NEEDS_ACTION = "needsAction";
    public static final int ERROR_NUMBER = -1;
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    public static final String ERROR_STRING = "";
    public static final String NOTE_TOKEN = "checkurl";


    /**
     * Finds the task with title {@code String} in the tasklist with title {@code String}
     *
     * @param taskTitle title of the task we look for
     * @param listTitle the title of the list to which the task belongs
     * @return the Task instances
     */
    public static Task searchTask(String taskTitle, String listTitle) throws CommandException {
        Task task = null;

        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            TaskLists taskLists = service.tasklists().list().execute();
            TaskList taskList = taskLists.getItems().stream()
                    .filter(t -> t.getTitle().equals(listTitle))
                    .findFirst()
                    .orElse(null);

            Tasks tasks = service.tasks().list(taskList.getId()).execute();
            task = tasks.getItems().stream()
                    .filter(t -> t.getTitle().equals(taskTitle))
                    .findFirst()
                    .orElse(null);

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

        return task;
    }

    /**
     * Creates a task with title {@code String} to the tasklist with ID {@code String}
     *
     * @param taskTitle title of the task we want to create
     * @param listId the identifier of the list to which the task will be added
     * @param notes description or relevant URL link to this task
     * @param due the date and time of the deadline, in format "MM/dd/yyyy HH:mm"
     */
    public static void createTask(String taskTitle, String listId, String notes, String due)
            throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            TaskLists taskLists = service.tasklists().list().execute();

            Task task = service.tasks().insert(
                    listId,
                    new Task().setTitle(taskTitle)
                              .setDue(getDate(due))
                              .setNotes(notes)
                              .setStatus(NEEDS_ACTION)
            ).execute();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

    }

    /**
     * Adds a group of tasks to a Google Task List with Id {@code String listId}
     *
     * @param simpleList a list of {@code SimplifiedTask}
     * @param listId the identifier of the list to which the task will be added
     */
    public static void addMultipleTask(SimplifiedTask[] simpleList, String listId)
            throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            for (SimplifiedTask simpleTask: simpleList) {
                Task task = service.tasks().insert(
                        listId,
                        new Task().setTitle(simpleTask.getTitle())
                                .setDue(getDate(simpleTask.getDue()))
                                .setNotes(simpleTask.getNotes())
                                .setStatus(NEEDS_ACTION)
                ).execute();
            }


        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }

    }

    /**
     * Converts a string in format "MM/dd/yyyy HH:mm" to a DateTime object
     *
     * @param s string in format "MM/dd/yyyy HH:mm", representing a date
     * @return the DateTime instances, or null if encountered error when parsing
     */
    public static DateTime getDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date date = simpleDateFormat.parse(s);
            return new DateTime(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * Marks the task with index {@code int index} in the tasklist with ID {@code String listId} as completed
     *
     * @param index title of the task we look for
     * @param listId the identifier of the list to which the task belongs
     * @return result whether this command made any change of the task list (0 means no change) and
     * the title of the task with index {@code int}
     */
    public static Pair<Integer, String> completeTask(int index, String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            int isChanged = FALSE;
            Tasks tasks = service.tasks().list(listId).execute();
            List<Task> list = tasks.getItems();
            if (list.size() < index) {
                Pair<Integer, String> result = new Pair<Integer, String>(ERROR_NUMBER, INDEX_OUT_OF_BOUND);
                return result;
            }
            Task task = list.get(index - 1);

            if (!task.getStatus().equals(COMPLETED)) {
                task.setStatus(COMPLETED);
                isChanged = TRUE;
            }

            task = service.tasks().update(
                    listId,
                    task.getId(),
                    task
            ).execute();

            Pair<Integer, String> result = new Pair<Integer, String>(isChanged, task.getTitle());

            return result;

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Marks the task with index {@code int index} in the tasklist with ID {@code String listId} as incompleted
     *
     * @param index title of the task we look for
     * @param listId the identifier of the list to which the task belongs
     * @return result whether this command made any change of the task list (0 means no change) and
     * the title of the task with index {@code int}
     */
    public static Pair<Integer, String> undoTask(int index, String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            int isChanged = FALSE;
            Tasks tasks = service.tasks().list(listId).execute();
            List<Task> list = tasks.getItems();
            if (list.size() < index) {
                Pair<Integer, String> result = new Pair<Integer, String>(ERROR_NUMBER, INDEX_OUT_OF_BOUND);
                return result;
            }
            Task task = list.get(index - 1);

            if (!task.getStatus().equals(NEEDS_ACTION)) {
                task.setCompleted(null);
                task.setStatus(NEEDS_ACTION);
                isChanged = TRUE;
            }

            task = service.tasks().update(
                    listId,
                    task.getId(),
                    task
            ).execute();

            Pair<Integer, String> result = new Pair<Integer, String>(isChanged, task.getTitle());

            return result;

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Retrieve the URL of task with index {@code int index} in the tasklist with ID {@code String listId}
     *
     * @param index title of the task we look for
     * @param listId the identifier of the list to which the task belongs
     * @return the URL of task with index {@code int index} in the tasklist with ID {@code String listId}
     * or error if index is out of bound. and the title of the task with index {@code int}
     */
    public static Pair<String, String> getTaskUrl(int index, String listId) throws CommandException {
        ConnectTasksApi connection = new ConnectTasksApi();

        try {
            connection.authorize();
        } catch (Exception e) {
            throw new CommandException(AUTHORIZE_FAILURE);
        }

        com.google.api.services.tasks.Tasks service = connection.getTasksService();

        try {
            Tasks tasks = service.tasks().list(listId).execute();
            List<Task> list = tasks.getItems();
            if (list.size() < index) {
                Pair<String, String> result = new Pair<String, String>(ERROR_STRING, ERROR_STRING);
                return result;
            }
            Task task = list.get(index - 1);

            String title = task.getTitle();
            String notesWithUrl = task.getNotes();
            String[] parts = notesWithUrl.split(NOTE_TOKEN);

            Pair<String, String> result = new Pair<String, String>(parts[1], title);
            return result;

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }
}
```
###### \java\seedu\progresschecker\storage\DefaultTasks.java
``` java
/**
 * Contains information of the default tasks.
 * Using an object to save data to reduce the cost of file I/O.
 */
public class DefaultTasks {
    private static final String SUB = "[Submission]";
    private static final String COM = "[Compulsory]";
    private static final String STAR = "&#9733;";
    public static SimplifiedTask[] getDefaultTasks() {
        return new SimplifiedTask[] {
            // WEEK 2
            new SimplifiedTask("LO[W2.2]" + STAR,
                    "Can use basic features of an IDE (2.2 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week2/outcomes.html",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.3]" + SUB + STAR + STAR,
                    "Can use Java Collections: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1#use-collections-lo-collections",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.4]" + SUB + STAR + STAR + STAR,
                    "Can use Java varargs feature: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1#use-varargs-lo-varargss",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.5]" + STAR + STAR,
                    "Can automate simple regression testing of text UIs (2.5 a~d): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week2/outcomes.html",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.6]" + SUB + STAR,
                    "Can use Git to save history (2.6 a~f): checkurlhttps://www.sourcetreeapp.com/",
                    "01/25/2018 23:59"),

            // WEEK 3
            new SimplifiedTask("LO[W3.1]" + STAR + STAR,
                    "Can refactor code at a basic level (3.1 a~d, c needs submission): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.2]" + SUB + STAR + STAR,
                    "Can follow a simple style guide (3.2 a~d, c needs submission): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.3]" + STAR + STAR,
                    "Can improve code readability (3.3 a~d): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.4]" + STAR + STAR,
                    "Can use good naming (3.4 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.5]" + STAR + STAR,
                    "Can avoid unsafe coding practices (3.5 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.6]" + STAR + STAR + STAR,
                    "Can write good code comments (3.6 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.7]" + STAR + STAR + STAR,
                    "Can use intermediate level features of an IDE (3.7 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.8]" + SUB + STAR,
                    "Can communicate with a remote repo (3.8 a~d, c&d need submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/samplerepo-things",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.9]" + STAR + STAR + STAR,
                    "Can traverse Git history: checkurlhttps (3.9 a~d):"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.10]" + COM + SUB + STAR,
                    "Can work with a 1KLoC code base: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1",
                    "02/01/2018 23:59"),

            // WEEK 4
            new SimplifiedTask("LO[W4.1]" + STAR + STAR + STAR,
                    "Can explain models (4.1 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.2]" + STAR,
                    "Can explain OOP (4.2 a~e): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.3]" + STAR,
                    "Can explain basic object/class structures (4.3 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.4]" + STAR,
                    "Can implement (4.4 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.5]" + SUB + STAR + STAR,
                    "Can do exception handling in code (4.5 a~d, c needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#handle-exceptions-lo-exceptions",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.6]" + SUB + STAR + STAR + STAR,
                    "Can use Java enumerations (4.6 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1/#use-enums-lo-enums",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.7]" + SUB + STAR,
                    "Can create PRs on GitHub (4.7 a~e, all need submission: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/samplerepo-pr-practice",
                    "02/08/2018 23:59"),

            // WEEK 5
            new SimplifiedTask("LO[W5.1]" + STAR + STAR + STAR,
                    "Can use intermediate-level class diagrams (5.1 a~e): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.2]" + STAR + STAR + STAR,
                    "Can explain single responsibility principle: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#follow-the-single-responsibility-principle-lo-srp",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.3]" + SUB + STAR,
                    "Can implement inheritance (5.3 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#use-inheritance-to-achieve-code-reuse-lo-inheritance",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.4]" + SUB + STAR + STAR,
                    "Can implement class-level members (5.4 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#use-class-level-members-lo-classlevel",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.5]" + SUB + STAR + STAR + STAR,
                    "Can implement composition (5.5 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#implement-a-class-lo-implementclass",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.6]" + STAR + STAR + STAR,
                    "Can implement aggregation (5.6 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.7]" + STAR + STAR + STAR,
                    "Can implement overloading (5.7 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.8]" + STAR + STAR,
                    "Can explain requirements (5.8 a~d, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#use-non-functional-requirements-lo-nfr",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.9]" + STAR + STAR + STAR,
                    "Can explain some techniques for gathering requirements (5.9 a~g): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.10]" + SUB + STAR,
                    "Can use some techniques for specifying requirements (5.10 a~k, c needs submission): "
                            + "checkurlhttps://github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#utilize-user-stories-lo-userstories",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.11]" + COM + SUB + STAR,
                    "Can work with a 2KLoC code base: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2",
                    "02/15/2018 23:59"),

            // WEEK 6
            new SimplifiedTask("LO[W6.1]" + SUB + STAR,
                    "Can use simple JUnit tests (6.1 a~e, e needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#use-junit-to-implement-unit-tests-lo-junit",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.2]" + STAR,
                    "Can follow Forking Workflow (6.2 a~e): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week6/outcomes.html",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.3]" + STAR,
                    "Can interpret basic sequence diagrams (6.3 a~f): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week6/outcomes.html",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.4]" + SUB + STAR,
                    "Can implement polymorphism (6.4 a~h, d~h need submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#use-polymorphism-lo-polymorphism",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.5]" + SUB + STAR + STAR + STAR,
                    "Can use JavaFX to build a simple GUI: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#use-java-fx-for-gui-programming-lo-javafx",
                    "02/22/2018 23:59"),

            // RECESS

            // WEEK 7
            new SimplifiedTask("LO[W7.1]" + STAR,
                    "Can record requirements of a product: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week7/outcomes.html",
                    "03/08/2018 23:59"),

            // WEEK 8
            new SimplifiedTask("LO[W8.1]" + STAR + STAR + STAR,
                    "Can apply basic product design guidelines: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week8/outcomes.html",
                    "03/15/2018 23:59"),

            // WEEK 9
            new SimplifiedTask("LO[W9.1]" + STAR + STAR,
                    "Can use models to conceptualize an OO solution: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week9/outcomes.html",
                    "03/22/2018 23:59"),

            // WEEK 10
            new SimplifiedTask("LO[W10.1]" + STAR + STAR + STAR,
                    "Can explain SE principles: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week10/outcomes.html",
                    "03/29/2018 23:59"),

            // WEEK 11
            new SimplifiedTask("LO[W11.1]" + STAR + STAR + STAR,
                    "Can explain object oriented domain models: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week11/outcomes.html",
                    "04/05/2018 23:59"),

            // WEEK 12
            new SimplifiedTask("LO[W12.1]" + STAR + STAR + STAR,
                    "Can explain some UML models: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week12/outcomes.html",
                    "04/12/2018 23:59"),

            // WEEK 13
            new SimplifiedTask("LO[W13.1]" + STAR,
                    "Can demo a product: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week13/outcomes.html",
                    "04/19/2018 23:59"),

        };
    }
}
```
###### \java\seedu\progresschecker\storage\TestTasks.java
``` java
/**
 * Contains information of test tasks.
 */
public class TestTasks {
    public static SimplifiedTask[] getTestTasks() {
        return new SimplifiedTask[] {
            new SimplifiedTask("LO[W3.10][Compulsory][Submission]&#9733;",
                "Work with a 1KLoC code base: checkurlhttps://nus-cs2103-ay1718s2.github.io/website/schedule"
                        + "/week3/outcomes.html",
                "02/01/2018 23:59"),
            new SimplifiedTask("LO[W4.1]&#9733;&#9733;&#9733;",
                "Can explain models: checkurlhttps://nus-cs2103-ay1718s2.github.io/website/schedule/week4"
                        + "/outcomes.html",
                "02/08/2018 23:59"),
            new SimplifiedTask("LO[W5.11][Compulsory][Submission]&#9733;",
                "Work with a 2KLoC code base: checkurlhttps://nus-cs2103-ay1718s2.github.io/website/schedule"
                        + "/week5/outcomes.html",
                "02/15/2018 23:59"),
            new SimplifiedTask("LO[W6.5][Submission]&#9733;&#9733;&#9733;",
                "Can use JavaFX to build a simple GUI: checkurlhttps://nus-cs2103-ay1718s2.github.io/website"
                        + "/schedule/week6/outcomes.html",
                "02/22/2018 23:59")
        };
    }

}
```
###### \java\seedu\progresschecker\ui\Browser2Panel.java
``` java
    /**
     * Loads the HTML file which contains task information.
     */
    public void loadBarPage(String content) {
        loadPageViaString(content);
    }

    public void loadPageViaString(String content) {
        Platform.runLater(() -> browser2.getEngine().loadContent(content));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser2 = null;
    }

    @Subscribe
    private void handleLoadBarEvent(LoadBarEvent event)  {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadBarPage(event.getContent());
    }
```
###### \java\seedu\progresschecker\ui\BrowserPanel.java
``` java
    /**
     * Loads the HTML file which contains task information.
     */
    public void loadTaskPage(String content) {
        loadPageViaString(content);
    }

    public void loadPageViaString(String content) {
        Platform.runLater(() -> browser.getEngine().loadContent(content));
    }
```
###### \java\seedu\progresschecker\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleLoadTaskEvent(LoadTaskEvent event)  {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadTaskPage(event.getContent());
    }

    @Subscribe
    private void handleLoadUrlEvent(LoadUrlEvent event)  {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPage(event.getUrl());
    }
```
###### \resources\view\MainWindow.fxml
``` fxml
                 <SplitPane id="taskPane" fx:id="taskPane" dividerPositions="0.25" orientation="VERTICAL" VBox.vgrow="ALWAYS">
                  <items>
                     <StackPane fx:id="browser2Placeholder" maxHeight="-Infinity" minHeight="-Infinity" prefHeight="120.0" prefWidth="200.0" />
                     <StackPane fx:id="browserPlaceholder" prefHeight="120.0" prefWidth="200.0" />
                  </items>
                 </SplitPane>
```
###### \resources\view\sampleTasklist.html
``` html
<body  style="background-color:grey;"><div class="container"  style="background-color:grey;"><h2 style="font-family:verdana; color:white">CS2103 LOs</h2>
    <br>

    <!-- completed task -->
    <div class="panel panel-success">
        <div class="panel-heading">1. LO[W6.5][Submission]&#9733;&#9733;&#9733;</div>
        <div class="panel-body">
            <dd style="font-family:verdana; color:black;">&#9888; &nbsp;2018-02-22</dd>
            <dd style="font-family:verdana; color:darkseagreen;">&#9873; &nbsp;Completed! &#9745;</dd>
            <dd style="font-family:verdana; color:black;">&#9998; &nbsp;Can use JavaFX to build a simple GUI: </dd>
            <p><a href="https://nus-cs2103-ay1718s2.github.io/website/schedule/week6/outcomes.html">https://nus-cs2103-ay1718s2.github.io/website/schedule/week6/outcomes.html</a></p>
        </div>
    </div>

    <!-- pending task -->
    <div class="panel panel-danger">
        <div class="panel-heading">2. LO[W5.11][Compulsory][Submission]&#9733;</div>
        <div class="panel-body">
            <dd style="font-family:verdana; color:black;">&#9888; &nbsp;2018-02-15</dd>
            <dd style="font-family:verdana; color:red;">&#9873; &nbsp;Please work on it :) &#9744;</dd>
            <dd style="font-family:verdana; color:black;">&#9998; &nbsp;Work with a 2KLoC code base: </dd>
            <p><a href="https://nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html">https://nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html</a></p>
        </div>
    </div>
    <div class="panel panel-danger">
        <div class="panel-heading">3. LO[W4.1]&#9733;&#9733;&#9733;</div>
        <div class="panel-body">
            <dd style="font-family:verdana; color:black;">&#9888; &nbsp;2018-02-08</dd>
            <dd style="font-family:verdana; color:red;">&#9873; &nbsp;Please work on it :) &#9744;</dd>
            <dd style="font-family:verdana; color:black;">&#9998; &nbsp;Can explain models: </dd>
            <p><a href="https://nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html">https://nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html</a></p>
        </div>
    </div>
    <div class="panel panel-danger">
        <div class="panel-heading">4. LO[W3.10][Compulsory][Submission]&#9733;</div>
        <div class="panel-body">
            <dd style="font-family:verdana; color:black;">&#9888; &nbsp;2018-02-01</dd>
            <dd style="font-family:verdana; color:red;">&#9873; &nbsp;Please work on it :) &#9744;</dd>
            <dd style="font-family:verdana; color:black;">&#9998; &nbsp;Work with a 1KLoC code base: </dd>
            <p><a href="https://nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html">https://nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html</a></p>
        </div>
    </div>
</div>
</div>

</dl>
<h2 style="font-family:verdana; color:white">You have completed 1/4 !</h2></body>
</html>
```
###### \resources\view\TaskListCard.fxml
``` fxml
<HBox id="taskCardPane" fx:id="taskCardPane" prefHeight="140.0" prefWidth="250.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane prefHeight="140.0" prefWidth="362.0" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <HBox prefHeight="140.0" prefWidth="250.0">
            <children>
                <VBox alignment="CENTER_LEFT" minHeight="-Infinity" prefHeight="140.0" prefWidth="216.0">
                    <padding>
                        <Insets bottom="5" left="15" right="5" top="5" />
                    </padding>
                    <HBox alignment="CENTER_LEFT" spacing="5">
                        <Label fx:id="id" styleClass="cell_big_label">
                            <minWidth>
                                <!-- Ensures that the label text is never truncated -->
                                <Region fx:constant="USE_PREF_SIZE" />
                            </minWidth>
                        </Label>
                        <Label fx:id="title" styleClass="cell_big_label" text="\$title" />
                    </HBox>
                    <FlowPane fx:id="tags" />
                    <GridPane prefHeight="80.0" prefWidth="199.0">
                        <columnConstraints>
                            <ColumnConstraints hgrow="SOMETIMES" maxWidth="89.0" minWidth="10.0" prefWidth="54.0" />
                            <ColumnConstraints hgrow="SOMETIMES" maxWidth="171.0" minWidth="10.0" prefWidth="142.0" />
                        </columnConstraints>
                        <rowConstraints>
                            <RowConstraints maxHeight="21.0" minHeight="0.0" prefHeight="20.0" vgrow="SOMETIMES" />
                            <RowConstraints maxHeight="26.0" minHeight="10.0" prefHeight="20.0" vgrow="SOMETIMES" />
                            <RowConstraints maxHeight="23.0" minHeight="7.0" prefHeight="20.0" vgrow="SOMETIMES" />
                            <RowConstraints maxHeight="23.0" minHeight="10.0" prefHeight="20.0" vgrow="SOMETIMES" />
                        </rowConstraints>
                        <children>
                            <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="PS:" GridPane.rowIndex="3" />
                            <Label fx:id="ps" styleClass="cell_small_label" text="\$ps" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                            <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Status:" GridPane.rowIndex="2" />
                            <Label fx:id="status" styleClass="cell_small_label" text="\$status" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                            <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Start:" />
                            <Label fx:id="start" styleClass="cell_small_label" text="\$start" GridPane.columnIndex="1" />
                            <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Deadline:" GridPane.rowIndex="1" />
                            <Label fx:id="deadline" styleClass="cell_small_label" text="\$deadline" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                        </children>
                    </GridPane>
                </VBox>
            </children>
        </HBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
