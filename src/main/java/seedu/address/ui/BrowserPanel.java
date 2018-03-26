package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GridPane grid;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label role;
    @FXML
    private Label status;
    @FXML
    private Label subject;
    @FXML
    private Label level;
    @FXML
    private Label price;

    public BrowserPanel() {
        super(FXML);

        name.setText("");
        grid.setVisible(false);

        registerAsAnEventHandler(this);
    }

    /**
     * Loads a {@code person}'s details into the browser panel.
     */
    private void loadPersonDetails(Person person) {
        grid.setVisible(true);

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        status.setText(person.getStatus().value);
        subject.setText(person.getSubject().value);
        level.setText(person.getLevel().value);
        price.setText("$" + person.getPrice().value + " / hr");
        if (person.getTags().contains(new Tag("Student"))
                || person.getTags().contains(new Tag("student"))) {
            role.setText("Student");
        } else if (person.getTags().contains(new Tag("Tutor"))
                || person.getTags().contains(new Tag("tutor"))) {
            role.setText("Tutor");
        }
        /*if (person.getPhone().value == null) {
            phone.setText(" - ");
        } else {
            phone.setText(person.getPhone().value);
        }
        if (person.getAddress().value == null) {
            address.setText(" - ");
        } else {
            address.setText(person.getAddress().value);
        }
        if (person.getEmail().value == null) {
            email.setText(" - ");
        } else {
            email.setText(person.getEmail().value);
        }
        if (person.getStatus().value == null) {
            status.setText(" - ");
        } else {
            status.setText(person.getStatus().value);
        }
        if (person.getSubject().value == null) {
            subject.setText(" - ");
        } else {
            subject.setText(person.getSubject().value);
        }
        if (person.getLevel().value == null) {
            level.setText(" - ");
        } else {
            level.setText(person.getLevel().value);
        }
        if (person.getPrice().value == null) {
            price.setText(" - ");
        } else {
            price.setText("$" + person.getPrice().value + " / hr");
        }
        if (person.getTags().contains(new Tag("Student"))
            || person.getTags().contains(new Tag("student"))) {
            role.setText("Student");
        } else if (person.getTags().contains(new Tag("Tutor"))
                   || person.getTags().contains(new Tag("tutor"))) {
            role.setText("Tutor");
        } else {
            role.setText(" - ");
        }*/
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
