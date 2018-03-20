package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

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
    private AnchorPane cardPersonPane;

    @FXML
    private Label cardPersonName;
    @FXML
    private Label cardPersonUniversity;
    @FXML
    private Label cardPersonEmail;
    @FXML
    private Label cardPersonContact;
    @FXML
    private Label cardPersonRating;
    @FXML
    private Label cardPersonStatus;
    @FXML
    private Label cardPersonNumber;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        cardPersonName.setText(person.getName().fullName);
        cardPersonUniversity.setText("WIP");
        cardPersonEmail.setText(person.getEmail().value);
        cardPersonContact.setText(person.getPhone().value);
        cardPersonRating.setText("WIP");
        cardPersonStatus.setText("WIP");
        cardPersonNumber.setText(String.valueOf(displayedIndex));
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
        return cardPersonNumber.getText().equals(card.cardPersonNumber.getText())
                && person.equals(card.person);
    }
}
