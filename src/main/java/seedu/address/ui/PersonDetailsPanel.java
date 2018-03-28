package seedu.address.ui;

import static seedu.address.commons.util.GeocodeUtil.getGeocode;

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
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

//@@author jaronchan
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
     */

    private void loadPersonMapAddress(Person person) {
        LatLong addressAsGeocode = getGeocode(person.getAddress().toString());

        mapView.setZoom(17);
        mapView.setCenter(addressAsGeocode.getLatitude(), addressAsGeocode.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(addressAsGeocode);

        Marker marker = new Marker(markerOptions);
        map.addMarker(marker);

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
                .zoomControl(false)
                .zoom(10);

        map = mapView.createMap(mapOptions);
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
}
