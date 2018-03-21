package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.MaximizeAppRequestEvent;
import seedu.address.commons.events.ui.MinimizeAppRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final double MIN_WINDOW_WIDTH = 800;
    public static final double MIN_WINDOW_HEIGHT = 600;
    public static final int WINDOW_CORNER_SIZE = 8;
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;
    private UiResizer uiResizer;
    private Config config;
    private UserPrefs prefs;

    // Independent UI parts residing in this UI container
    private PersonListPanel personListPanel;

    // X and Y offset of the window (Use for draggable title bar
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private AnchorPane topPane;


    @FXML
    private AnchorPane infoPanePlaceholder;

    @FXML
    private AnchorPane topCommandPlaceholder;

    @FXML
    private AnchorPane listPersonsPlaceholder;

    @FXML
    private AnchorPane centerPanePlaceholder;

    @FXML
    private AnchorPane topTitlePlaceholder;

    // Responsive
    @FXML
    private SplitPane bottomPaneSplit;

    @FXML
    private AnchorPane bottomListPane;

    @FXML
    private AnchorPane bottomInfoPane;


    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Setup custom UI
        setBorderlessWindow();
        setDraggableTitleBar();
        setDoubleClickMaximize();
        this.uiResizer = new UiResizer(primaryStage, prefs.getGuiSettings(),
                MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT, WINDOW_CORNER_SIZE);

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        // Handle responsive
        handleSplitPaneResponsive();

        // Handle minimize and maximize request
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        InfoPanel infoPanel = new InfoPanel();
        infoPanePlaceholder.getChildren().add(infoPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        listPersonsPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        centerPanePlaceholder.getChildren().add(resultDisplay.getRoot());

        TitleBar titleBar = new TitleBar(prefs.getAddressBookFilePath());
        topTitlePlaceholder.getChildren().add(titleBar.getRoot());
        setAccelerator(titleBar.getControlHelp(), KeyCombination.valueOf("F1"));

        CommandBox commandBox = new CommandBox(logic);
        topCommandPlaceholder.getChildren().add(commandBox.getRoot());
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

    void show() {
        primaryStage.show();
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    public void requestFocus() {
        primaryStage.requestFocus();
    }

    /**
     * Handle responsiveness by fixing the width of {@code bottomListPane}
     * when increasing the width of {@code bottomPaneSplit}
     */
    private void handleSplitPaneResponsive() {
        int splitHandleSize = 5;

        bottomPaneSplit.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (bottomInfoPane.getWidth() > bottomInfoPane.getMinWidth() - splitHandleSize) {
                bottomPaneSplit.setDividerPosition(0, (
                        bottomListPane.getWidth() + splitHandleSize) / newValue.doubleValue());
            }
        });
    }

    /**
     * Sets the accelerator of help button pane.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(Pane pane, KeyCombination keyCombination) {
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                pane.getOnMouseClicked().handle(new javafx.scene.input.MouseEvent(
                        javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
                        MouseButton.PRIMARY, 0, false, false, false,
                        false, false, false, false,
                        false, false, false, null));
                event.consume();
            }
        });
    }

    private void setBorderlessWindow() {
        // StageStyle.UNDECORATED is buggy
        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }

    private void setDoubleClickMaximize() {
        topPane.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount() == 2) {
                raise(new MaximizeAppRequestEvent());
            }
        });
    }

    private void setDraggableTitleBar() {
        double minY = Screen.getPrimary().getVisualBounds().getMinY();

        topPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topPane.setOnMouseDragged(event -> {
            // Only allow in title bar (Blue area)
            if (xOffset > 120 && yOffset > 40 && xOffset + yOffset - 200 > 0) {
                return;
            }

            double newY = event.getScreenY() - yOffset;
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(Math.max(newY, minY));
        });
    }

    @Subscribe
    public void handleMinimizeAppRequestEvent(MinimizeAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        primaryStage.setIconified(true);
    }

    @Subscribe
    public void handleMaximizeAppRequestEvent(MaximizeAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        uiResizer.toggleMaximize();
    }
}
