//@@author kush1509
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.job.Job;


/**
 * A UI component that displays information of a {@code Job}.
 */
public class JobCard extends UiPart<Region> {

    private static final String FXML = "JobListCard.fxml";

    private static final String[] SKILL_COLOR_STYLES =
        { "teal", "red", "green", "blue", "orange", "brown",
            "yellow", "pink", "lightgreen", "grey", "purple" };

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
    private Label position;
    @FXML
    private Label id;
    @FXML
    private Label team;
    @FXML
    private Label jobLocation;
    @FXML
    private Label numberOfPositions;
    @FXML
    private FlowPane skills;

    public JobCard(Job job, int displayedIndex) {
        super(FXML);
        this.job = job;
        id.setText(displayedIndex + ". ");
        position.setText(job.getPosition().value);
        team.setText(job.getTeam().value);
        jobLocation.setText(job.getLocation().value);
        numberOfPositions.setText("Positions: " + job.getNumberOfPositions().value);
        initSkills(job);
    }

    /**
     * Creates the skill labels for {@code job}.
     */
    private void initSkills(Job job) {
        job.getSkills().forEach(skill -> {
            Label skillLabel = new Label(skill.skillName);
            skillLabel.getStyleClass().add(getSkillColorStyleFor(skill.skillName));
            skills.getChildren().add(skillLabel);
        });
    }

    /**
     * Returns the color style for {@code skillName}'s label.
     */
    private String getSkillColorStyleFor(String skillName) {
        // we use the hash code of the skill name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between skills.
        return SKILL_COLOR_STYLES[Math.abs(skillName.hashCode()) % SKILL_COLOR_STYLES.length];
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
