package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class ProfileDisplay extends UiPart<Region> {

    private static final String FXML = "ProfileDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(ProfileDisplay.class);
    
    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;

    public ProfileDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);
    }
    
    private void loadPersonProfile(Person person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonProfile(event.getNewSelection().person);
    }

}