//@@author emer7

package guitests.guihandles;

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
     * Enters the given value in the text field and presses enter.
     */
    public void run(String value) {
        click();
        guiRobot.interact(() -> getRootNode().setText(value));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
    }
}
