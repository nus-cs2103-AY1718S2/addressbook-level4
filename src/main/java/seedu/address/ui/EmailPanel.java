package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.web.HTMLEditor;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * Shows the draft email page
 */
public class EmailPanel extends UiPart<Region> {

    private static final String FXML = "EmailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EmailPanel.class);
    private String recipient;

    @FXML
    private TextField to;

    @FXML
    private TextField subject;

    @FXML
    private HTMLEditor body;

    @FXML
    private Button send;

    public EmailPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     *Shows the recipient of this email draft
     */
    private void showRecipient(Person person) {
        to.setText(person.getEmail().value);
        body.setHtmlText("Dear " + person.getName().fullName + ",");

    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showRecipient(event.getNewSelection().person);
    }
}
