//@@author kush1509
package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EditPersonEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * Shows the contact details of the person in a separate and formatted page
 */
public class ContactDetailsDisplay extends UiPart<Region> {

    private static final String FXML = "ContactDetailsDisplay.fxml";
    private static final String DEFAULT_IMAGE = "/images/default.png";
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

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     *Shows the contact details of the person
     */
    private void showPersonDetails(Person person) {

        name.setWrapText(true);
        name.setText(person.getName().fullName);
        if (person.getProfilePicture().filePath != null) {
            imageView.setImage(person.getProfilePicture().getImage());
        } else {
            imageView.setImage(getImage(DEFAULT_IMAGE));
        }
        List<Label> keysList = new ArrayList<>();
        List<Label> valuesList = new ArrayList<>();

        addPropertyToList("Full Name", person.getName().fullName, keysList, valuesList);
        addPropertyToList("Phone", person.getPhone().value, keysList, valuesList);
        addPropertyToList("Email", person.getEmail().value, keysList, valuesList);
        addPropertyToList("Address", person.getAddress().value, keysList, valuesList);

        keys.setItems(FXCollections.observableList(keysList));
        values.setItems(FXCollections.observableList(valuesList));
        System.out.println(values);
    }

    /**
     * Adds the label for key and value to the respective list
     */
    private void addPropertyToList(String key, String value, List<Label> keysList, List<Label> valuesList) {
        Label keyLabel = new Label(key + ":");
        Label valueLabel = new Label(value);
        keyLabel.getStyleClass().add("details-key");
        valueLabel.getStyleClass().add("details-value");
        keyLabel.setWrapText(true);
        valueLabel.setWrapText(true);

        keysList.add(keyLabel);
        valuesList.add(valueLabel);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.getNewSelection().person);
    }

    @Subscribe
    private void handleEditPersonEvent(EditPersonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPersonDetails(event.person);
    }
}
