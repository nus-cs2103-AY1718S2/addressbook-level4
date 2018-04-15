package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The Details Panel of STUtor that shows a person's details.
 */

//@@author sherlynng
public class DetailsPanel extends UiPart<Region> {

    private static final String FXML = "DetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GridPane grid;
    @FXML
    private HBox ratingBox;
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
    @FXML
    private Label remark;
    @FXML
    private Label rating;
    @FXML
    private Label rateCount;

    public DetailsPanel() {
        super(FXML);

        name.setText("");
        grid.setVisible(false);
        ratingBox.setVisible(false);

        registerAsAnEventHandler(this);
    }

    /**
     * Loads a {@code person}'s details into the details panel.
     */
    public void loadPersonDetails(Person person) {
        grid.setVisible(true);
        ratingBox.setVisible(true);

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        status.setText(person.getStatus().value);
        subject.setText(person.getSubject().value);
        level.setText(person.getLevel().value);
        price.setText("$" + person.getPrice().value + " / hr");
        role.setText(person.getRole().value);
        remark.setText(person.getRemark().value);

        if (person.getRate().getCount() == 0) {
            rating.setText("-");
        } else {
            rating.setText(Double.toString(person.getRate().getDisplayedValue()));
        }
        rateCount.setText(Integer.toString(person.getRate().getCount()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
