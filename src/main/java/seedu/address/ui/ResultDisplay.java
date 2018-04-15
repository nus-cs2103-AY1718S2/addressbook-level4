package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.HomeRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.PopulatePrefixesRequestEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    //@@author jonleeyz-reused
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            displayed.setValue(event.message);
            if (event.isSuccessful()) {
                setStyleToIndicateCommandSuccess();
            } else {
                setStyleToIndicateCommandFailure();
            }
        });
    }
    //@@author

    //@@author jonleeyz
    /**
     * Handles the event where a valid keyboard shortcut is pressed
     * to populate the CommandBox with command prefixes,
     * {@code PopulatePrefixesRequestEvent}.
     */
    @Subscribe
    private void handlePopulatePrefixesRequestEvent(PopulatePrefixesRequestEvent event) {
        setStyleToIndicateCommandSuccess();
        Platform.runLater(() -> {
            displayed.setValue(event.commandUsageMessage);
        });
    }

    /**
     * Handles the event where the Esc key is pressed or "home" is input to the CommandBox.
     * {@code HomeRequestEvent}.
     */
    @Subscribe
    private void handleHomeRequestEvent(HomeRequestEvent event) {
        setStyleToIndicateCommandSuccess();
        Platform.runLater(() -> {
            displayed.setValue(event.MESSAGE_HOME);
        });
    }
    //@@author

    //@@author jonleeyz-reused
    private void setStyleToIndicateCommandSuccess() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }
    //@@author
}
