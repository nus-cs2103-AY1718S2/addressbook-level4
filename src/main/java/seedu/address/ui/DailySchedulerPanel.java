//@@author jaronchan
package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.services.calendar.model.Event;
import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DailyScheduleShownChangedEvent;
import seedu.address.commons.events.ui.LoadDirectionsEvent;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.ResetDirectionsEvent;
import seedu.address.commons.events.ui.UpdateNumberOfButtonsEvent;
import seedu.address.logic.commands.ShowScheduleCommand;

/**
 * The UI component that handles the display of daily schedules and directions between locations.
 */
public class DailySchedulerPanel extends UiPart<Region> {

    private static final String FXML = "DailySchedulerPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapPanel directionPanel;

    @FXML
    private VBox eventsListStack;

    @FXML
    private StackPane directionPanelPlaceholder;

    @FXML
    private VBox buttonStack;

    @FXML
    private ToggleButton button;

    public DailySchedulerPanel() {
        super(FXML);
        showPlannedEvents(new ArrayList<>());
        registerAsAnEventHandler(this);
    }

    /**
     * Fills schedule panel to with scheduled events for the specified date.
     * If the day has no events, a placeholder text will be shown.
     *
     */
    private void showPlannedEvents(List<Event> dailyEventsList) {

        int numOfEvents = dailyEventsList.size();
        if (numOfEvents != 0) {
            for (int i = 0; i < numOfEvents; i++) {
                ScheduledEventCard card = new ScheduledEventCard(dailyEventsList.get(i), i + 1);
                eventsListStack.getChildren().add(card.getRoot());
            }
        } else {
            Label emptyListLabel = new Label("No events listed.\n"
                    + "You must first load events for a particular date\n"
                    + "with existing events.\n"
                    + "To load scheduled events,\n"
                    + "execute show-schedule command.\n\n"
                    + ShowScheduleCommand.MESSAGE_USAGE + "\n");
            emptyListLabel.setWrapText(true);
            eventsListStack.getChildren().add(emptyListLabel);
        }
    }

    /**
     * Resets schedule panel.
     */
    private void removePlannedEvents() {
        eventsListStack.getChildren().clear();
    }

    /**
     * Buttons depending on how many trips to be made.
     */
    public void addButtons(int numOfInstances) {
        for (int i = 1; i <= numOfInstances; i++) {
            buttonStack.getChildren().add(new ToggleButton(Integer.toString(i)));
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
     * Resets the directions on the map.
     */
    public void resetDirections() {
        if (directionPanel != null && directionPanelPlaceholder.getChildren().contains(directionPanel.getRoot())) {
            directionPanel.resetDirections();
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
    private void handleDailyScheduleShownChangedEvent(DailyScheduleShownChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        removePlannedEvents();
        showPlannedEvents(event.getDailyEventsList());

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

    @Subscribe
    private void handleResetDirectionsEvent(ResetDirectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetDirections();
    }

    @Subscribe
    private void handleUpdateNumberOfButtonsEvent(UpdateNumberOfButtonsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        removeButtons();
        addButtons(event.getNumOfInstances());
    }
}
