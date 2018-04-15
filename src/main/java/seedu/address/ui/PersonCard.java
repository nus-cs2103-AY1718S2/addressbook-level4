package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double ICON_WIDTH = 25;
    private static final double ICON_HEIGHT = 25;
    private static final String[] TAG_COLOR_STYLES =
        { "teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "indigo" };

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
    private Label address;

    @FXML
    private Label email;

    @FXML
    private FlowPane groups;

    @FXML
    private FlowPane preferences;

    @FXML
    private ImageView phoneIcon;

    @FXML
    private ImageView addressIcon;

    @FXML
    private ImageView emailIcon;

    @FXML
    private ImageView groupIcon;

    @FXML
    private ImageView prefIcon;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        initGroupTags(person);
        initPreferenceTags(person);
        setImageSizeForAllImages();
    }

    //@@author amad-person
    private void setImageSizeForAllImages() {
        phoneIcon.setFitWidth(ICON_WIDTH);
        phoneIcon.setFitHeight(ICON_HEIGHT);

        addressIcon.setFitWidth(ICON_WIDTH);
        addressIcon.setFitHeight(ICON_HEIGHT);

        emailIcon.setFitWidth(ICON_WIDTH);
        emailIcon.setFitHeight(ICON_HEIGHT);

        groupIcon.setFitWidth(ICON_WIDTH);
        groupIcon.setFitHeight(ICON_HEIGHT);

        prefIcon.setFitWidth(ICON_WIDTH);
        prefIcon.setFitHeight(ICON_HEIGHT);
    }

    //@@author AJZ1995
    /**
     * Returns the color style for {@code tagName}'s label.
     */
    public static String getPrefTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    public static String getGroupTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the group tag labels for {@code person}.
     */
    private void initGroupTags(Person person) {
        person.getGroupTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getGroupTagColorStyleFor(tag.tagName));
            groups.getChildren().add(tagLabel);
        });
    }

    /**
     * Creates the preference tag labels for {@code person}.
     */
    private void initPreferenceTags(Person person) {
        person.getPreferenceTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getPrefTagColorStyleFor(tag.tagName));
            preferences.getChildren().add(tagLabel);
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
