package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.ui.CommandBox;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle<TextField> {

    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(TextField commandBoxNode) {
        super(commandBoxNode);
    }

    /**
     * Returns the text in the command box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);

        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Enters the given command in the Command Box and presses enter twice.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean run_withAutocomplete(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
        guiRobot.type(KeyCode.ENTER);

        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }

    //@@author aquarinte
    /**
     * Types text into the command box and sets caret at the end of text.
     * Overwrites previous input.
     */
    public void setText(String input) {
        guiRobot.interact(() -> getRootNode().setText(input));
        guiRobot.interact(() -> getRootNode().positionCaret(getInput().length()));
    }

    /**
     * Inserts text into command box.
     */
    public void insertText(String input) {
        int caretPos = getRootNode().getCaretPosition();
        guiRobot.interact(() -> getRootNode().insertText(caretPos, input));
        guiRobot.interact(() -> getRootNode().positionCaret(caretPos + input.length()));
    }

    /**
     * Sets the caret position in the command box to {@code index}.
     */
    public void setCaretPosition(int index) {
        guiRobot.interact(() -> getRootNode().positionCaret(index));
    }

    /**
     * Removes change listener for autocomplete, so that it will not interfere with JUnit System Tests.
     */
    public void disableAutocomplete() {
        getRootNode().textProperty().removeListener(CommandBox.getAutocompleteListener());
    }
}
