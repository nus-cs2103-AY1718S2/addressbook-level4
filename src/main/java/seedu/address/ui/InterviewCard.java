//@@author deeheenguyen
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.interview.Interview;

/**
 * An UI component that displays information of a {@code Interview}.
 */
public class InterviewCard extends UiPart<Region> {
    private static final String FXML = "InterviewListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Interview interview;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label interviewTitle;
    @FXML
    private Label interviewLocation;
    @FXML
    private Label interviewee;
    @FXML
    private Label date;

    public InterviewCard(Interview interview, int displayedIndex) {
        super(FXML);
        this.interview = interview;
        id.setText(displayedIndex + ". ");
        interviewTitle.setText(interview.getInterviewTitle().toString());
        interviewLocation.setText(interview.getInterviewLocation().value);
        interviewee.setText(interview.getInterviewee().toString());
        date.setText(interview.getDate().toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobCard)) {
            return false;
        }

        // state check
        InterviewCard card = (InterviewCard) other;
        return id.getText().equals(card.id.getText())
                && interview.equals(card.interview);
    }
}




