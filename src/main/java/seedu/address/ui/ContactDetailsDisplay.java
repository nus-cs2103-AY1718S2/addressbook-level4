package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    @FXML
    private ImageView imageView;

    public ContactDetailsDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     *Shows the contact details of the person
     */
    private void showPersonDetails(Person person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        //imageView.imageProperty().bind(person.imageProperty());
        imageView.setImage(person.getProfilePicture().getImage());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }
}
