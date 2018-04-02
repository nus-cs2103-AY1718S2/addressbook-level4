package seedu.progresschecker.ui;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import seedu.progresschecker.MainApp;
import seedu.progresschecker.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLORS =
        { "red", "orange", "yellow", "green", "blue", "purple" };
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
    private Label phone;
    @FXML
    private Label major;
    @FXML
    private Label year;
    @FXML
    private Label email;
    @FXML
    private Label username;
    @FXML
    private FlowPane tags;
    @FXML
    private Rectangle profile;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        major.setText(person.getMajor().value);
        year.setText(person.getYear().value);
        email.setText(person.getEmail().value);
        username.setText(person.getUsername().username);
        person.getTags().forEach(tag -> {
            Label label = new Label(tag.tagName);
            label.getStyleClass().add(getTagColor(tag.tagName));
            tags.getChildren().add(label);
        });
        loadPhoto();
    }

    /**
     * Get a deterministic tag color based off tag's name value.
     */
    private String getTagColor(String tagName) {
        int index = getValueOfString(tagName) % TAG_COLORS.length;
        return TAG_COLORS[index];
    }

    /**
     * Loads profile photo
     */
    private void loadPhoto() {
        String photoPath = person.getPhotoPath();
        Image profilePhoto;
        if (photoPath.contains("contact")) {
            File photo = new File(photoPath);
            if (photo.exists() && !photo.isDirectory()) {
                String url = photo.toURI().toString();
                profilePhoto = new Image(url);
                profile.setFill(new ImagePattern(profilePhoto));
            }
        } else {
            profilePhoto = new Image(
                    MainApp.class.getResourceAsStream(person.getDefaultPath()));
            profile.setFill(new ImagePattern(profilePhoto));
        }
    }

    /**
     * Adds each letter of given string into an integer.
     */
    private int getValueOfString(String tagName) {
        int sum = 0;
        for (char c : tagName.toCharArray()) {
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
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
