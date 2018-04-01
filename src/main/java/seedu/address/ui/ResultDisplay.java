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
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowSuggestionEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    public static final String SUGGESTION_STYLE_CLASS = "suggestion";
    public static final String WELCOME_MESSAGE = "Welcome to Employee Tracker (ET) ! Please unlock ET to use it! If " +
            "you need help, please press F1 or use help command!";

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        setStyleForSuggestion();
        Platform.runLater(() -> displayed.setValue(WELCOME_MESSAGE));
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        removeStyleForSuggestion();
        Platform.runLater(() -> displayed.setValue(event.message));
        if (event.isSuccessful) {
            setStyleToIndicateCommandSuccess();
        } else {
            setStyleToIndicateCommandFailure();
        }
    }

    @Subscribe
    private void handleShowSuggestionEvent(ShowSuggestionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setStyleForSuggestion();
        Platform.runLater(() -> displayed.setValue(event.getSuggestion()));
    }

    /**
     * Sets the {@code ResultDisplay} style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the {@code ResultDisplay} style to use the default style.
     */
    private void setStyleToIndicateCommandSuccess() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the {@code ResultDisplay} style to use the suggestion style.
     */
    private void setStyleForSuggestion() {
        if (!resultDisplay.getStyleClass().contains(SUGGESTION_STYLE_CLASS)) {
            resultDisplay.getStyleClass().add(SUGGESTION_STYLE_CLASS);
        }
    }

    /**
     * Sets the {@code ResultDisplay} style to use the suggestion style.
     */
    private void removeStyleForSuggestion() {
        resultDisplay.getStyleClass().remove(SUGGESTION_STYLE_CLASS);
    }

}
