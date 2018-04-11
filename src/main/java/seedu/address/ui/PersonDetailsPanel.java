//@@author jaronchan
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;
import seedu.address.commons.events.ui.ShowUpdatedSessionLogEvent;
import seedu.address.model.person.Person;

/**
 * The UI component that handles the display of beneficiary details, location on map
 * and session reports.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapPanel mapPanel;

    @FXML
    private StackPane mapPanelPlaceholder;

    @FXML
    private TextArea sessionLogPanel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Label priorityLabel;

    public PersonDetailsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        showSelectedPersonDetails(null);
        loadMapPanel();
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showSelectedPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            nameLabel.setText(person.getName().toString());
            phoneNumberLabel.setText(person.getPhone().toString());
            emailLabel.setText(person.getEmail().toString());
            addressLabel.setText(person.getAddress().toString());
            conditionLabel.setText("TO BE IMPLEMENTED IN 2.0");
            priorityLabel.setText("TO BE IMPLEMENTED IN 2.0");
            String sessionLogToDisplay = person.getSessionLogs().toString();
            loadSessionLogs(sessionLogToDisplay);
        } else {
            // Person is null, remove all the text.
            nameLabel.setText("");
            phoneNumberLabel.setText("");
            emailLabel.setText("");
            addressLabel.setText("");
            conditionLabel.setText("");
            priorityLabel.setText("");
            sessionLogPanel.setText("Select a Person to display content.");
        }
    }

    /**
     * Loads a map to the allocated stack pane.
     */
    public void loadMapPanel() {
        if (mapPanel == null) {
            mapPanel = new MapPanel("MapPanel.fxml");
            mapPanelPlaceholder.getChildren().add(mapPanel.getRoot());
        }
    }

    /**
     * Removes a map from the allocated stack pane.
     */
    public void removeMapPanel() {

        if (mapPanel != null && mapPanelPlaceholder.getChildren().contains(mapPanel.getRoot())) {
            mapPanel.resetMap();
            mapPanelPlaceholder.getChildren().remove(mapPanel.getRoot());
            mapPanel = null;
        }
    }

    /**
     * Loads the session logs stored to {@code Person}.
     */
    private void loadSessionLogs(String sessionLogToDisplay) {
        if (sessionLogToDisplay.equals("")) {
            sessionLogPanel.setText("No session logs has been added to this person yet!");
        } else {
            sessionLogPanel.setText(sessionLogToDisplay);
        }
    }



    /**
     * Frees resources allocated to the map panel if map panel is not empty.
     */
    public void freeResources() {
        if (mapPanel != null && mapPanelPlaceholder.getChildren().contains(mapPanel.getRoot())) {
            mapPanel.freeResources();
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (mapPanel != null && mapPanelPlaceholder.getChildren().contains(mapPanel.getRoot())) {
            mapPanel.loadAddress(event.getNewSelection().person.getAddress().toString());
        }
        showSelectedPersonDetails(event.getNewSelection().person);
    }

    @Subscribe
    private void handleShowUpdatedSessionLogEvent(ShowUpdatedSessionLogEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadSessionLogs(event.getTargetPerson().getSessionLogs().toString());
    }

    @Subscribe
    private void handleShowInvalidAddressOverlayEvent(ShowInvalidAddressOverlayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mapPanel.showInvalidAddressOverlay(event.getAddressValidity());
    }

    @Subscribe
    private void handleLoadMapPanelEvent(LoadMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getFeatureTarget().equals("details")) {
            loadMapPanel();
        }
    }

    @Subscribe
    private void handleRemoveMapPanelEvent(RemoveMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!event.getFeatureTarget().equals("details")) {
            removeMapPanel();
        }
    }
}
