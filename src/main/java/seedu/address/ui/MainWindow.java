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
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.WindowSettings;
import seedu.address.commons.events.ui.ActiveListChangedEvent;
import seedu.address.commons.events.ui.BookListSelectionChangedEvent;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.commons.events.ui.ClearMainContentRequestEvent;
import seedu.address.commons.events.ui.DeselectBookRequestEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ReselectBookRequestEvent;
import seedu.address.commons.events.ui.ShowAliasListRequestEvent;
import seedu.address.commons.events.ui.ShowBookReviewsRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowLibraryResultRequestEvent;
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
    private WelcomePanel welcomePanel;
    private BookDetailsPanel bookDetailsPanel;
    private BookReviewsPanel bookReviewsPanel;
    private BookInLibraryPanel bookInLibraryPanel;
    private AliasListPanel aliasListPanel;
    private BookListPanel bookListPanel;
    private HelpWindow helpWindow;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private Scene scene;

    @FXML
    private StackPane mainContentPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane bookListPanelPlaceholder;

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
        updateStylesheet(prefs);

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
        welcomePanel = new WelcomePanel();
        bookDetailsPanel = new BookDetailsPanel();
        bookReviewsPanel = new BookReviewsPanel();
        bookInLibraryPanel = new BookInLibraryPanel();
        aliasListPanel = new AliasListPanel(logic.getDisplayAliasList());
        mainContentPlaceholder.getChildren().add(welcomePanel.getRoot());
        mainContentPlaceholder.getChildren().add(bookDetailsPanel.getRoot());
        mainContentPlaceholder.getChildren().add(bookReviewsPanel.getRoot());
        mainContentPlaceholder.getChildren().add(bookInLibraryPanel.getRoot());
        mainContentPlaceholder.getChildren().add(aliasListPanel.getRoot());
        bookReviewsPanel.hide();
        bookInLibraryPanel.hide();
        bookDetailsPanel.setStyleSheet(prefs.getAppTheme().getCssFile());

        bookListPanel = new BookListPanel(logic.getActiveList());
        bookListPanelPlaceholder.getChildren().add(bookListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getBookShelfFilePath());
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
        primaryStage.setHeight(prefs.getWindowSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getWindowSettings().getWindowWidth());
        if (prefs.getWindowSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getWindowSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getWindowSettings().getWindowCoordinates().getY());
        }
    }

    /** Updates the stylesheet used based on user preferences. */
    private void updateStylesheet(UserPrefs prefs) {
        scene.getStylesheets().setAll(prefs.getAppTheme().getCssFile());
        if (bookDetailsPanel != null) {
            bookDetailsPanel.setStyleSheet(prefs.getAppTheme().getCssFile());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    WindowSettings getCurrentGuiSetting() {
        return new WindowSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp() {
        if (helpWindow == null) {
            helpWindow = new HelpWindow();
        }
        helpWindow.show();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Hides all panels in the main content.
     */
    private void hideMainContent() {
        welcomePanel.hide();
        bookDetailsPanel.hide();
        bookReviewsPanel.hide();
        bookInLibraryPanel.hide();
        aliasListPanel.hide();
    }

    /**
     * Clears book list selection and hides all panels in the main content.
     */
    private void clearSelectionAndHideMainContent() {
        bookListPanel.clearSelection();
        hideMainContent();
    }

    /**
     * Clears book list selection, hides all panels in the main content and shows the welcome panel.
     */
    private void showWelcomePanel() {
        clearSelectionAndHideMainContent();
        welcomePanel.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    @Subscribe
    private void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        prefs.setAppTheme(event.newTheme);
        updateStylesheet(prefs);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleActiveListChangedEvent(ActiveListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showWelcomePanel();
        bookListPanel.setBookList(logic.getActiveList());
        bookListPanel.scrollToTop();
    }

    @Subscribe
    private void handleClearMainContentRequestEvent(ClearMainContentRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showWelcomePanel();
    }

    @Subscribe
    private void handleBookListSelectionChangedEvent(BookListSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        hideMainContent();
        bookDetailsPanel.show();
    }

    @Subscribe
    private void handleShowBookReviewsRequestEvent(ShowBookReviewsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        clearSelectionAndHideMainContent();
        bookReviewsPanel.show();
    }

    @Subscribe
    private void handleShowBookInLibraryRequestEvent(ShowLibraryResultRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        clearSelectionAndHideMainContent();
        bookInLibraryPanel.show();
    }

    @Subscribe
    private void handleShowAliasListRequestEvent(ShowAliasListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        clearSelectionAndHideMainContent();
        aliasListPanel.show();
    }

    @Subscribe
    private void handleDeselectBookRequestEvent(DeselectBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (bookListPanel.deselectBook()) {
            hideMainContent();
            welcomePanel.show();
        }
    }

    @Subscribe
    private void handleReselectBookRequestEvent(ReselectBookRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (bookListPanel.reselectBook()) {
            hideMainContent();
            bookDetailsPanel.show();
        }
    }
}
