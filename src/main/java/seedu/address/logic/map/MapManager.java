//@@author jaronchan
package seedu.address.logic.map;

import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

/**
 * Handles changes to any Map user interfaces.
 */

public class MapManager {

    private GoogleMap map;
    private GeocodingService geocodingService;

    private DirectionsService directionsService;
    private DirectionsRequest directionsRequest;
    private DirectionsRenderer directionsRenderer;
    private DirectionsPane directionsPane;

    public MapManager(GoogleMap map, DirectionsPane directionsPane) {
        this.map = map;
        geocodingService = new GeocodingService();
        directionsService = new DirectionsService();
        this.directionsPane = directionsPane;
        directionsRenderer = new DirectionsRenderer(true, map, directionsPane);
    }

    /**
     *  Calls on geocodingService to update geocode and set map maker of specified person.
     */

    public void setMapMarkerFromAddress(GoogleMap map, String address) {
        geocodingService.geocode(address, new MyGeocodingServiceCallback(map));
    }

    /**
     *  Sets the directions between addresses on the map.
     */
    public void setDirectionsOnMap(String addressOrigin, String addressDestination) {
        directionsRenderer.clearDirections();
        directionsRequest = new DirectionsRequest(addressOrigin, addressDestination, TravelModes.DRIVING);
        directionsRenderer = new DirectionsRenderer(true, map, directionsPane);
        directionsService.getRoute(directionsRequest, new MyDirectionsServiceCallback(map), directionsRenderer);
    }

    public void resetDirectionsMap() {
        directionsRenderer.clearDirections();
    }
}






