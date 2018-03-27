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
import seedu.address.commons.events.ui.PopulateRequestEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.LocateCommand;

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

    @Subscribe
    private void handleNewPopulateRequestEvent(PopulateRequestEvent event) {
        setStyleToIndicateCommandSuccess();
        final String messageUsage;
        switch (event.command) {
            case "add":
                messageUsage = AddCommand.MESSAGE_USAGE;
                break;
            case "edit":
                messageUsage = EditCommand.MESSAGE_USAGE;
                break;
            case "delete":
                messageUsage = DeleteCommand.MESSAGE_USAGE;
                break;
            case "locate":
                messageUsage = LocateCommand.MESSAGE_USAGE;
                break;
            case "find":
                messageUsage = FindCommand.MESSAGE_USAGE;
                break;
            default:
                // should be an Exception
                messageUsage = "";
        }

        Platform.runLater(() -> {
            displayed.setValue(messageUsage);
        });
    }

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
}
