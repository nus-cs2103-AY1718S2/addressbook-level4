//@@author emer7
package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ReviewInputEvent;

/**
 * Pop-up dialog to prompt user to enter the review.
 */
public class ReviewDialog {

    public static final String REVIEW_DIALOG_PANE_FIELD_ID = "reviewDialogPane";

    private Dialog<Pair<String, String>> dialog;
    private ButtonType reviewButtonType;
    private TextField reviewer;
    private TextArea review;
    private GridPane grid;
    private Node reviewButton;
    private Logger logger = LogsCenter.getLogger(this.getClass());

    public ReviewDialog() {
        dialog = new Dialog<>();
        dialog.getDialogPane().setId(REVIEW_DIALOG_PANE_FIELD_ID);
        dialog.setTitle("Review Dialog");
        dialog.setHeaderText("Reviewer must be a valid email address"
                + "\n"
                + "Review accepts any characters and has no length limit.");

        reviewButtonType = new ButtonType("Review", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(reviewButtonType, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        reviewer = new TextField();
        reviewer.setPromptText("Enter your email address here");
        reviewer.setId("reviewerInputTextField");
        review = new TextArea();
        review.setPromptText("Enter your review here");
        review.setId("reviewInputTextField");

        grid.add(new Label("Reviewer:"), 0, 0);
        grid.add(reviewer, 1, 0);
        grid.add(new Label("Review:"), 0, 1);
        grid.add(review, 1, 1);

        reviewButton = dialog.getDialogPane().lookupButton(reviewButtonType);
        reviewButton.setDisable(true);

        reviewer.textProperty().addListener((observable, oldValue, newValue) -> {
            reviewButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> reviewer.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == reviewButtonType) {
                return new Pair<>(reviewer.getText(), review.getText());
            }
            return null;
        });
    }

    /**
     * Show the pop-up dialog when called
     */
    public void show() {
        Optional <Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(input -> {
            EventsCenter.getInstance().post(new ReviewInputEvent(input.getKey(), input.getValue()));
        });
    }
}
