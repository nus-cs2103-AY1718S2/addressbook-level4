//@@author emer7
package guitests.guihandles;

import static guitests.guihandles.ReviewInputHandle.REVIEW_INPUT_FIELD_ID;
import static guitests.guihandles.ReviewerInputHandle.REVIEWER_INPUT_FIELD_ID;
import static seedu.address.ui.ReviewDialog.REVIEW_DIALOG_PANE_FIELD_ID;

import guitests.GuiRobot;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 * A handle to the {@code ReviewDialog} of the application.
 */
public class ReviewDialogHandle extends StageHandle {

    public static final String REVIEW_DIALOG_WINDOW_TITLE = "Review Dialog";

    private final DialogPane dialogPane;

    public ReviewDialogHandle(Stage reviewDialogStage) {
        super(reviewDialogStage);

        this.dialogPane = getChildNode("#" + REVIEW_DIALOG_PANE_FIELD_ID);
    }

    /**
     * Returns true if a review dialog is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(REVIEW_DIALOG_WINDOW_TITLE);
    }

    public void setReviewerInputTextField(String value) {
        ReviewerInputHandle reviewerInput = new ReviewerInputHandle(getChildNode("#" + REVIEWER_INPUT_FIELD_ID));
        reviewerInput.run(value);
    }

    public void setReviewInputTextField(String value) {
        ReviewInputHandle reviewInput = new ReviewInputHandle(getChildNode("#" + REVIEW_INPUT_FIELD_ID));
        reviewInput.run(value);
    }
}
