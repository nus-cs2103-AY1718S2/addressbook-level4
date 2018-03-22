package seedu.recipe.ui;

import static seedu.recipe.ui.util.AutoCompletionUtil.APPLICATION_KEYWORDS;
import static seedu.recipe.ui.util.AutoCompletionUtil.MAX_SUGGESTIONS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

//@@author hoangduong1607
public class SuggestionsPopUp extends ContextMenu {

    private static final char LF = '\n';
    private static final char SPACE = ' ';

    private CommandBox commandBox;
    private TextArea commandTextArea;

    protected SuggestionsPopUp(CommandBox commandBox) {
        super();
        this.commandBox = commandBox;
        commandTextArea = commandBox.getCommandTextArea();
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
        String inputText = getLastWord(commandTextArea.getText());
        // finds suggestions and displays
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

        show(commandTextArea, Side.BOTTOM, anchorX, anchorY);
    }

    /**
     * Finds possible suggestions from {@code word} and
     * list of valid suggestions {@code textList}.
     */
    private void findSuggestions(String word, List<String> textList) {
        getItems().clear();
        Collections.sort(textList);

        for (String suggestion : textList) {
            if (suggestion.startsWith(word)) {
                addSuggestion(suggestion);
            }

            if (getItems().size() == MAX_SUGGESTIONS) {
                break;
            }
        }
    }

    /**
     * Adds a suggestion to suggestion list
     */
    private void addSuggestion(String suggestion) {
        MenuItem item = new MenuItem(suggestion);
        item.setOnAction(event -> commandBox.replaceText(replaceLastWord(commandTextArea.getText(), item.getText())));
        getItems().add(item);
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
}
