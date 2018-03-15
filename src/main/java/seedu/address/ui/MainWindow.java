package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowCalendarRequestEvent;
import seedu.address.commons.events.ui.ShowErrorsRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.SwitchFeatureEvent;
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
    private PersonDetailsPanel personDetailsPanel;
    private CalendarPanel calendarPanel;
    private DailySchedulerPanel dailySchedulerPanel;
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    //@@author jaronchan
    @FXML
    private StackPane personDetailsPlaceholder;

    @FXML
    private StackPane calendarPlaceholder;

    @FXML
    private StackPane dailySchedulerPlaceholder;

    @FXML
    private TabPane featuresTabPane;

    @FXML
    private Tab detailsTab;

    @FXML
    private Tab calendarTab;

    @FXML
    private Tab dailySchedulerTab;

    //@@author

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem viewCalendarMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

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

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(viewCalendarMenuItem, KeyCombination.valueOf("F8"));
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
        //@@author jaronchan
        personDetailsPanel = new PersonDetailsPanel();
        calendarPanel = new CalendarPanel();
        dailySchedulerPanel = new DailySchedulerPanel();

        personDetailsPlaceholder.getChildren().add(personDetailsPanel.getRoot());
        calendarPlaceholder.getChildren().add(calendarPanel.getRoot());
        dailySchedulerPlaceholder.getChildren().add(dailySchedulerPanel.getRoot());
        //@@author

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBox.setMainWindow(this);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        hideBeforeLogin();

    }

    void hide() {
        primaryStage.hide();
    }

    /** @@author {kaisertanqr}
     *
     * Hides browser and person list panel.
     */
    void hideBeforeLogin() {
        featuresTabPane.setVisible(false);
        personDetailsPlaceholder.setVisible(false);
        calendarPlaceholder.setVisible(false);
        dailySchedulerPlaceholder.setVisible(false);
        personListPanelPlaceholder.setVisible(false);
    }

    /** @@author {kaisertanqr}
     *
     * Unhide browser and person list panel.
     */
    void showAfterLogin() {
        featuresTabPane.setVisible(true);
        personDetailsPlaceholder.setVisible(true);
        calendarPlaceholder.setVisible(true);
        dailySchedulerPlaceholder.setVisible(true);
        personListPanelPlaceholder.setVisible(true);
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

    //@@author {ifalluphill}
    /**
     * Opens the error window.
     */
    @FXML
    public void handleViewErrors() {
        ErrorsWindow errorsWindow = new ErrorsWindow();
        errorsWindow.show();
    }

    /**
     * Opens the calendar window.
     */
    @FXML
    public void handleViewCalendar() {
        CalendarWindow calendarWindow = new CalendarWindow();
        calendarWindow.show();
    }
    //@@author

    void show() {
        primaryStage.show();
    }

    //@@author jaronchan

    /**
     * Handle event of feature tab switching.
     */
    @Subscribe
    private void handleFeatureSwitch(SwitchFeatureEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getFeatureTarget()) {
        case "details":
            featuresTabPane.getSelectionModel().select(detailsTab);
            break;

        case "calendar":
            featuresTabPane.getSelectionModel().select(calendarTab);
            break;

        case "scheduler":
            featuresTabPane.getSelectionModel().select(dailySchedulerTab);
            break;

        default:
            break;
        }
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
        personDetailsPanel.freeResources();
        calendarPanel.freeResources();
        dailySchedulerPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author {ifalluphill}
    @Subscribe
    private void handleShowErrorsEvent(ShowErrorsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleViewErrors();
    }

    @Subscribe
    private void handleViewCalendarEvent(ShowCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleViewCalendar();
    }
    //@@author
}
