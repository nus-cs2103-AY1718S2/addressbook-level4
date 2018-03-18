package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * Shows the contact details of the person in a separate and formatted page
 */
public class ContactDetailsDisplay extends UiPart<Region> {

    private static final String FXML = "ContactDetailsDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactDetailsDisplay.class);

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;

    public ContactDetailsDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     *Shows the contact details of the person
     */
    private void showPersonDetails(Person person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }
}
