# EdwardKSG
###### \java\seedu\progresschecker\logic\apisetup\ConnectTasksApi.java
``` java
/**
 * Sets up Google Tasks API.
 * i. Authorizes data access based on client credentials.
 * ii. Builds service (initializes API).
 */
public class ConnectTasksApi {
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
        ).build();
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new task list. "
            + "Parameters: "
            + "LISTNAME "
            + "Example: " + COMMAND_WORD + " "
            + "CS2103 LOs";

    public static final String MESSAGE_SUCCESS = "New task list added: %1$s";
    public static final String DEFAULT_LIST_TITLE = "CS2103 LOs";
    public static final String FIRST_LIST_TITLE = "My List";
    public static final String DEFAULT_LIST_ID = "@default";
    public static final String CREATE_FAILURE = "Failed to create task list: ";

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
            setTaskListTitle(DEFAULT_LIST_ID, listTitle);
            createTaskList(FIRST_LIST_TITLE);
            copyTaskList(FIRST_LIST_TITLE, DEFAULT_LIST_ID);
            clearTaskList(DEFAULT_LIST_ID);

            InputStream in =
                    AddDefaultTasksCommand.class.getResourceAsStream(SOURCE_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                String title = line;

                if (title.equals((""))) {
                    break;
                } else {
                    String notes = reader.readLine();
                    String due = reader.readLine();
                    createTask(title, DEFAULT_LIST_ID, notes, due);
                }
            }

            reader.close();
            in.close();

            return new CommandResult(String.format(MESSAGE_SUCCESS, listTitle));
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
    public static final String FILE_FAILURE = "Something is wrong with the file system.";
    public static final String COMMAND_FORMAT = COMMAND_WORD + "TASKLIST-TITLE";
    public static final String MESSAGE_TITLE_CONSTRAINTS = "The title of a task list should not exceed "
            + "49 characters (as specified by Google Task.";
    public static final int MAX_TITLE_LENGTH = 49;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            // TODO: change description and parameter range when appropriate
            + ": Toggle view to display the task list with the given name.\n"
            + "Parameters: TASKLIST-TITLE (max "
            + MAX_TITLE_LENGTH
            + " characters)\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Viewing task list: %1$s";

    @Override
    public CommandResult execute() throws CommandException {
        updateView();
        return new CommandResult(String.format(MESSAGE_SUCCESS, DEFAULT_LIST_TITLE));
    }

    /**
     * Updates the HTML file and refresh the browser panel
     * @throws CommandException
     */
    public void updateView() throws CommandException {
        List<Task> list = MyTaskList.searchTaskListById(DEFAULT_LIST_ID);
        File htmlFile = new File(DATA_FOLDER + TASK_PAGE);
        writeToHtml(list, htmlFile);
        try {
            EventsCenter.getInstance().post(new LoadTaskEvent(readFile(htmlFile.getAbsolutePath(),
                    StandardCharsets.UTF_8)));
        } catch (IOException ioe) {
            throw new CommandException(FILE_FAILURE);
        }
    }


    /**
     * Writes the loaded task list to an html file.Loads the tasks.
     *
     * @param list task list serialized in a java List.
     * @param file File object of the html file.
     */
    void writeToHtml(List<Task> list, File file) throws CommandException {
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

            out.print("<!DOCTYPE html>\n" + "<html>\n"
                    + "<body style=\"background-color:grey;\">\n");
            out.print("<h1 style=\"font-family:verdana; color:white\">&#9764;"
                    + DEFAULT_LIST_TITLE + "&#9764;</h1>\n" + "<hr />\n" + "<dl>\n");

            for (int i = 0; i < size; i++) {
                Task task = list.get(i);
                out.print("    <dt style=\"font-family:verdana; color:antiquewhite;\">"
                        + (i + 1) + ". " + task.getTitle() + "</dt>\n");
                out.print("    <dd style=\"font-family:verdana; color:white;\">&#9888; &nbsp;"
                        + task.getDue().toString().substring(0, 10) + "</dd>\n");
                String status = task.getStatus();
                if (status.length() >= 11) {
                    out.print("    <dd style=\"font-family:verdana; color:red;\">&#9873; &nbsp;"
                            + "Please work on it! " + "&#9744;</dd>\n");
                    countIncomp++;
                } else {
                    out.print("    <dd style=\"font-family:verdana; color:darkseagreen;\">&#9873; &nbsp;"
                            + "Completed! " + "&#9745;</dd>\n");
                    countCompleted++;
                }

                out.print("    <dd style=\"font-family:verdana; color:white;\">&#9998; &nbsp;"
                        + task.getNotes() + "</dd>\n");
                out.print("    <hr />\n");

            }

            double percent = countCompleted / (countCompleted + countIncomp);
            String progress = (int) (percent * 100) + "%";

            out.print("</dl>\n");

            out.print("<h2 style=\"font-family:verdana; color:white\">" + "You have completed " + progress
                    + " !" + "</h2>");

            out.print("</body>\n" + "</html>\n");

            out.close();


        } catch (IOException e) {
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteTaskCommand.MESSAGE_USAGE));
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
        if (title.length() > MAX_TITLE_LENGTH) {
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetTaskCommand.MESSAGE_USAGE));
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
            String title = ParserUtil.parseTaskTitle(args);
            return new ViewTaskListCommand(); // title);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTaskListCommand.MESSAGE_USAGE));
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
###### \java\seedu\progresschecker\model\task\MyTask.java
``` java
/**
 * Include customized methods (based on Google Tasks API) to manipulate tasks.
 */
