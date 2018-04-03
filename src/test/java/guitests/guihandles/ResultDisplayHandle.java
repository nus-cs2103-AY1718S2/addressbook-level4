package guitests.guihandles;

import java.util.List;

import javafx.scene.control.TextArea;

/**
 * A handler for the {@code ResultDisplay} of the UI
 */
public class ResultDisplayHandle extends NodeHandle<TextArea> {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(TextArea resultDisplayNode) {
        super(resultDisplayNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return getRootNode().getText();
    }

    /**
     * Clears all text in the Result Display.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean clear() {
        click();
        guiRobot.interact(() -> getRootNode().clear());
        return getRootNode().getText().equals("");
    }

    /**
     * Returns the list of style clases in the result display.
     */
    public List<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
