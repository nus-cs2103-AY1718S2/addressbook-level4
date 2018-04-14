//@@author emer7

package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A handle to the {@code ReviewDialog#reviewer} in the GUI.
 */
public class ReviewerInputHandle extends NodeHandle<TextField> {

    public static final String REVIEWER_INPUT_FIELD_ID = "reviewerInputTextField";

    public ReviewerInputHandle(TextField reviewerInputNode) {
        super(reviewerInputNode);
    }

    /**
     * Returns the text in the reviewer input box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public void run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
