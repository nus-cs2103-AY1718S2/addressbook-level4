package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.animation.Animation;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.UiUtil;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "command-error";
    public static final String PARSE_INVALID = "parse-invalid";
    public static final String PARSE_VALID = "parse-valid";

    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    private HBox floatParseRealTime;
    private Label floatParseLabel;

    // Animation
    private ArrayList<Animation> allAnimation = new ArrayList<>();
    private boolean animated;

    @FXML
    private TextField commandInput;

    public CommandBox(Logic logic, HBox floatParseRealTime, Label floatParseLabel, boolean animated) {
        super(FXML);
        this.logic = logic;
        this.floatParseRealTime = floatParseRealTime;
        this.floatParseLabel = floatParseLabel;
        this.animated = animated;

        // Command helper not tested for now
        if (floatParseRealTime != null && floatParseLabel != null) {
            setupInputChange();
        }

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandInput.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();
            navigateToPreviousInput();
            break;

        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;

        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;

        if (historySnapshot.hasPrevious()) {
            replaceText(historySnapshot.previous());
        }
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;

        if (historySnapshot.hasNext()) {
            replaceText(historySnapshot.next());
        }
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandInput.setText(text);
        commandInput.positionCaret(commandInput.getText().length());
    }

    private void setupInputChange() {
        commandInput.textProperty().addListener((obs, old, inputText) -> {
            String result = "Enter a command...";

            floatParseRealTime.getStyleClass().remove(PARSE_VALID);
            floatParseRealTime.getStyleClass().remove(PARSE_INVALID);

            if (!inputText.equals("")) {
                Command command = logic.parse(inputText);

                if (command == null) {
                    floatParseRealTime.getStyleClass().add(PARSE_INVALID);
                    result = "Invalid command";

                } else {
                    floatParseRealTime.getStyleClass().add(PARSE_VALID);
                    result = command.getParsedResult();
                    if (result == null) {
                        result = "Valid command";
                    }
                }
            }
            floatParseLabel.setText(result);
        });

        commandInput.focusedProperty().addListener((obs, old, focused) -> {
            if (animated) {
                Animation animation;
                allAnimation.forEach(Animation::pause);
                allAnimation.clear();

                if (focused) {
                    floatParseRealTime.setOpacity(0);
                    floatParseRealTime.setVisible(true);
                    animation = UiUtil.fadeNode(floatParseRealTime, true, 100, (e) -> {
                    });
                } else {
                    animation = UiUtil.fadeNode(floatParseRealTime, false, 100, (e) -> {
                        floatParseRealTime.setVisible(false);
                    });
                }

                allAnimation.add(animation);
                animation.play();
            } else {
                floatParseRealTime.setOpacity(focused ? 1 : 0);
                floatParseRealTime.setVisible(focused);
            }
        });
    }
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandInput.getText());
            initHistory();
            historySnapshot.next();

            // Process result of the command
            commandInput.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();

            // Handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandInput.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandInput.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandInput.getStyleClass();

        if (!styleClass.contains(ERROR_STYLE_CLASS)) {
            styleClass.add(ERROR_STYLE_CLASS);
        }
    }

}
