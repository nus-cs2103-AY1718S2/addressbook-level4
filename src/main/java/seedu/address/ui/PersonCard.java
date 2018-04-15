package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    public static final String[] TAG_COLOR_STYLES =
        {"orange", "red", "yellow", "blue", "grey", "brown", "green", "pink", "black", "purple"};

    private static final String FXML = "PersonListCard.fxml";


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
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        initTags(person);
    }

    //@@author aussiroth
    /**
     * Returns the color style for {@code tag}.
     * Uses the tagType value inside the Tag to determine the colour.
     */
    private String getTagColorStyleFor(Tag tag) {
        switch (tag.tagType) {
        case SUBJECT:
            return TAG_COLOR_STYLES[0]; //subject is teal
        case LEVEL:
            return TAG_COLOR_STYLES[1]; //level is red
        case STATUS:
            return TAG_COLOR_STYLES[2]; //status is yellow
        case PRICE:
            return TAG_COLOR_STYLES[3]; //price is blue
        //fall through to default
        default:
            return TAG_COLOR_STYLES[8]; //all non-attribute are black
        }
    }

    //@@author dannyngmx94
    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag));
            tags.getChildren().add(tagLabel);
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
