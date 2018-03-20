package seedu.recipe.ui;

import static seedu.recipe.ui.util.KeyboardShortcutsMapping.COMMAND_SUBMISSION;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.LAST_COMMAND;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.NEW_LINE_IN_COMMAND;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.NEXT_COMMAND;
import static seedu.recipe.ui.util.KeyboardShortcutsMapping.SHOW_SUGGESTIONS_COMMAND;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.events.ui.NewResultAvailableEvent;
import seedu.recipe.logic.ListElementPointer;
import seedu.recipe.logic.Logic;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.parser.CliSyntax;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final char LF = '\n';
    private static final char SPACE = ' ';
    private static final String[] APPLICATION_KEYWORDS = {"add", "clear", "delete", "edit", "exit", "find",
        "help", "history", "list", "redo", "select", "tag", "undo", CliSyntax.PREFIX_INGREDIENT.toString(),
        CliSyntax.PREFIX_INSTRUCTION.toString(), CliSyntax.PREFIX_NAME.toString(),
        CliSyntax.PREFIX_PREPARATION_TIME.toString(), CliSyntax.PREFIX_TAG.toString(),
        CliSyntax.PREFIX_URL.toString()};
    private static final int MAX_SUGGESTIONS = 4;

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    @FXML
    private TextArea commandTextArea;
    private ContextMenu suggestionPopUp;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextArea.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        hideSuggestions();
        if (COMMAND_SUBMISSION.match(keyEvent)) {
            keyEvent.consume();
            submitCommand();
        } else if (LAST_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            navigateToPreviousInput();
        } else if (NEXT_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            navigateToNextInput();
        } else if (NEW_LINE_IN_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            createNewLine();
        } else if (SHOW_SUGGESTIONS_COMMAND.match(keyEvent)) {
            keyEvent.consume();
            showSuggestions();
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

    //@@hoangduong1607
    /**
     * Shows suggestions for commands when users type in Command Box
     */
    private void showSuggestions() {
        String inputText = getLastWord(commandTextArea.getText());
        // finds suggestions and displays
        suggestionPopUp = new ContextMenu();
        findSuggestions(inputText, Arrays.asList(APPLICATION_KEYWORDS));

        // gets caret position based on input text and font
        Text wholeCommand = new Text(commandTextArea.getText());
        Text lastLine = new Text(removeLastWord(getLastLine(commandTextArea.getText())));
        wholeCommand.setFont(commandTextArea.getFont());
        lastLine.setFont(commandTextArea.getFont());
        double textHeight = Math.min(wholeCommand.prefHeight(-1), commandTextArea.getHeight());
        double textWidth = lastLine.prefWidth(-1);

        double anchorX = textWidth;
        double anchorY = -commandTextArea.getHeight() + commandTextArea.getBorder().getInsets().getTop() + textHeight;

        suggestionPopUp.show(commandTextArea, Side.BOTTOM, anchorX, anchorY);
    }

    /**
     * Finds possible suggestions from {@code word} and
     * list of valid suggestions {@code textList}.
     */
    private void findSuggestions(String word, List<String> textList) {
        Collections.sort(textList);

        for (String suggestion : textList) {
            if (suggestion.startsWith(word)) {
                addSuggestion(suggestion);
            }

            if (suggestionPopUp.getItems().size() == MAX_SUGGESTIONS) {
                break;
            }
        }
    }

    /**
     * Adds a suggestion to suggestion list
     */
    private void addSuggestion(String suggestion) {
        MenuItem item = new MenuItem(suggestion);
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                replaceText(replaceLastWord(commandTextArea.getText(), item.getText()));
            }
        });
        suggestionPopUp.getItems().add(item);
    }

    /**
     * Hides suggestions
     */
    private void hideSuggestions() {
        if (suggestionPopUp != null && suggestionPopUp.isShowing()) {
            suggestionPopUp.hide();
        }
    }

    /**
     * Gets last word in user input from {@code inputText}
     */
    private String getLastWord(String inputText) {
        String lastWord = new String("");

        for (int i = inputText.length() - 1; i >= 0; i--) {
            if (isWordSeparator(inputText.charAt(i))) {
                break;
            }
            lastWord = inputText.charAt(i) + lastWord;
        }

        return lastWord;
    }

    /**
     * Checks whether {@code inputChar} is a word separator
     */
    private boolean isWordSeparator(char inputChar) {
        return (inputChar == LF || inputChar == SPACE);
    }

    /**
     * Gets last line in user input from {@code inputText}
     */
    private String getLastLine(String inputText) {
        String lastLine = new String("");

        for (int i = inputText.length() - 1; i >= 0; i--) {
            if (isLineSeparator(inputText.charAt(i))) {
                break;
            }
            lastLine = inputText.charAt(i) + lastLine;
        }

        return lastLine;
    }

    /**
     * Checks whether {@code inputChar} is a line separator
     */
    private boolean isLineSeparator(char inputChar) {
        return (inputChar == LF);
    }

    /**
     * Replaces last word of {@code text} with {@code newLastWord}
     */
    private String replaceLastWord(String text, String newLastWord) {
        return removeLastWord(text) + newLastWord;
    }

    /**
     * Removes last word from {@code text}
     */
    private String removeLastWord(String text) {
        int newLength = text.length() - getLastWord(text).length();
        return text.substring(0, newLength);
    }

    //@@author
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
        commandTextArea.setText(text);
        commandTextArea.positionCaret(commandTextArea.getText().length());
    }

    /**
     * Append a line feed character to the command area
     */
    private void createNewLine() {
        int caretPosition = commandTextArea.getCaretPosition();
        StringBuilder commandTextStringBuilder = new StringBuilder(commandTextArea.getText());
        commandTextStringBuilder.insert(caretPosition, LF);
        String newCommandText = commandTextStringBuilder.toString();
        commandTextArea.setText(newCommandText);
        commandTextArea.positionCaret(caretPosition + 1);
    }

    /**
     * Handles the command submission.
     */
    @FXML
    private void submitCommand() {
        try {
            CommandResult commandResult = logic.execute(commandTextArea.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextArea.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextArea.getText());
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
        commandTextArea.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextArea.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