public class MyTask {

    public static final String AUTHORIZE_FAILURE = "Failed to authorize tasks api client credentials";
    public static final String LOAD_FAILURE = "Failed to load this task list";
    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm";
    public static final String COMPLETED = "completed";
    public static final String NEEDS_ACTION = "needsAction";


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
     * Creates a task with title {@code String} to the tasklist with title {@code String}
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
     * Marks the task with title {@code String} in the tasklist with ID {@code String} as completed
     *
     * @param index title of the task we look for
     * @param listId the identifier of the list to which the task belongs
     * @return title the title of the task with index {@code int}
     */
    public static String completeTask(int index, String listId) throws CommandException {
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
            Task task = list.get(index - 1);

            task.setStatus(COMPLETED);
            task = service.tasks().update(
                    listId,
                    task.getId(),
                    task
            ).execute();

            return task.getTitle();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }

    /**
     * Marks the task with title {@code String} in the tasklist with ID {@code String} as incompleted
     *
     * @param index title of the task we look for
     * @param listId the identifier of the list to which the task belongs
     * @return title the title of the task with index {@code int}
     */
    public static String undoTask(int index, String listId) throws CommandException {
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
            Task task = list.get(index - 1);

            task.setCompleted(null);
            task.setStatus(NEEDS_ACTION);
            task = service.tasks().update(
                    listId,
                    task.getId(),
                    task
            ).execute();

            return task.getTitle();

        } catch (IOException ioe) {
            throw new CommandException(LOAD_FAILURE);
        }
    }
}
```
###### \java\seedu\progresschecker\model\task\MyTaskList.java
``` java
/**
 * Include customized methods (based on Google Tasks API) to manipulate task lists.
 */
public class MyTaskList {

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
```
###### \resources\view\sampleTasklist.html
``` html
<!DOCTYPE html>
<html>
<body style="background-color:grey;">
<h1 style="font-family:verdana; color:white">CS2103 LOs</h1>
<hr />
<dl>
    <dt style="font-family:verdana; color:antiquewhite;">1. LO[W6.5][Submission]тн?тн?тн?</dt>
    <dd style="font-family:verdana; color:white;">Due: &nbsp;&nbsp;&nbsp;2018-02-22</dd>
    <dd style="font-family:verdana; color:red;">Status:   needsAction</dd>
    <dd style="font-family:verdana; color:white;">Notes: &nbsp;&nbsp;Can use JavaFX to build a simple GUI: https://nus-cs2103-ay1718s2.github.io/website/schedule/week6/outcomes.html</dd>
    <hr />
    <dt style="font-family:verdana; color:antiquewhite;">2. LO[W5.11][Compulsory][Submission]тн?</dt>
    <dd style="font-family:verdana; color:white;">Due: &nbsp;&nbsp;&nbsp;2018-02-15</dd>
    <dd style="font-family:verdana; color:red;">Status:   needsAction</dd>
    <dd style="font-family:verdana; color:white;">Notes: &nbsp;&nbsp;Work with a 2KLoC code base: https://nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html</dd>
    <hr />
    <dt style="font-family:verdana; color:antiquewhite;">3. LO[W4.1]тн?тн?тн?</dt>
    <dd style="font-family:verdana; color:white;">Due: &nbsp;&nbsp;&nbsp;2018-02-08</dd>
    <dd style="font-family:verdana; color:red;">Status:   needsAction</dd>
    <dd style="font-family:verdana; color:white;">Notes: &nbsp;&nbsp;Can explain models: https://nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html</dd>
    <hr />
    <dt style="font-family:verdana; color:antiquewhite;">4. LO[W3.10][Compulsory][Submission]тн?</dt>
    <dd style="font-family:verdana; color:white;">Due: &nbsp;&nbsp;&nbsp;2018-02-01</dd>
    <dd style="font-family:verdana; color:red;">Status:   needsAction</dd>
    <dd style="font-family:verdana; color:white;">Notes: &nbsp;&nbsp;Work with a 1KLoC code base: https://nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html</dd>
    <hr />
</dl>
</body>
</html>
```
###### \resources\view\TaskListCard.fxml
``` fxml
<HBox id="taskCardPane" fx:id="taskCardPane" prefHeight="140.0" prefWidth="250.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
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
