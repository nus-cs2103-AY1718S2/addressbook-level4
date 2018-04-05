package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.model.person.Person;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    //@@author KevinCJH
    private static final String[] SKILL_COLOR_STYLES =
        { "teal", "red", "green", "blue", "orange", "brown",
            "yellow", "pink", "lightgreen", "grey", "purple" };
    private static final String DEFAULT_IMAGE = "/images/default.png";
    //@@author
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label currentPosition;
    @FXML
    private Label company;
    @FXML
    private FlowPane skills;
    @FXML
    private ImageView imageView;

    //@@author kush1509
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        currentPosition.setText(person.getCurrentPosition().value);
        company.setText(person.getCompany().value);
        if (person.getProfilePicture().filePath != null) {
            imageView.setImage(person.getProfilePicture().getImage());
        } else {
            imageView.setImage(getImage(DEFAULT_IMAGE));
        }
        //@@author KevinCJH
        initSkills(person);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    //@@author KevinCJHc
    /**
     * Returns the color style for {@code skillName}'s label.
     */
    private String getSkillColorStyleFor(String skillName) {
        // we use the hash code of the skill name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between skills.
        return SKILL_COLOR_STYLES[Math.abs(skillName.hashCode()) % SKILL_COLOR_STYLES.length];
    }

    //@@author
    /**
     * Creates the skill labels for {@code person}.
     */
    private void initSkills(Person person) {
        person.getSkills().forEach(skill -> {
            Label skillLabel = new Label(skill.skillName);
            skillLabel.getStyleClass().add(getSkillColorStyleFor(skill.skillName));
            skills.getChildren().add(skillLabel);
        });
    }
    //@@author
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
