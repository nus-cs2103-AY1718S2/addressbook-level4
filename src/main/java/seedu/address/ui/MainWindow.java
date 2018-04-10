package seedu.address.ui;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.Animation;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.MaximizeAppRequestEvent;
import seedu.address.commons.events.ui.MinimizeAppRequestEvent;
import seedu.address.commons.events.ui.ShowPanelRequestEvent;
import seedu.address.commons.util.UiUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final double MIN_WINDOW_WIDTH = 800;
    public static final double MIN_WINDOW_HEIGHT = 600;
    public static final int WINDOW_CORNER_SIZE = 8;

    private static final String FXML = "MainWindow.fxml";
    private static final double MAX_ANIMATION_TIME_MS = 200;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;
    private UiResizer uiResizer;
    private Config config;
    private UserPrefs prefs;

    // Independent UI parts residing in this UI container
    private InfoPanel infoPanel;
    private PdfPanel pdfPanel;
    private PersonListPanel personListPanel;

    // X and Y offset of the window (Use for draggable title bar
    private double xOffset = 0;
    private double yOffset = 0;

    // Animation
    private boolean animated;
    private ArrayList<Animation> allAnimation = new ArrayList<>();
    private Node activeNode;
    private String activePanel;

    @FXML
    private AnchorPane topPane;

    @FXML
    private AnchorPane resumePanePlaceholder;

    @FXML
    private AnchorPane infoPanePlaceholder;

    @FXML
    private VBox welcomePane;

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

    // Floating box
    @FXML
    private HBox floatParseRealTime;

    @FXML
    private Label floatParseLabel;

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

        // Animation setup
        this.animated = prefs.isAnimated();

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
        infoPanel = new InfoPanel(animated);
        infoPanePlaceholder.getChildren().add(infoPanel.getRoot());

        pdfPanel = new PdfPanel();
        resumePanePlaceholder.getChildren().add(pdfPanel.getRoot());

        ObservableList<Person> personList = logic.getActivePersonList();
        personListPanel = new PersonListPanel(personList, animated);
        listPersonsPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        centerPanePlaceholder.getChildren().add(resultDisplay.getRoot());

        TitleBar titleBar = new TitleBar(prefs.getAddressBookFilePath());
        topTitlePlaceholder.getChildren().add(titleBar.getRoot());
        setAccelerator(titleBar.getControlHelp(), KeyCombination.valueOf("F1"));

        CommandBox commandBox = new CommandBox(logic, floatParseRealTime, floatParseLabel);
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

    //@@author Ang-YC
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
    private void handleShowPanelRequestEvent(ShowPanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        String requested = event.getRequestedPanel();
        Node toHide = activeNode;
        Animation fadeIn;
        Animation fadeOut;

        // Don't animate if the currently active panel is what requested
        if (!Objects.equals(activePanel, requested)) {
            // Pause all current running animation
            allAnimation.forEach(Animation::pause);
            allAnimation.clear();

            activePanel = requested;

            // Show relevant panel
            if (PdfPanel.PANEL_NAME.equals(requested)) {
                pdfPanel.load();
                activeNode = resumePanePlaceholder;
            } else if (InfoPanel.PANEL_NAME.equals(requested)) {
                infoPanel.hide();
                activeNode = infoPanePlaceholder;
                infoPanel.show();
            } else if ("WelcomePane".equals(requested)) {
                activeNode = welcomePane;
            }

            if (activeNode == null) {
                return;
            }

            if (animated) {
                // Show currently requested panel
                activeNode.setOpacity(0);
                activeNode.setVisible(true);

                fadeIn = UiUtil.fadeNode(activeNode, true, MAX_ANIMATION_TIME_MS, ev -> { });
                allAnimation.add(fadeIn);
                fadeIn.play();

                // Hide the previously selected panel
                if (toHide != null) {
                    fadeOut = UiUtil.fadeNode(toHide, false,
                            MAX_ANIMATION_TIME_MS, ev -> onFinishAnimation(toHide));
                    allAnimation.add(fadeOut);
                    fadeOut.play();
                }
            } else {
                // Show currently requested panel
                activeNode.setOpacity(1);
                activeNode.setVisible(true);

                // Hide the previously selected panel
                onFinishAnimation(toHide);
            }
        }
    }

    /**
     * Hide and unload relevant nodes when animation is done playing
     * @param toHide The window to hide
     */
    private void onFinishAnimation(Node toHide) {
        // Hide the previously selected panel
        if (toHide != null) {
            toHide.setVisible(false);
            if (toHide.equals(resumePanePlaceholder)) {
                pdfPanel.unload();
            }
        }
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
