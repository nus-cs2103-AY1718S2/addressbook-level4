package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOR_STYLES =
        { "yellow", "blue", "red", "green", "orange", "purple", "grey"};
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
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private Label appointment;
    @FXML
    private Label insurance;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        birthday.setText(person.getBirthday().value);
        if (person.getAppointment() == null || person.getAppointment().equals("")) {
            appointment.setText("No Appointment Date");
        } else {
            appointment.setText(person.getAppointment().value);
        }
        if (person.getInsurance() == null) {
            insurance.setText("Potential Client");
        }
        else {
            insurance.setText(person.getInsurance().insuranceName);
        }
        startTag(person);
    }

    private String getTagColorStyleFor(String tag) {

        if (tag.equals("Life")) {
            return TAG_COLOR_STYLES[0]; //yellow
        }

        if (tag.equals("Saving")) {
            return TAG_COLOR_STYLES[1]; //blue
        }

        if (tag.equals("Health")) {
            return TAG_COLOR_STYLES[2]; //red
        }

        if (tag.equals("General")) {
            return TAG_COLOR_STYLES[4]; //orange
        }

        switch(tag) {
        case "friends":
        case "friend":
            return TAG_COLOR_STYLES[0];

        case "teacher":
        case "classmates":
            return TAG_COLOR_STYLES[1];

        case "family":
        case "husband":
            return TAG_COLOR_STYLES[3];

        case "enemy":
        case "owesMoney":
            return TAG_COLOR_STYLES[2];

        case "boyfriend":
        case "girlfriend":
            return TAG_COLOR_STYLES[5];

        case "grandparent":
        case "neighbours":
            return TAG_COLOR_STYLES[6];

        case "colleagues":
            return TAG_COLOR_STYLES[4];

        default:
            return "";
        }
    }


    /**
     * Creates the tag labels for {@code person}.
     */
    private void startTag(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
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
