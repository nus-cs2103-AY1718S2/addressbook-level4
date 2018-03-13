package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.*;
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

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
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

        case TAB:
            keyEvent.consume();
            autocomplete();
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
     * Tries to complete the last word of the text field
     */
    private void autocomplete() {
        String input = commandTextField.getText();
        String[] words = input.trim().split(" ");
        Set<String> possibilities = new HashSet<>();

        //trying to complete a command
        if(words.length == 1 && words[0].length() > 0){
            String[] commandsList = {AddCommand.COMMAND_WORD,
                    ClearCommand.COMMAND_WORD,
                    DeleteCommand.COMMAND_WORD,
                    EditCommand.COMMAND_WORD,
                    ExitCommand.COMMAND_WORD,
                    FindCommand.COMMAND_WORD,
                    HelpCommand.COMMAND_WORD,
                    HistoryCommand.COMMAND_WORD,
                    ListCommand.COMMAND_WORD,
                    RedoCommand.COMMAND_WORD,
                    SelectCommand.COMMAND_WORD,
                    UndoCommand.COMMAND_WORD};

            for(String command: commandsList){
                if(command.startsWith(words[0])) {
                    //we only append the end of the command
                    possibilities.add(command.substring(words[0].length()));
                }
            }
        }
        //trying to complete a field
        else if(words.length > 1) {
            int lastFieldIndex = input.lastIndexOf('/');

            if(lastFieldIndex > 0 && input.substring(lastFieldIndex).length() > 0){
                String field = input.substring(lastFieldIndex + 1);
                List<String> fieldsList;

                switch (input.charAt(lastFieldIndex - 1)) {
                    case 'n':
                        fieldsList = logic.getFilteredPersonList().stream().map(person -> person.getName().toString()).collect(Collectors.toList());
                        break;

                    case 'p':
                        fieldsList = logic.getFilteredPersonList().stream().map(person -> person.getPhone().toString()).collect(Collectors.toList());
                        break;

                    case 'e':
                        fieldsList = logic.getFilteredPersonList().stream().map(person -> person.getEmail().toString()).collect(Collectors.toList());
                        break;

                    case 'a':
                        fieldsList = logic.getFilteredPersonList().stream().map(person -> person.getAddress().toString()).collect(Collectors.toList());
                        break;

                    default:
                        fieldsList = new ArrayList<>();
                        break;
                }

                for(String fieldPossibility: fieldsList){

                    if(fieldPossibility.startsWith(field)) {
                        //we only append the end of the field
                        possibilities.add(fieldPossibility.substring(field.length()));
                    }
                }
            }
        }

        if(possibilities.size() > 0) {
            String p = (String) possibilities.toArray()[0];
            String longestCommonPrefix = "";

            for (int i = 0; i < p.length(); i++) {
                boolean isPrefixOkay = true;

                for (String possibility : possibilities) {
                    if (!possibility.startsWith(longestCommonPrefix + p.charAt(i))) {
                        isPrefixOkay = false;
                    }
                }
                if (!isPrefixOkay) {
                    break;
                }

                longestCommonPrefix += p.charAt(i);
            }

            commandTextField.appendText(longestCommonPrefix);
        }
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

}
