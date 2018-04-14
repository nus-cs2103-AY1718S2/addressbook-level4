//@@author emer7

package guitests.guihandles;

import javafx.collections.ObservableList;
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
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
