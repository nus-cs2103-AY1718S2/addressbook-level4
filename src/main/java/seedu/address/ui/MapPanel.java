//@@author jaronchan
package seedu.address.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.MapManager;

/**
 * The UI component that handles the display of maps.
 */
public class MapPanel extends UiPart<Region>
        implements Initializable, MapComponentInitializedListener {


    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private GoogleMapView mapView;

    @FXML
    private Pane invalidAddressOverlay;
    private GoogleMap map;

    public MapPanel(String fxmlUrl) {
        super(fxmlUrl);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);

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
     * Resets the map to default view .
     */
    public void resetMap() {
        if (map != null) {
            map.clearMarkers();
            map.setCenter(new LatLong(1.3521, 103.8198));
            map.setZoom(10);
            map.setMapType(MapTypeIdEnum.ROADMAP);
        }
    }
    public void loadAddress(String address) {
        MapManager.GeocodeUtil.setMapMarkerFromAddress(map, address);
    }
    public void showInvalidAddressOverlay(Boolean show) {
        invalidAddressOverlay.setVisible(show);
    }
    public void loadDirections(String addressOrigin, String addressDestination) {
        MapManager.DirectionsUtil.setDirectionsOnMap(map, mapView.getDirec(), addressOrigin, addressDestination);
    }
    //    public void removeDirections() {
    //
    //    }
    public void freeResources() {
        map = null;
    }

}
