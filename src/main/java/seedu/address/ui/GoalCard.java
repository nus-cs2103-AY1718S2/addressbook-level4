package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.goal.Goal;

//@@author deborahlow97
/**
 * An UI component that displays information of a {@code Goal}.
 */
public class GoalCard extends UiPart<Region> {
    private static final int NOT_COMPLETED_COLOUR_STYLE = 0;
    private static final int COMPLETED_COLOUR_STYLE = 1;
    private static final String FXML = "GoalListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Goal goal;

    @FXML
    private HBox goalCardPane;
    @FXML
    private Label goalText;
    @FXML
    private Label id;
    @FXML
    private FlowPane importance;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private FlowPane completion;


    public GoalCard(Goal goal, int displayedIndex) {
        super(FXML);
        this.goal = goal;
        id.setText(displayedIndex + ". ");
        goalText.setText(goal.getGoalText().value);
        initImportance(goal);
        startDateTime.setText("Start " + goal.getStartDateTime().value);
        if (goal.getEndDateTime().value.equals("")) {
            endDateTime.setText(goal.getEndDateTime().value);
        } else {
            endDateTime.setText("End " + goal.getEndDateTime().value);
        }
        initCompletion(goal);
    }

    /**
     * Creates the completion label for {@code goal}.
     */
    private void initCompletion(Goal goal) {
        String trueOrFalseString = goal.getCompletion().value;
        if (trueOrFalseString.equals("true")) {
            Image completedImage = AppUtil.getImage("/images/completedImage.png");
            ImageView completedImageView = new ImageView(completedImage);
            completedImageView.setFitHeight(30);
            completedImageView.setFitWidth(30);
            Label completionLabel = new Label("Completed", completedImageView);
            completion.getChildren().add(completionLabel);
        } else {
            Image ongoingImage = AppUtil.getImage("/images/ongoingImage.png");
            ImageView ongoingImageView = new ImageView(ongoingImage);
            ongoingImageView.setFitHeight(27);
            ongoingImageView.setFitWidth(27);
            Label completionLabel = new Label("Ongoing", ongoingImageView);
            completion.getChildren().add(completionLabel);
        }
    }

    /**
     * Creates the importance label for {@code goal}.
     */
    private void initImportance(Goal goal) {
        String starValue = changeImportanceToStar(goal.getImportance().value);
        Label importanceLabel = new Label(starValue);
        importanceLabel.getStyleClass().add("yellow");
        importance.getChildren().add(importanceLabel);
    }

    /**
     * Takes in @param value representing the importance value
     * @return a number of star string.
     */
    public static String changeImportanceToStar(String value) {
        int intValue = Integer.parseInt(value);
        String starString = "";
        for (int i = 0; i < intValue; i++) {
            starString = starString + '\u2605' + " ";
        }
        return starString;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GoalCard)) {
            return false;
        }

        // state check
        GoalCard card = (GoalCard) other;
        return id.getText().equals(card.id.getText())
                && goal.equals(card.goal);
    }
}
