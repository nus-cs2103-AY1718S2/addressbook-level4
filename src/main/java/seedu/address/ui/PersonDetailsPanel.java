//@@author jaronchan
package seedu.address.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;
import seedu.address.logic.MapManager;
import seedu.address.model.person.Person;

/**
 * The Person Details Panel of the App.
 * To be UPDATED
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final MapPanel mapPanel;
    @FXML
    private StackPane mapPanelPlaceHolder;


    public PersonDetailsPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.

        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        mapPanel = new MapPanel("MapPanel.fxml");
        mapPanelPlaceHolder.getChildren().add(mapPanel.getRoot());
    }

    public void removeMapPanel() {

        if (mapPanel != null && mapPanelPlaceHolder.getChildren().contains(mapPanel.getRoot())) {
            mapPanel.resetMap();
            mapPanelPlaceHolder.getChildren().remove(mapPanel.getRoot());
        }
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        mapPanel.freeResources();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mapPanel.loadAddress(event.getNewSelection().person.getAddress().toString());
    }

    @Subscribe
    private void handleShowInvalidAddressOverlayEvent(ShowInvalidAddressOverlayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mapPanel.showInvalidAddressOverlay(event.getAddressValidity());
    }
}
