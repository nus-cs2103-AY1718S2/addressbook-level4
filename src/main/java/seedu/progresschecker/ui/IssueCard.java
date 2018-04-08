package seedu.progresschecker.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.progresschecker.model.issues.Issue;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class IssueCard extends UiPart<Region> {

    private static final String FXML = "IssueListCard.fxml";
    private static final String[] LABEL_COLORS =
            { "red", "orange", "yellow", "green", "blue", "purple" };
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Issue issue;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label body;
    @FXML
    private Label milestone;
    @FXML
    private FlowPane labelOfIssue;
   

    public IssueCard(Issue issue, int displayedIndex) {
        super(FXML);
        this.issue = issue;
        id.setText(displayedIndex + ". ");
        title.setText(issue.getTitle().toString());
        body.setText(issue.getBody().fullBody);
        milestone.setText(issue.getMilestone().fullMilestone);
        issue.getLabelsList().forEach(labels -> {
            Label label = new Label(labels.fullLabels);
            label.getStyleClass().add(getTagColor(labels.fullLabels));
            labelOfIssue.getChildren().add(label);
        });
    }

    /**
     * Get a deterministic tag color based off tag's name value.
     */
    private String getTagColor(String labelName) {
        int index = getValueOfString(labelName) % LABEL_COLORS.length;
        return LABEL_COLORS[index];
    }

    /**
     * Adds each letter of given string into an integer.
     */
    private int getValueOfString(String labelName) {
        int sum = 0;
        for (char c : labelName.toCharArray()) {
            sum += c;
        }
        return sum;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IssueCard)) {
            return false;
        }

        // state check
        IssueCard card = (IssueCard) other;
        return id.getText().equals(card.id.getText())
                && issue.equals(card.issue);
    }
}
