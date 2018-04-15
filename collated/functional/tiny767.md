# tiny767
###### \java\seedu\address\commons\events\ui\BrowserUrlChangedEvent.java
``` java
/**
 * Indicates that the browser has changed
 */
public class BrowserUrlChangedEvent extends BaseEvent {
    private String newUrl;

    public BrowserUrlChangedEvent(String newUrl) {
        this.newUrl = newUrl;
    }
    public String getNewUrl() {
        return newUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ToggleFacebookPanelEvent.java
``` java
/** Indicates that the Facebook Panel should be toggled  */
public class ToggleFacebookPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\FacebookLoginCommand.java
``` java
/**
 * Connects the address book to a personal Facebook account.
 */
public class FacebookLoginCommand extends Command {
    public static final String COMMAND_WORD = "facebooklogin";
    public static final String COMMAND_ALIAS = "fblogin";
    public static final String MESSAGE_LOGIN_INIT = "Initiating authentication. "
            + "Please log into your Facebook account.";
    public static final String MESSAGE_SUCCESS = "You are logged in to Facebook";
    public static final String MESSAGE_FAILURE = "Error in Facebook authorisation";
    public static final String FACEBOOK_DOMAIN = "https://www.facebook.com/";
    private static final String FACEBOOK_APP_ID = "199997423936335";

    private static final String FACEBOOK_PERMISSIONS = "user_about_me,email,publish_actions,user_birthday,"
            + "user_education_history,user_friends,user_games_activity,user_hometown,user_likes,"
            + "user_location,user_photos,user_posts,user_relationship_details,user_relationships,"
            + "user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,"
            + "ads_management,ads_read,business_management,manage_pages,pages_manage_cta,publish_pages,"
            + "user_actions.books,user_actions.fitness,read_custom_friendlists,"
            + "pages_messaging_phone_number,pages_messaging_subscriptions,pages_show_list,user_actions.music,"
            + "user_actions.news,rsvp_event,user_events,user_managed_groups,"
            + "pages_manage_instant_articles,user_actions.video,instagram_basic,instagram_manage_comments,"
            + "instagram_manage_insights,read_audience_network_insights,read_insights";

    private static final String FACEBOOK_AUTH_URL =
            "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" + FACEBOOK_APP_ID
                    + "&redirect_uri=" + FACEBOOK_DOMAIN + "&scope=" + FACEBOOK_PERMISSIONS;

    private static WebEngine webEngine;

    private static String accessToken;
    private static String authenticatedUsername;
    private static String authenticatedUserId;
    private static boolean isAuthenticated = false;
    private static String authenticatedUserPage;


    private static DefaultFacebookClient fbClient;
    private static User user;

    public static DefaultFacebookClient getFbClient() {
        return fbClient;
    }

    public static String getAuthenticatedUsername() {
        return authenticatedUsername;
    }

    public static WebEngine getWebEngine() {
        return webEngine;
    }

    public static String getAuthenticatedUserPage() {
        return authenticatedUserPage;
    }

    public static boolean getAuthenticateState() {
        return isAuthenticated;
    }


    /**
     * Sets a WebEngine
     */
    public static void setWebEngine(WebEngine webEngine) {
        FacebookLoginCommand.webEngine = webEngine;
    }

    /**
     * Completes the authentication process by retrieving the token and setting up username and id
     */
    public static void completeAuth(String urlWithToken) throws CommandException {

        accessToken = urlWithToken.replaceAll(".*#access_token=(.+)&.*", "$1");

        fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
        user = fbClient.fetchObject("me", User.class);

        try {
            authenticatedUsername = user.getName();
            authenticatedUserId = user.getId();
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (accessToken != null) {
            isAuthenticated = true;
            authenticatedUserPage = FACEBOOK_DOMAIN + authenticatedUserId;
            EventsCenter.getInstance().post(new NewResultAvailableEvent(
                    MESSAGE_SUCCESS + "\n" + "User name: " + authenticatedUsername));
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new ToggleFacebookPanelEvent());

        try {
            Platform.runLater(() -> webEngine.load(FACEBOOK_AUTH_URL));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_LOGIN_INIT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookLoginCommand); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\logic\commands\FacebookPostCommand.java
``` java
/**
 * Posts a message to a personal Facebook account.
 */
public class FacebookPostCommand extends Command {
    public static final String COMMAND_WORD = "facebookpost";
    public static final String COMMAND_ALIAS = "fbpost";
    public static final String EXAMPLE_POST = "Good morning!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": make a post to Facebook wall \n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: MESSAGE\n"
            + "Example: " + COMMAND_WORD + " " + EXAMPLE_POST;

    public static final String MESSAGE_FACEBOOK_POST_SUCCESS = "Attempted to post to Facebook";
    public static final String MESSAGE_FACEBOOK_POST_LOGIN = "User has not been authenticated" + "\n"
            + "Please log in and then try posting again!";
    public static final String MESSAGE_FACEBOOK_POST_FAIL = "There was an error posting to Facebook";

