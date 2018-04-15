package seedu.address.ui;

import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.LoadingEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowNotifManRequestEvent;
import seedu.address.commons.events.ui.ShowNotificationRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Stage secondaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private ChartsPanel chartsPanel;
    private CoinListPanel coinListPanel;
    private Config config;
    private UserPrefs prefs;

    private NotificationsWindow notificationsWindow;

    @FXML
    private StackPane chartsPlaceholder;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane coinListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox loadingAnimation;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        secondaryStage = new Stage();

        // Configure the UI
        setTitle(config.getAppTitle());
        setLoadingAnimation();
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

        chartsPanel = new ChartsPanel();
        chartsPlaceholder.getChildren().add(chartsPanel.getRoot());

        coinListPanel = new CoinListPanel(logic.getFilteredCoinList());
        coinListPanelPlaceholder.getChildren().add(coinListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFilteredCoinList().size(),
                prefs.getCoinBookFilePath());
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

    //@@author laichengyu
    private void setLoadingAnimation() {
        ProgressIndicator pi = new ProgressIndicator();
        loadingAnimation = new VBox(pi);
        loadingAnimation.setAlignment(Pos.CENTER);
    }
    //@@author

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
        BrowserWindow helpWindow = new BrowserWindow(BrowserWindow.USERGUIDE_FILE_PATH);
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

    public CoinListPanel getCoinListPanel() {
        return this.coinListPanel;
    }

    void releaseResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    //@@author ewaldhew
    @Subscribe
    private void handleShowNotifManEvent(ShowNotifManRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        notificationsWindow = new NotificationsWindow(secondaryStage, event.data);
        notificationsWindow.show();
    }

    @Subscribe
    private void handleShowNotificationEvent(ShowNotificationRequestEvent nre) {
        logger.info(LogsCenter.getEventHandlingLogMessage(nre));
        spawnNotification(nre.toString(), nre.targetIndex, nre.codeString);
    }

    /**
     * Spawns a popup notification with the given message.
     */
    private void spawnNotification(String message, Index index, String code) {
        Notifications.create()
                     .title("The following rule has triggered this notification:")
                     .text(String.format("%1$s\nClick to jump to view %2$s", message, code))
                     .graphic(new ImageView(IconUtil.getCoinIcon(code)))
                     .onAction(event -> {
                         try {
                             logic.execute(ListCommand.COMMAND_WORD);
                             EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
                             event.consume();
                         } catch (Exception e) {
                             throw new RuntimeException();
                         }
                     })
                     .show();
    }
    //@@author



    //@@author laichengyu
    /**
     * Displays loading animation when isLoading is true and hides it otherwise
     * @param isLoading loading state of the application
     */
    @FXML
    private void handleLoading(boolean isLoading) {
        toggleLoadingAnimation(isLoading);
    }

    /**
     * Adds or remove the loading animation from {@code coinListPanelPlaceholder}
     * depending on the loading status
     * @param isLoading the loading status of the application
     */
    private void toggleLoadingAnimation(boolean isLoading) {
        Platform.runLater(() -> {
            if (isLoading) {
                activateLoadingAnimation();
            } else {
                deactivateLoadingAnimation();
            }
        });
    }

    private void activateLoadingAnimation() {
        loadingAnimation.setVisible(true);
        coinListPanelPlaceholder.getChildren().add(loadingAnimation);
        setTitle("Syncing...");
    }

    private void deactivateLoadingAnimation() {
        loadingAnimation.setVisible(false);
        coinListPanelPlaceholder.getChildren().remove(loadingAnimation);
        setTitle(config.getAppTitle());
    }

    @Subscribe
    private void handleLoadingEvent(LoadingEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleLoading(event.isLoading);
    }
    //@@author
}
