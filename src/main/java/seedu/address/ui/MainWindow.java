package seedu.address.ui;

import static seedu.address.ui.ProgressIndicatorProperties.PROGRESS_INDICATOR_COLOR;
import static seedu.address.ui.ProgressIndicatorProperties.PROGRESS_INDICATOR_HEIGHT;
import static seedu.address.ui.ProgressIndicatorProperties.PROGRESS_INDICATOR_LABEL_COLOR;
import static seedu.address.ui.ProgressIndicatorProperties.PROGRESS_INDICATOR_LABEL_NAME;
import static seedu.address.ui.ProgressIndicatorProperties.PROGRESS_INDICATOR_WIDTH;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.WeeklyEvent;

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
    private Calendar calendar;
    private Timetable timetable;
    private PersonListPanel personListPanel;
    private ToDoListPanel toDoListPanel;
    private GroupListPanel groupListPanel;
    private ProgressIndicator progressIndicator;
    private Label progressIndicatorLabel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane calendarPlaceholder;

    @FXML
    private FlowPane progressIndicatorPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane toDoListPanelPlaceholder;

    @FXML
    private StackPane groupListPanelPlaceholder;

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
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
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
        calendar = new Calendar(logic.getFilteredEventList());
        timetable = new Timetable();
        calendarPlaceholder.getChildren().add(calendar.getCalendarView());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        groupListPanel = new GroupListPanel(logic.getFilteredGroupList());
        groupListPanelPlaceholder.getChildren().add(groupListPanel.getRoot());

        //@@author nhatquang3112
        toDoListPanel = new ToDoListPanel(logic.getFilteredToDoList());
        toDoListPanelPlaceholder.getChildren().add(toDoListPanel.getRoot());

        progressIndicatorLabel = new Label(PROGRESS_INDICATOR_LABEL_NAME);
        progressIndicatorLabel.setStyle(PROGRESS_INDICATOR_LABEL_COLOR);
        progressIndicatorPlaceholder.getChildren().add(progressIndicatorLabel);

        progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle(PROGRESS_INDICATOR_COLOR);
        progressIndicator.setPrefSize(PROGRESS_INDICATOR_WIDTH, PROGRESS_INDICATOR_HEIGHT);
        progressIndicator.setProgress(logic.getToDoListCompleteRatio());
        progressIndicatorPlaceholder.getChildren().add(progressIndicator);
        //@@author

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        //@@author jas5469
        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath(),
                logic.getFilteredPersonList().size());
        //@@author
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    //@@author nhatquang3112
    void updateProgressIndicator() {
        progressIndicator.setProgress(logic.getToDoListCompleteRatio());
    }
    //@@author

    //@@author LeonidAgarth
    /**
     * Clears the old calendar and display an updated one
     */
    void redisplayCalendar() {
        calendarPlaceholder.getChildren().clear();
        calendar = new Calendar(logic.getFilteredEventList());
        calendarPlaceholder.getChildren().add(calendar.getCalendarView());
    }

    /**
     * Clears the old timetable and display an updated one
     */
    void redisplayTimetable(ObservableList<WeeklyEvent> modules) {
        calendarPlaceholder.getChildren().clear();
        timetable = new Timetable(modules);
        calendarPlaceholder.getChildren().add(timetable.getTimetableView());
    }

    //@@author
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
        // browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
