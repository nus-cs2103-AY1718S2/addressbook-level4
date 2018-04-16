package seedu.address.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.commons.events.ui.PersonDetailsChangedEvent;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
/** @@author Codee */
public class PlayerDetails extends UiPart<Region> {

    private static final String FXML = "PlayerDetails.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private Person personBeforeChange;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label jerseyNumber;
    @FXML
    private Label remark;

    //@@author lohtianwei
    public PlayerDetails(Person person) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.person = person;
        name.setText(person.getName().fullName);
        jerseyNumber.setText("Jersey Number: " + person.getJerseyNumber().value);

        if (person.getPhone().isPrivate()) {
            phone.setText(person.getPhone().toString());
        } else {
            phone.setText(person.getPhone().value);
        }

        if (person.getAddress().isPrivate()) {
            address.setText(person.getAddress().toString());
        } else {
            address.setText(person.getAddress().value);
        }

        if (person.getEmail().isPrivate()) {
            email.setText(person.getEmail().toString());
        } else {
            email.setText(person.getEmail().value);
        }

        if (person.getRemark().isPrivate()) {
            remark.setText("Remarks: " + person.getRemark().toString());
        } else {
            remark.setText("Remarks: " + person.getRemark().value);
        }
        personBeforeChange = this.person;
    }

    //@@author Codee
    @Subscribe
    private void handlePersonDetailsChangedEvent(PersonDetailsChangedEvent event) {
        if (event.getPerson().getName().fullName.equals(person.getName().fullName)) {
            name.setText((event.getPerson().getName().toString()));
            phone.setText(event.getPerson().getPhone().toString());
            jerseyNumber.setText("Jersey Number: " + event.getPerson().getJerseyNumber().toString());
            address.setText(event.getPerson().getAddress().toString());
            email.setText(event.getPerson().getEmail().toString());
            remark.setText("Remarks: " + event.getPerson().getRemark().toString());
        }
    }
}

