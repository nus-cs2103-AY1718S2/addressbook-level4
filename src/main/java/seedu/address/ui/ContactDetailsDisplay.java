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

    private List<Label> keysList = new ArrayList<>();
    private List<Label> valuesList = new ArrayList<>();

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
        
        addPropertyToList("Full Name", person.getName().fullName);
        addPropertyToList("Phone", person.getPhone().value);
        addPropertyToList("Email", person.getEmail().value);
        addPropertyToList("Address", person.getAddress().value);

        keys.setItems(FXCollections.observableList(keysList));
        values.setItems(FXCollections.observableList(valuesList));
    }
    
    /**
     * Adds the label for key and value to the respective list
     */
    private void addPropertyToList(String key, String value) {
        Label keyLabel = new Label(key + ":");
        Label valueLabel = new Label(value);
        keyLabel.getStyleClass().add("details-key");
        valueLabel.getStyleClass().add("details-value");
        
        keysList.add(keyLabel);
        valuesList.add(valueLabel);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }
}
