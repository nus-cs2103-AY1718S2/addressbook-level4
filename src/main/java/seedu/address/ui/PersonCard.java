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

    private static final String[] TAGS_COLOUR_STYLES = {"red" , "blue" , "green" , "yellow" , "orange"};

    private static final String DUMMY_GRADE_TEXT = "-";
    private static final String DUMMY_LOCALE_TEXT = "-";
    private static final String[] DUMMY_SUBJECTS_TEXT = {"-"};

    private static final String URGENT_PATTERN_REGEX = "Urgent";

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
    private Label grade;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label locale;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane subjects;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        grade.setText(DUMMY_GRADE_TEXT);
        phone.setText(person.getPhone().value);
        locale.setText(DUMMY_LOCALE_TEXT);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        intTags(person);
        intSubjects(person);
    }

    /**
     *@author olimhc-reused
     *Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits with minor modification
     * Initialises tags
     * @param person
     */
    private void intTags(Person person) {
        person.getTags().forEach(tag -> {
            Label newLabel = new Label(tag.tagName);
            newLabel.getStyleClass().add(getColour(tag.tagName));
            tags.getChildren().add(newLabel);
        });
    }

    /**
     * Initialises subjects
     * @param person
     */
    private void intSubjects(Person person) {
        for (String s : DUMMY_SUBJECTS_TEXT) {
            Label newLabel = new Label(s);
            subjects.getChildren().add(newLabel);
        }
    }

    /**
     * @param name
     * @return String colour
     */
    private String getColour(String name) {
        if (name.equalsIgnoreCase(URGENT_PATTERN_REGEX)) {
            return TAGS_COLOUR_STYLES[0];
        } else {
            return TAGS_COLOUR_STYLES[(Math.abs(name.hashCode() % TAGS_COLOUR_STYLES.length))];
        }
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
