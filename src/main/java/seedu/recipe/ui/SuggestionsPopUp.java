package seedu.recipe.ui;

import static seedu.recipe.ui.util.AutoCompletionUtil.APPLICATION_COMMANDS;
import static seedu.recipe.ui.util.AutoCompletionUtil.APPLICATION_KEYWORDS;
import static seedu.recipe.ui.util.AutoCompletionUtil.MAX_SUGGESTIONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import seedu.recipe.ui.util.AutoCompletionUtil;
import seedu.recipe.ui.util.TextInputProcessorUtil;

//@@author hoangduong1607
/**
 * The component that is responsible for showing a suggestion list for auto-completion
 */
public class SuggestionsPopUp extends ContextMenu {

    private CommandBox commandBox;
    private TextArea commandTextArea;
    private TextInputProcessorUtil textInputProcessor;
    private AutoCompletionUtil autoCompletionUtil;

    protected SuggestionsPopUp(CommandBox commandBox, AutoCompletionUtil autoCompletionUtil) {
        super();
        this.commandBox = commandBox;
        commandTextArea = commandBox.getCommandTextArea();
        textInputProcessor = new TextInputProcessorUtil();
        this.autoCompletionUtil = autoCompletionUtil;
    }

    /**
     * Hides suggestions
     */
    protected void hideSuggestions() {
        if  (isShowing()) {
            hide();
        }
    }

    /**
     * Shows suggestions for commands when users type in Command Box
     */
    protected void showSuggestions() {
        textInputProcessor.setContent(commandTextArea.getText());
        textInputProcessor.setFont(commandTextArea.getFont());
        String lastWord = textInputProcessor.getLastWord();
        // finds suggestions and displays
        ArrayList<String> suggestionList = new ArrayList<>(APPLICATION_KEYWORDS);
        suggestionList.addAll(APPLICATION_COMMANDS);

        findSuggestions(lastWord, suggestionList);

        // gets caret position based on input text and font
        double anchorX = findDisplayPositionX(textInputProcessor.getCaretPositionX());
        double anchorY = findDisplayPositionY(textInputProcessor.getCaretPositionY());

        show(commandTextArea, Side.BOTTOM, anchorX, anchorY);
    }

    /**
     * Finds possible suggestions from {@code word} and
     * list of valid suggestions {@code textList}.
     */
    private void findSuggestions(String prefix, List<String> dictionary) {
        getItems().clear();
        Collections.sort(dictionary);

        for (String suggestion : dictionary) {
            if (suggestion.startsWith(prefix)) {
                addSuggestion(suggestion);
            }

            if (getItems().size() == MAX_SUGGESTIONS) {
                break;
            }
        }
    }

    /**
     * Finds X-coordinate to display SuggestionsPopUp in CommandBox
     */
    double findDisplayPositionX(double caretPositionX) {
        return commandBox.getRoot().getLayoutX() + commandTextArea.getInsets().getLeft() + caretPositionX;
    }

    /**
     * Finds Y-coordinate to display SuggestionsPopUp in CommandBox
     */
    double findDisplayPositionY(double caretPositionY) {
        return Math.min(-commandTextArea.getHeight() + commandTextArea.getInsets().getTop()
                + commandTextArea.getInsets().getBottom() + caretPositionY, -commandTextArea.getInsets().getBottom());
    }

    /**
     * Adds a suggestion to suggestion list
     */
    private void addSuggestion(String suggestion) {
        MenuItem item = new MenuItem(suggestion);
        textInputProcessor.setContent(commandTextArea.getText());

        String autoCompletionText;
        int caretPosition;
        if (autoCompletionUtil.isCommandKeyWord(item.getText())) {
            autoCompletionText = autoCompletionUtil.getAutoCompletionText(item.getText());
            caretPosition = item.getText().length() + 1;
        } else {
            autoCompletionText = textInputProcessor.replaceLastWord(item.getText());
            caretPosition = autoCompletionText.length();
        }

        item.setOnAction(event -> {
            commandBox.replaceText(autoCompletionText);
            commandTextArea.positionCaret(caretPosition);
        });
        getItems().add(item);
    }
}
