package seedu.progresschecker.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.progresschecker.commons.core.Config;
import seedu.progresschecker.commons.core.GuiSettings;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.ui.ChangeThemeEvent;
import seedu.progresschecker.commons.events.ui.ExitAppRequestEvent;
import seedu.progresschecker.commons.events.ui.ShowHelpRequestEvent;
import seedu.progresschecker.commons.events.ui.TabLoadChangedEvent;
import seedu.progresschecker.commons.util.AppUtil;
import seedu.progresschecker.logic.Logic;
import seedu.progresschecker.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String FXML = "MainWindow.fxml";
    private static final String ICON = "/images/progress_checker_32.png";
    private static final String DARK_THEME = "view/DarkTheme.css";
    private static final String DAY_THEME = "view/DayTheme.css";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private Browser2Panel browser2Panel;
    private ExerciseListPanel exerciseListPanel;
    private IssueListPanel issueListPanel;
    private PersonListPanel personListPanel;
    private ProfilePanel profilePanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane browser2Placeholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane exerciseListPanelPlaceholder;

    @FXML
    private StackPane profilePanelPlaceholder;

    @FXML
    private StackPane issuePanelPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane tabPlaceholder;

    @FXML
    private Tab profilePlaceholder;

    @FXML
    private Tab taskPlaceholder;

    @FXML
    private Tab exercisePlaceholder;

    @FXML
    private Tab issuePlaceholder;


    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        handleDayTheme();

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        browser2Panel = new Browser2Panel();
        browser2Placeholder.getChildren().add(browser2Panel.getRoot());

        profilePanel = new ProfilePanel();
        profilePanelPlaceholder.getChildren().add(profilePanel.getRoot());

        exerciseListPanel = new ExerciseListPanel(logic.getFilteredExerciseList());
        exerciseListPanelPlaceholder.getChildren().add(exerciseListPanel.getRoot());

        issueListPanel = new IssueListPanel(logic.getFilteredIssueList());
        issuePanelPlaceholder.getChildren().add(issueListPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getProgressCheckerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    //@@author: Livian1107
    /**
     * Switches to the Night Theme.
     */
    @FXML
    public void handleNightTheme() {
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().setAll(DARK_THEME);
        primaryStage.setScene(scene);
        show();
    }

    /**
     * Switches to the Day Theme.
     */
    @FXML
    public void handleDayTheme() {
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().setAll(DAY_THEME);
        primaryStage.setScene(scene);
        show();
    }
    //@@author

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
        browser2Panel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author Livian1107
    /**
     * Sets the icon of Main Window
     * @param icon with given path
     */
    private void setIcon(String icon) {
        primaryStage.getIcons().setAll(AppUtil.getImage(icon));
    }

    /**
     * Sets the minimum size of the main window
     */
    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    @Subscribe
    private  void handleChangeThemeEvent(ChangeThemeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getTheme()) {
        case "day":
            handleDayTheme();
            break;
        case "night":
            handleNightTheme();
            break;
        default:
            handleDayTheme();
        }
    }
    //@@author

    //@@author iNekox3
    @Subscribe
    private void handleTabLoadChangedEvent(TabLoadChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        SingleSelectionModel<Tab> selectionModel = tabPlaceholder.getSelectionModel();
        switch (event.getTabName()) {
        case "profile":
            selectionModel.select(profilePlaceholder);
            break;
        case "task":
            selectionModel.select(taskPlaceholder);
            break;
        case "exercise":
            selectionModel.select(exercisePlaceholder);
            break;
        case "issues":
            selectionModel.select(issuePlaceholder);
            break;
        default:
            selectionModel.select(selectionModel.getSelectedItem());
        }
    }
    //@@author
}
