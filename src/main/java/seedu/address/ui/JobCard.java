package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.job.Job;

//@@author ChengSashankh
/**
 * An UI component that displays information of a {@code Job}.
 */
public class JobCard extends UiPart<Region> {
    private static final String FXML = "JobListCard.fxml";
    private static final String[] TAG_COLORS = {"red", "pink", "blue"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Job job;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label jobTitle;
    @FXML
    private Label jobLocation;
    @FXML
    private Label jobSkills;
    @FXML
    private FlowPane tags;

    public JobCard(Job job, int displayedIndex) {
        super(FXML);
        this.job = job;
        id.setText(displayedIndex + ". ");
        jobTitle.setText(job.getJobTitle().fullTitle);
        jobLocation.setText(job.getLocation().value);
        jobSkills.setText(job.getSkills().toString());
        initTags(job);
    }

    /**
     * Creates colored tags for each tag assigned to the {@code Job}.
     * @param job is the job for which the tags are being populated.
     */
    private void initTags(Job job) {
        job.getTags().forEach(tag -> {
            Label label = new Label(tag.tagName);
            label.getStyleClass().add(getTagColorFor(tag.tagName));
            tags.getChildren().add(label);
        });
    }

    /**
     * Chooses a color for the tag based on hashcode
     * @param tagName for which color is chosen.
     * @return string containing the color name.
     */
    private String getTagColorFor(String tagName) {
        //some explanation
        return TAG_COLORS[Math.abs(tagName.hashCode()) % TAG_COLORS.length];
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
        JobCard card = (JobCard) other;
        return id.getText().equals(card.id.getText())
                && job.equals(card.job);
    }
}

//@@author
