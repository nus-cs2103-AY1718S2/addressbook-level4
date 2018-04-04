package seedu.address.ui;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ShowSuggestionEvent;
import seedu.address.commons.events.ui.ToggleNotificationCenterEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TestAddEventCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int DOUBLE_KEY_TOLERANCE = 300;
    private static final String[] allCommandsWord = {AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
        RateCommand.COMMAND_WORD, ReviewCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, TestAddEventCommand.COMMAND_WORD, SortCommand.COMMAND_WORD,
        LockCommand.COMMAND_WORD, UnlockCommand.COMMAND_WORD, SetPasswordCommand.COMMAND_WORD,
        ChangeThemeCommand.COMMAND_WORD};
    private static final String[] allCommandsUsage = {AddCommand.MESSAGE_USAGE, EditCommand.MESSAGE_USAGE,
        RateCommand.MESSAGE_USAGE, ReviewCommand.MESSAGE_USAGE, SelectCommand.MESSAGE_USAGE,
        DeleteCommand.MESSAGE_USAGE, FindCommand.MESSAGE_USAGE, TestAddEventCommand.MESSAGE_USAGE,
        SortCommand.MESSAGE_USAGE, LockCommand.MESSAGE_USAGE, UnlockCommand.MESSAGE_USAGE,
        SetPasswordCommand.MESSAGE_USAGE, ChangeThemeCommand.MESSAGE_USAGE};

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private Queue<KeyEvent> consecutiveShiftPressed;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        consecutiveShiftPressed = new LinkedList<>();

        commandTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                raiseSuggestionEventIfMatchesCommandWord(newValue);
            }
        });
    }

    /**
     * Raises a ShowSuggestionEvent if the {@param newValue } matches one of command word.
     */
    private void raiseSuggestionEventIfMatchesCommandWord(String newValue) {
        try {
            newValue = (new Scanner(newValue)).next();
        } catch (NoSuchElementException e) {
            if (LogicManager.isLocked()) {
                raise(new ShowSuggestionEvent(ResultDisplay.WELCOME_MESSAGE));
            } else {
                raise(new ShowSuggestionEvent(ResultDisplay.SUGGEST_HELP_MESSAGE));
            }
            return;
        }
        boolean found = false;
        for (int i = 0; i < allCommandsWord.length; i++) {
            if (newValue.equals(allCommandsWord[i])) {
                raise(new ShowSuggestionEvent(allCommandsUsage[i]));
                found = true;
                break;
            }
        }
        if (!found) {
            raise(new ShowSuggestionEvent(""));
        }
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
        case SHIFT:
            registerShiftPressed(keyEvent);
            if (consecutiveShiftPressed.size() == 2) {
                resetWaitForSecondShift();
                raise(new ToggleNotificationCenterEvent());
            }
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Records SHIFT key has been registered and waits for the next SHIFT.
     */
    private void registerShiftPressed(KeyEvent keyEvent) {
        consecutiveShiftPressed.offer(keyEvent);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                resetWaitForSecondShift();
            }
        }, DOUBLE_KEY_TOLERANCE);
    }

    /**
     * Stops the wait for the second consecutive SHIFT (for double SHIFT keyEvent) and reset the metadata
     */
    private void resetWaitForSecondShift() {
        for (KeyEvent ke: consecutiveShiftPressed) {
            ke.consume();
        }
        consecutiveShiftPressed.clear();
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, true));
        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage(), false));
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
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }
}
