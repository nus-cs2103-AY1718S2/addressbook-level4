package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PasswordCorrectEvent;
import seedu.address.commons.events.ui.PasswordWrongEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

//@@author yeggasd
/**
 * The manager of the Password UI component.
 */
public class PasswordUiManager extends ComponentManager implements Ui {
    public static final String WRONG_PASSWORD_ERROR_DIALOG_STAGE_TITLE = "Password Wrong Error";
    public static final String WRONG_PASSWORD_ERROR_DIALOG_HEADER_MESSAGE = "Wrong Password used";
    public static final String WRONG_PASSWORD_ERROR_DIALOG_CONTENT_MESSAGE = "Try Again";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";
    private static final double MAX_WINDOW_SIZE = Double.MAX_VALUE;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private Storage storage;
    private Model model;
    private Ui ui;

    private PasswordWindow pw;
    private Stage primaryStage;

    public PasswordUiManager(Storage storage, Model model, Ui ui) {
        super();
        this.storage = storage;
        this.model = model;
        this.ui = ui;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        this.primaryStage = primaryStage;
        try {
            pw = new PasswordWindow(primaryStage, model, storage);
            pw.show();
            pw.fillInnerParts();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private void showPasswordWrongAlertAndWait(String description, String details) {
        showAlertDialogAndWait(AlertType.ERROR, WRONG_PASSWORD_ERROR_DIALOG_STAGE_TITLE, description, details);
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(primaryStage, type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        pw.hide();
        pw.releaseResources();
    }
    @Subscribe
    private void handlePasswordCorrectEvent(PasswordCorrectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(MAX_WINDOW_SIZE);
        ui.start(primaryStage); (
                (UiManager) ui).openBirthdayNotification();
    }

    @Subscribe
    private void handlePasswordWrongEvent(PasswordWrongEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPasswordWrongAlertAndWait(WRONG_PASSWORD_ERROR_DIALOG_HEADER_MESSAGE,
                WRONG_PASSWORD_ERROR_DIALOG_CONTENT_MESSAGE);
    }

}
