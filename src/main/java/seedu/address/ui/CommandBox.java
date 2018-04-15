package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.EditRemarkEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UnmatchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private boolean isFindNextField = false;
    private boolean isMatchCommand = false;
    private boolean isEditRemarkCommand;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        isEditRemarkCommand = false;

        registerAsAnEventHandler(this);
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
        //@@author sherlynng
        case TAB:
            keyEvent.consume();
            autofill();
            break;
        case DELETE:
            keyEvent.consume();
            deletePreviousPrefix();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Sets {@code CommandBox}'s text field with command format and
     * if next field is present, caret is positioned to the next field.
     */
    private void autofill() {
        String input = commandTextField.getText();
        int nextCaretPosition = -1;
        boolean isFirstTime = false; // check for commands that have different behaviors between first and other tabs

        isFirstTime = autofillCommand(input, isFirstTime);
        autofillBehavior(isFirstTime);
    }

    /**
     * Autofills the command depending on user input.
     * @param input
     * @param isFirstTime
     * @return true if it is the first time tab is pressed. Else false.
     */
    private boolean autofillCommand(String input, boolean isFirstTime) {
        switch (input) {
        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(AddCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_ROLE);
            isFindNextField = true;
            isMatchCommand = false;
            break;
        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(EditCommand.COMMAND_WORD + " 1 " + PREFIX_NAME + " " + PREFIX_PHONE + " "
                    + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_PRICE + " " + PREFIX_SUBJECT + " "
                    + PREFIX_LEVEL + " " + PREFIX_ROLE);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(RemarkCommand.COMMAND_WORD + " 1 " + PREFIX_REMARK);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case RateCommand.COMMAND_WORD:
        case RateCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(RateCommand.COMMAND_WORD + " 1 " + PREFIX_RATE);
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            isMatchCommand = false;
            break;
        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(SelectCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(DeleteCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case UnmatchCommand.COMMAND_WORD:
        case UnmatchCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(UnmatchCommand.COMMAND_WORD + " 1");
            selectIndexToEdit();
            isFindNextField = false;
            isMatchCommand = false;
            break;
        case MatchCommand.COMMAND_WORD:
        case MatchCommand.COMMAND_WORD_ALIAS:
            commandTextField.setText(MatchCommand.COMMAND_WORD + " 1 2");
            selectIndexToEdit();
            isFindNextField = false;
            isFirstTime = true;
            break;
        default:
            // no autofill
        }
        return isFirstTime;
    }

    /**
     * Positions the caret according to command type.
     * @param isFirstTime is used to differentiate commands that have different behaviors for different tabs.
     */
    private void autofillBehavior(boolean isFirstTime) {
        int nextCaretPosition;
        if (isFindNextField) {
            nextCaretPosition = findNextField();
            if (nextCaretPosition != -1) {
                commandTextField.positionCaret(nextCaretPosition);
            }
        }

        if (isMatchCommand) {
            selectIndexToEdit();
        }

        if (isFirstTime) {
            if (commandTextField.getText().length() >= MatchCommand.COMMAND_WORD.length()
                    && commandTextField.getText().substring(0, 5).equals(MatchCommand.COMMAND_WORD)) { // match command
                isMatchCommand = true;
            } else { // all other commands that has finding next field as its subsequent behavior
                isFindNextField = true;
            }
        }
    }

    /**
     * Deletes the previous prefix from current caret position and
     * if next field is present, caret is positioned to the next field.
     */
    private void deletePreviousPrefix() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int deleteStart = text.lastIndexOf(" ", caretPosition - 1);

        if (deleteStart != -1) {
            commandTextField.deleteText(deleteStart, caretPosition);
            commandTextField.positionCaret(findNextField());
        }
    }

    /**
     * Finds the next input field from current caret position and
     * if next field is present, caret is positioned to the next field.
     */
    private int findNextField() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int nextFieldPosition = text.indexOf("/", caretPosition);

        return nextFieldPosition + 1;
    }

    /**
     * Positions the caret to index position and selects the index to be edited.
     */
    private void selectIndexToEdit() {
        String text = commandTextField.getText();
        int caretPosition = commandTextField.getCaretPosition();
        int indexPosition = -1;

        for (int i = caretPosition; i < text.length(); i++) {
            Character character = text.charAt(i);
            if (Character.isDigit(character)) {
                indexPosition = i;
                break;
            }
        }

        commandTextField.positionCaret(indexPosition);
        if (indexPosition != -1) {
            commandTextField.selectForward();
        }
    }
    //@@author

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
            if (!isEditRemarkCommand) {
                commandTextField.setText("");
            } else {
                // do not replace text
                isEditRemarkCommand = false; // reset it back to default
            }
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
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

    //@@author sherlynng
    @Subscribe
    private void handleEditRemarkEvent(EditRemarkEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        replaceText(event.getPersonRemark());

        isEditRemarkCommand = true;
    }
    //@@author
}
