//@@author emer7

package guitests.guihandles;

import javafx.scene.control.TextArea;

/**
 * A handle to the {@code ReviewDialog#review} in the GUI.
 */
public class ReviewInputHandle extends NodeHandle<TextArea> {

    public static final String REVIEW_INPUT_FIELD_ID = "reviewInputTextField";

    public ReviewInputHandle(TextArea reviewInputNode) {
        super(reviewInputNode);
    }

    /**
     * Returns the text in the review input box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given value in the text area and presses enter.
     */
    public void run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();
    }
}
