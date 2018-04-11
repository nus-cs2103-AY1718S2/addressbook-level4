package seedu.progresschecker.ui;

import java.io.File;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import seedu.progresschecker.MainApp;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.progresschecker.model.person.Person;

//@@author Livian1107

/**
 * Panel contains the information of person
 */
public class ProfilePanel extends UiPart<Region>  {

    private static final String FXML = "ProfilePanel.fxml";
    private static String DEFAULT_PHOTO = "/images/profile_photo.jpg";

    private static final String[] TAG_COLORS = { "red", "orange", "yellow", "green", "blue", "purple" };
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Person person;
    private Person currentlyViewedPerson;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label name;
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
    private Ellipse profile;

    public ProfilePanel() {
        super(FXML);
        this.person = null;
        loadDefaultPerson();
        registerAsAnEventHandler(this);

    }

    /**
     * Get a deterministic tag color based off tag's name value.
     */
    private String getTagColor(String tagName) {
        int index = getValueOfString(tagName) % TAG_COLORS.length;
        return TAG_COLORS[index];
    }

    /**
     * Loads the default person
     */
    private void loadDefaultPerson() {
        name.setText("Person X");
        phone.setText("");
        username.setText("");
        email.setText("");
        year.setText("");
        major.setText("");
        tags.getChildren().clear();

        setDefaultInfoPhoto();
        currentlyViewedPerson = null;
        logger.info("Currently Viewing: Default Person");
    }

    /**
     * Loads the info of the selected person
     */
    private void loadPerson(Person person) {
        this.person = person;
        tags.getChildren().clear();
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

        currentlyViewedPerson = person;
        logger.info("Currently Viewing: " + currentlyViewedPerson.getName());
    }

    /**
     * Sets the default info photo.
     */
    public void setDefaultInfoPhoto() {
        Image defaultImage = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO));
        profile.setFill(new ImagePattern(defaultImage));
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

    @Subscribe
    private void handlePersonPanelSelectionChangeEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.getNewSelection().person);
    }
}