    private static WebEngine webEngine;

    private String toPost;

    /**
     * Creates an FacebookPostCommand based on the message
     */
    public FacebookPostCommand(String message) {
        requireNonNull(message);
        toPost = message;
    }

    /**
     * Completes the post command
     */
    public void completePost() throws CommandException {

        DefaultFacebookClient fbClient = FacebookLoginCommand.getFbClient();
        try {
            fbClient.publish("me/feed", FacebookType.class, Parameter.with("message", toPost));
        } catch (Exception e) {
            e.printStackTrace();
            new CommandException(MESSAGE_FACEBOOK_POST_FAIL);
        }

        EventsCenter.getInstance().post(new ToggleFacebookPanelEvent());

        webEngine = FacebookLoginCommand.getWebEngine();
        Platform.runLater(() -> webEngine.load(FacebookLoginCommand.getAuthenticatedUserPage()));
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!FacebookLoginCommand.getAuthenticateState()) {
            FacebookLoginCommand fbLoginCommand = new FacebookLoginCommand();
            fbLoginCommand.execute();
            return new CommandResult(MESSAGE_FACEBOOK_POST_LOGIN);
        } else {
            completePost();
            return new CommandResult(MESSAGE_FACEBOOK_POST_SUCCESS
                +    "\nUsername: " + FacebookLoginCommand.getAuthenticatedUsername()
                +    "\nmessage: '" + toPost + "'");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookPostCommand // instanceof handles nulls
                && toPost.equals(((FacebookPostCommand) other).toPost));
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
//Solution below adapted from https://github.com/se-edu/addressbook-level4/pull/599
/**
 * Modifies the remark of a person in the address book
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit the remark of a person. "
            + "Parameters: "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Is a very good coder.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";

    private final Index index;
    private final Remark remark;

    private Person personToEdit;
    private Person editedPerson;



    /**
     * Creates a remark command to edit the remark of the specified person based on the index
     * @param index index of the person in the list to modify the remark
     * @param remark content of the remark to be updated
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;

    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(createSuccessMessage(editedPerson));
    }

    /**
     *Generate an execution success message based on whether the remark is adding to or removing from
     */
    private String createSuccessMessage(Person editedPerson) {
        String message = remark.value.isEmpty() ? MESSAGE_DELETE_REMARK_SUCCESS : MESSAGE_ADD_REMARK_SUCCESS;
        return String.format(message, editedPerson);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = new Person(personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                this.remark,
                personToEdit.getLink(),
                personToEdit.getSkills(),
                personToEdit.getTags());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }

}
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Change the theme of addressbook
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";
    public static final String LIGHT_THEME = "light";
    public static final String DARK_THEME = "dark";
    public static final String MORNING_THEME = "morning";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes theme to. "
            + "Parameters: morning/light/dark";


    public static final String MESSAGE_SUCCESS = "Theme has been changed successfully";

    private final String theme;

    /**
     * Creates a ThemeCommand to change the theme to the specified {@code theme}
     */
    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute() {
        if (theme.equalsIgnoreCase(ThemeCommand.LIGHT_THEME)) {
            UiTheme.getInstance().setToLightTheme();
        } else if (theme.equalsIgnoreCase(ThemeCommand.DARK_THEME)) {
            UiTheme.getInstance().setToDarkTheme();
        } else if (theme.equalsIgnoreCase(ThemeCommand.MORNING_THEME)) {
            UiTheme.getInstance().setToMorningTheme();
        }

        return new CommandResult(MESSAGE_SUCCESS);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ThemeCommand)) {
            return false;
        }

        // state check
        ThemeCommand t = (ThemeCommand) other;
        return theme.equals(t.theme);
    }
}
```
###### \java\seedu\address\logic\parser\FacebookPostCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FacebookPostCommand object
 */
public class FacebookPostCommandParser implements Parser<FacebookPostCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FacebookAddCommand
     * and returns an FacebookAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FacebookPostCommand parse(String input) throws ParseException {
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacebookPostCommand.MESSAGE_USAGE));
        }
        return new FacebookPostCommand(trimmedInput);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and create a new {@code RemarkCommand} object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");
        return new RemarkCommand(index, new Remark(remark));

    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns a ThemeCommand object for execution.
         * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String trimmedArgs = args.trim();
            if (!trimmedArgs.equalsIgnoreCase(ThemeCommand.LIGHT_THEME)
                && !trimmedArgs.equalsIgnoreCase(ThemeCommand.DARK_THEME)
                && !trimmedArgs.equalsIgnoreCase(ThemeCommand.MORNING_THEME)) {
                throw new IllegalValueException("");

            } else {
                return new ThemeCommand(trimmedArgs);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ThemeCommand.MESSAGE_USAGE));
        }

    }

}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remark can take any values, and it can be blank";

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid address.
     */
    public Remark(String remark) {
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\ui\FacebookPanel.java
``` java
/**
 * The Facebook Panel of the App, used for facebook authentication and displaying profile
 */
