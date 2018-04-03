package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExecuteCommandRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.PopulatePrefixesRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static BrowserPanel browserPanel = new BrowserPanel();


    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem undoMenuItem;

    @FXML
    private MenuItem redoMenuItem;

    @FXML
    private MenuItem clearMenuItem;

    @FXML
    private MenuItem historyMenuItem;

    @FXML
    private MenuItem listMenuItem;

    @FXML
    private MenuItem findMenuItem;

    @FXML
    private MenuItem addMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private MenuItem editMenuItem;

    @FXML
    private MenuItem locateMenuItem;

    @FXML
    private MenuItem selectMenuItem;

    @FXML
    private MenuItem helpMenuItem;

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
        setAccelerator(exitMenuItem, KeyCombination.valueOf("Alt + Q"));

        setAccelerator(undoMenuItem, KeyCombination.valueOf("Ctrl + Z"));
        setAccelerator(redoMenuItem, KeyCombination.valueOf("Ctrl + Y"));
        setAccelerator(clearMenuItem, KeyCombination.valueOf("Ctrl + Shift + C"));

        setAccelerator(historyMenuItem, KeyCombination.valueOf("Ctrl + H"));
        setAccelerator(listMenuItem, KeyCombination.valueOf("F2"));
        setAccelerator(findMenuItem, KeyCombination.valueOf("Ctrl + F"));

        setAccelerator(addMenuItem, KeyCombination.valueOf("Ctrl + A"));
        setAccelerator(deleteMenuItem, KeyCombination.valueOf("Ctrl + D"));
        setAccelerator(editMenuItem, KeyCombination.valueOf("Ctrl + E"));
        setAccelerator(locateMenuItem, KeyCombination.valueOf("Ctrl + L"));
        setAccelerator(selectMenuItem, KeyCombination.valueOf("Ctrl + S"));

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

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
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
    private void handleHelp() {
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    /**
     * Executes the {@code undo} operation
     */
    @FXML
    private void handleUndo() {
        raise(new ExecuteCommandRequestEvent("undo"));
    }

    /**
     * Executes the {@code redo} operation
     */
    @FXML
    private void handleRedo() {
        raise(new ExecuteCommandRequestEvent("redo"));
    }

    /**
     * Executes the {@code clear} operation
     */
    @FXML
    private void handleClear() {
        raise(new ExecuteCommandRequestEvent("clear"));
    }

    /**
     * Executes the {@code history} operation
     */
    @FXML
    private void handleHistory() {
        raise(new ExecuteCommandRequestEvent("history"));
    }

    /**
     * Executes the {@code list} operation
     */
    @FXML
    private void handleList() {
        raise(new ExecuteCommandRequestEvent("list"));
    }

    /**
     * Populates the {@code CommandBox} with the {@code FindCommand} prefixes.
     */
    @FXML
    private void handleFind() {
        raise(new PopulatePrefixesRequestEvent("find"));
    }

    /**
     * Populates the {@code CommandBox} with the {@code AddCommand} prefixes.
     */
    @FXML
    private void handleAdd() {
        raise(new PopulatePrefixesRequestEvent("add"));
    }

    /**
     * Populates the {@code CommandBox} with the {@code DeleteCommand} prefixes.
     */
    @FXML
    private void handleDelete() {
        raise(new PopulatePrefixesRequestEvent("delete"));
    }

    /**
     * Populates the {@code CommandBox} with the {@code EditCommand} prefixes.
     */
    @FXML
    private void handleEdit() {
        raise(new PopulatePrefixesRequestEvent("edit"));
    }

    /**
     * Populates the {@code CommandBox} with the {@code LocateCommand} prefixes.
     */
    @FXML
    private void handleLocate() {
        raise(new PopulatePrefixesRequestEvent("locate"));
    }

    /**
     * Populates the {@code CommandBox} with the {@code SelectCommand} prefixes.
     */
    @FXML
    private void handleSelect() {
        raise(new PopulatePrefixesRequestEvent("select"));
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

    public static void loadUrl(String url) {
        browserPanel.loadPage(url);
    }
}
