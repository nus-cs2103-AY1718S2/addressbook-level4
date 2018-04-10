//@@author emer7
package guitests.guihandles;

import static seedu.address.ui.ReviewDialog.REVIEW_DIALOG_PANE_FIELD_ID;

import guitests.GuiRobot;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class ReviewDialogHandle extends StageHandle {

    public static final String REVIEW_DIALOG_WINDOW_TITLE = "Review Dialog";

    private final DialogPane dialogPane;

    public ReviewDialogHandle(Stage reviewDialogStage) {
        super(reviewDialogStage);

        this.dialogPane = getChildNode("#" + REVIEW_DIALOG_PANE_FIELD_ID);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(REVIEW_DIALOG_WINDOW_TITLE);
    }
}
