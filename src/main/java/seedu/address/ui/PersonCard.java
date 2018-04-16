package seedu.address.ui;

import java.util.Iterator;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Cca;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOR_STYLES = new String[]{"teal", "red", "yellow", "blue", "orange", "brown",
                                                                  "green", "pink", "black", "purple"};

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
    private Label birthday;
    @FXML
    private Label levelOfFriendship;
    @FXML
    private Label unitNumber;
    @FXML
    private Label ccas;
    @FXML
    private Label meetDate;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        birthday.setText(person.getBirthday().value);
        levelOfFriendship.setText(changeLevelOfFriendshipToHeart(person.getLevelOfFriendship().value));
        unitNumber.setText(person.getUnitNumber().value);
        ccas.setText(getCcasInString(person.getCcas()));
        meetDate.setText("Meet Date: " + person.getMeetDate().value);
        initTags(person);
    }

    //@@author deborahlow97
    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between diffe 11rent runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    private String getCcasInString(Set<Cca> ccas) {
        String ccasValue = "";
        Iterator iterator = ccas.iterator();
        while (iterator.hasNext()) {
            ccasValue = ccasValue + iterator.next().toString() + " ";
        }
        return ccasValue;
    }

    /**
     * Takes in @param value representing the level of friendship value
     * @return a number of hearts string.
     */
    public static String changeLevelOfFriendshipToHeart(String value) {
        int intValue = Integer.parseInt(value);
        String heartString = "";
        for (int i = 0; i < intValue; i++) {
            heartString = heartString + '\u2665' + " ";
        }
        return heartString;
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
