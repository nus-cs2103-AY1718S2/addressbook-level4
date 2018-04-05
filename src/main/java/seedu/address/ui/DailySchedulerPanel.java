//@@author jaronchan
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadDirectionsEvent;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RemoveDirectionsEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.UpdateNumberOfButtonsEvent;

/**
 * The UI component that handles the display of daily schedules and directions between locations.
 */
public class DailySchedulerPanel extends UiPart<Region> {

    private static final String FXML = "DailySchedulerPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapPanel directionPanel;

    @FXML
    private StackPane directionPanelPlaceholder;

    @FXML
    private VBox buttonStack;

    @FXML
    private ToggleButton button;

    public DailySchedulerPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Buttons depending on how many trips to be made.
     */
    public void addButtons(int numOfInstances) {
        for (int i = 0; i < numOfInstances; i++) {
            buttonStack.getChildren().add(new ToggleButton(" "));
        }
    }
    /**
     * Removes existing buttons.
     */
    public void removeButtons() {
        buttonStack.getChildren().clear();
    }

    /**
     * Loads a map with directional information to the allocated stack pane.
     */
    public void loadDirectionPanel() {
        if (directionPanel == null) {
            directionPanel = new MapPanel("MapPanel.fxml");
            directionPanelPlaceholder.getChildren().add(directionPanel.getRoot());
        }
    }

    /**
     * Removes the map with directional information.
     */
    public void removeDirectionPanel() {

        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.resetMap();
            directionPanelPlaceholder.getChildren().remove(directionPanel.getRoot());
            directionPanel = null;
        }
    }

    /**
     * Updates the directions on the map.
     */
    public void updateDirections(String addressOrigin, String addressDestination) {
        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.loadDirections(addressOrigin, addressDestination);
        }
    }

    /**
     * Frees resources allocated to the direction panel if direction panel is not empty.
     */
    public void freeResources() {
        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.freeResources();
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    private void handleLoadMapPanelEvent(LoadMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getFeatureTarget().equals("scheduler")) {
            loadDirectionPanel();
        }
    }

    @Subscribe
    private void handleRemoveMapPanelEvent(RemoveMapPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!event.getFeatureTarget().equals("scheduler")) {
            removeDirectionPanel();
        }
    }

    @Subscribe
    private void handleLoadDirectionsEvent(LoadDirectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateDirections(event.getAddressOrigin(), event.getGetAddressDestination());
    }

//    @Subscribe
//    private void handleRemoveDirectionsEvent(RemoveDirectionsEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
//
//        }
//    }
    @Subscribe
    private void handleUpdateNumberOfButtonsEvent(UpdateNumberOfButtonsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        removeButtons();
        addButtons(event.getNumOfInstances());
    }
}
