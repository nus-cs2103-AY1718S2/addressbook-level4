package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private ListView<Label> keys;

    @FXML
    private ListView<Label> values;

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
        name.setText(person.getName().fullName);

        List<Label> keysList = new ArrayList<>();
        List<Label> valuesList = new ArrayList<>();

        Label fullNameKey = new Label("Full Name" + ":");
        Label fullNameValue = new Label(person.getName().fullName);
        fullNameKey.getStyleClass().add("details-key");
        fullNameValue.getStyleClass().add("details-value");

        Label phoneKey = new Label("Phone" + ":");
        Label phoneValue = new Label(person.getPhone().value);
        phoneKey.getStyleClass().add("details-key");
        phoneValue.getStyleClass().add("details-value");

        Label emailKey = new Label("Email" + ":");
        Label emailValue = new Label(person.getEmail().value);
        emailKey.getStyleClass().add("details-key");
        emailValue.getStyleClass().add("details-value");

        Label addressKey = new Label("Address" + ":");
        Label addressValue = new Label(person.getAddress().value);
        addressKey.getStyleClass().add("details-key");
        addressValue.getStyleClass().add("details-value");

        keysList.add(fullNameKey);
        keysList.add(phoneKey);
        keysList.add(emailKey);
        keysList.add(addressKey);
        valuesList.add(fullNameValue);
        valuesList.add(phoneValue);
        valuesList.add(emailValue);
        valuesList.add(addressValue);

        keys.setItems(FXCollections.observableList(keysList));
        values.setItems(FXCollections.observableList(valuesList));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }
}
