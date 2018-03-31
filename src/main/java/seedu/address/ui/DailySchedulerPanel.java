//@@author jaronchan
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoadMapPanelEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RemoveMapPanelEvent;
import seedu.address.commons.events.ui.SwitchFeatureEvent;

/**
 * The UI component that handles the display of daily schedules and directions between locations.
 */
public class DailySchedulerPanel extends UiPart<Region> {

    private static final String FXML = "DailySchedulerPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private MapPanel directionPanel;

    @FXML
    private StackPane directionPanelPlaceholder;



    public DailySchedulerPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
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
}
