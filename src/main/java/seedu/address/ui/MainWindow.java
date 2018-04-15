package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.RefreshReportPanelEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ToggleBrowserPanelEvent;
import seedu.address.commons.events.ui.ToggleFacebookPanelEvent;
import seedu.address.commons.events.ui.ToggleReportPanelEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private FacebookPanel facebookPanel;
    private ReportPanel reportPanel;
    private InterviewListPanel interviewListPanel;
    private PersonListPanel personListPanel;
    private JobListPanel jobListPanel;
    private Config config;
    private UserPrefs prefs;
    private Boolean isReportPanelOpen;

    @FXML
    private StackPane browserOrReportPlaceholder;

    @FXML
    private StackPane reportPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane jobListPanelPlaceholder;

    @FXML
    private StackPane interviewListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        //@@author tiny767
        Scene scene = primaryStage.getScene();
        UiTheme.getInstance().setScene(scene);
        primaryStage.setScene(scene);
        //@@author

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
        switchToBrowserPanel();
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        jobListPanel = new JobListPanel(logic.getFilteredJobList());
        jobListPanelPlaceholder.getChildren().add(jobListPanel.getRoot());

        interviewListPanel = new InterviewListPanel(logic.getFilteredInterviewList());
        interviewListPanelPlaceholder.getChildren().add(interviewListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }
    // @@author anh2111
    /**
     * Replace the current panel by Report panel
     */
    void switchToReportPanel() {
        reportPanel = new ReportPanel(logic.getReport(), logic.getReportHistory());
        browserOrReportPlaceholder.getChildren().clear();
        browserOrReportPlaceholder.getChildren().add(reportPanel.getRoot());
        isReportPanelOpen = true;
    }

    /**
     * Replace the current panel by Browser panel
     */
    void switchToBrowserPanel() {
        browserPanel = new BrowserPanel();
        browserOrReportPlaceholder.getChildren().clear();
        browserOrReportPlaceholder.getChildren().add(browserPanel.getRoot());
        isReportPanelOpen = false;
    }

    // @@author
    //@@author tiny767
    /**
     * Replace the current panel by Facebook panel
     */
    void switchToFacebookPanel() {
        facebookPanel = new FacebookPanel();
        browserOrReportPlaceholder.getChildren().clear();
        browserOrReportPlaceholder.getChildren().add(facebookPanel.getRoot());
        isReportPanelOpen = false;
    }
    //@@author

    @Subscribe
    private void handleRefreshReportPanel(RefreshReportPanelEvent event) {
        if (isReportPanelOpen) {
            switchToReportPanel();
        }
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
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
    // @@author anh2111
    @Subscribe
    private void handleToggleBrowserPanelEvent(ToggleBrowserPanelEvent event) {
        switchToBrowserPanel();
    }

    @Subscribe
    private void handleToggleReportPanelEvent(ToggleReportPanelEvent event) {
        switchToReportPanel();
    }
    // @@author

    @Subscribe
    private void handleToggleFacebookPanelEvent(ToggleFacebookPanelEvent event) {
        switchToFacebookPanel();
    }
}
