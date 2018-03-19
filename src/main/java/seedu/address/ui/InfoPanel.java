package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InfoPanelChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The Info Panel of the App.
 */
public class InfoPanel extends UiPart<Region> {

    public static final Person DEFAULT_PERSON = null;
    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label infoPanel;

    public InfoPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person person = event.getNewSelection().person;
        infoPanel.setText(person.getName().toString());
        infoPanel.setUserData(person);
        infoPanel.fireEvent(new InfoPanelChangedEvent());
    }
}