public class FacebookPanel extends UiPart<Region> {

    private static final String FXML = "FacebookPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    private Label location;


    public FacebookPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        facebookInit();

        registerAsAnEventHandler(this);
    }
    /**
     * Sets the browser up to for Facebook functionality
     */
    private void facebookInit() {
        FacebookLoginCommand.setWebEngine(browser.getEngine());
        location = new Label();
        location.textProperty().bind(browser.getEngine().locationProperty());
        setEventHandlerForBrowserUrlChangedEvent();
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    private void setEventHandlerForBrowserUrlChangedEvent() {
        location.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("access_token")) {
                logger.info("facebook panel url changed to : '" + newValue + "'");
                raise(new BrowserUrlChangedEvent(newValue));

            }
        });
    }
    @Subscribe
    private void handleBrowserUrlChangedEvent(BrowserUrlChangedEvent event) throws CommandException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        try {
            FacebookLoginCommand.completeAuth(event.getNewUrl());
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        Scene scene = primaryStage.getScene();
        UiTheme.getInstance().setScene(scene);
        primaryStage.setScene(scene);
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Replace the current panel by Facebook panel
     */
    void switchToFacebookPanel() {
        facebookPanel = new FacebookPanel();
        browserOrReportPlaceholder.getChildren().clear();
        browserOrReportPlaceholder.getChildren().add(facebookPanel.getRoot());
        isReportPanelOpen = false;
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Sets up a person's tags
     * @param person
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label label = new Label(tag.tagName);
            label.getStyleClass().add(getTagColorFor(tag.tagName));
            tags.getChildren().add(label);
        });
    }

    /**
     * Gets the color for a tag based on its name
     * @param tagName
     * @return
     */
    private String getTagColorFor(String tagName) {
        return TAG_COLORS[Math.abs(tagName.hashCode()) % TAG_COLORS.length];
    }
```
###### \java\seedu\address\ui\UiTheme.java
``` java
/**
 * stores the styles that can be used for the interface
 */
public class UiTheme {
    private static final String LIGHT_THEME_STYLE = "view/LightTheme.css";
    private static final String DARK_THEME_STYLE = "view/DarkTheme.css";
    private static final String MORNING_THEME_STYLE = "view/MorningTheme.css";
    private static Scene scene = null;
    private static UiTheme instance = null;

    public static void setScene(Scene s) {
        scene = s;
        setDefaultTheme();
    }

    private static void setDefaultTheme() {
        setToMorningTheme();
    }

    public static void setToLightTheme() {
        scene.getStylesheets().setAll(LIGHT_THEME_STYLE);
    }

    public static void setToDarkTheme() {
        scene.getStylesheets().setAll(DARK_THEME_STYLE);
    }

    public static void setToMorningTheme() {
        scene.getStylesheets().setAll(MORNING_THEME_STYLE);
    }

    public static UiTheme getInstance() {
        if (instance == null) {
            instance = new UiTheme();
        }

        return instance;
    }
}
```
###### \resources\view\LightTheme.css
``` css
.background {
    -fx-background-color: derive(#c1bfbf, 20%);
    background-color: #383838; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #c1bfbf;
    -fx-control-inner-background: #c1bfbf;
    -fx-background-color: #c1bfbf;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#c1bfbf, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#c1bfbf, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#c1bfbf, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
    -fx-background-color: #c1bfbf;
}

.list-cell:fille:even {
    -fx-background-color: #c1bfbf;
}

.list-cell:filled:odd {
    -fx-background-color: #ced7db;
}

.list-cell:filled:selected {
    -fx-background-color: #424d5f;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#c1bfbf, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#c1bfbf, 20%);
     -fx-border-color: derive(#c1bfbf, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#c1bfbf, 20%);
    -fx-text-fill: white;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: white !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#c1bfbf, 30%);
    -fx-border-color: derive(#c1bfbf, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#c1bfbf, 30%);
    -fx-border-color: derive(#c1bfbf, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#c1bfbf, 30%);
}

.context-menu {
    -fx-background-color: derive(#c1bfbf, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#c1bfbf, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #c1bfbf;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: black;
  -fx-text-fill: #c1bfbf;
}

.button:focused {
    -fx-border-color: black, black;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #c1bfbf;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #c1bfbf;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #c1bfbf;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#c1bfbf, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: black;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#c1bfbf, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#c1bfbf, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, white, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #c1bfbf, transparent, #c1bfbf;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#tags .red{
    -fx-text-fill: white;
    -fx-background-color: red;
}

#tags .blue{
    -fx-text-fill: black;
    -fx-background-color: blue;
}

#tags .pink{
    -fx-text-fill: white;
    -fx-background-color: pink;
}

```
