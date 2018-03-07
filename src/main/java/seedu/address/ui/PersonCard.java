package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOR = {"red", "green", "blue"};

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
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        labelTag(person);
    }

    /**
     * Set the label for the tag
     * @param p
     */
    private void labelTag(Person p) {

        p.getTags().forEach(tag -> {
            Label showLabel = new Label(tag.tagName);
            showLabel.getStyleClass().add(fromTagNameToColor(tag.tagName));
            //Zoom effect on the label when the mouse is on the label
            labelZoomEffect(showLabel);
            tags.getChildren().add(showLabel);
        });
    }

    /**
     * Zoom effect on the label when the mouse is on the label
     * @param label
     */
    private void labelZoomEffect(Label label) {
        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setScaleX(1.5);
                label.setScaleY(1.5);
            }
        });

        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setScaleX(1);
                label.setScaleY(1);
            }
        });
    }

    /**
     *
     * @param tagName
     * @return the color for the label.
     */
    private String fromTagNameToColor(String tagName) {
        // use hashCode to hash the tagName into an integer
        // this help keeping the label colors consistency through every run
        return TAG_COLOR[Math.abs(tagName.hashCode()) % TAG_COLOR.length];
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
