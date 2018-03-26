package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BirthdayNotificationEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class BirthdayNotificationWindow extends UiPart<Stage> {

    private static final String FXML = "PasswordWindow.fxml";

    private final Logger logger = LogsCenter.getLogger("MainWindow");

    private Stage primaryStage;

    // Independent Ui parts residing in this Ui container
    private BirthdayNotification birthdayNotification;

    @FXML
    private StackPane birthdayNotificationPlaceholder;

    public BirthdayNotificationWindow(Stage primaryStage) {
        super(FXML, primaryStage);
        // Set dependencies
        this.primaryStage = primaryStage;
        fillInnerParts();

        setTitle("Birthdays Today");
        // setWindowDefaultSize(prefs);
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        birthdayNotification = new BirthdayNotification(null);
        birthdayNotificationPlaceholder.getChildren().add(birthdayNotification.getRoot());
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

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    void releaseResources() {
        birthdayNotification.freeResources();
    }


    @Subscribe
    private void handleBirthdayNotificationEvent(BirthdayNotificationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        show();
    }

}
