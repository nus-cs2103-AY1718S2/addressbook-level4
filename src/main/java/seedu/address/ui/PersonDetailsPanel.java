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
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;
import seedu.address.logic.MapManager;
import seedu.address.model.person.Person;

/**
 * The Person Details Panel of the App.
 * To be UPDATED
 */
public class PersonDetailsPanel extends UiPart<Region>
        implements Initializable, MapComponentInitializedListener {

    private static final String FXML = "PersonDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GoogleMapView mapView;

    @FXML
    private Pane invalidAddressOverlay;

    private GoogleMap map;

    public PersonDetailsPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.

        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);

    }

    /**
     * Update the map based on new selection event.
     * Default view is shown if no address is invalid.
     */

    private void loadPersonMapAddress(Person person) {

        MapManager.GeocodeUtil.setMapMarkerFromAddress(map, person.getAddress().toString());
    }

    /**
     * Set the initial properties of the map.
     */

    @Override
    public void mapInitialized() {

        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(1.3521, 103.8198))
                .mapType(MapTypeIdEnum.ROADMAP)
                .mapTypeControl(false)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoom(10);

        map = mapView.createMap(mapOptions);
        invalidAddressOverlay.setVisible(false);

    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        map = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonMapAddress(event.getNewSelection().person);
    }

    @Subscribe
    private void handleShowInvalidAddressOverlayEvent(ShowInvalidAddressOverlayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        invalidAddressOverlay.setVisible(event.getAddressValidity());
    }
}
