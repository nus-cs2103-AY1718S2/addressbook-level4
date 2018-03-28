//@@author jaronchan
package seedu.address.logic;

import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;

//import seedu.address.commons.core.EventsCenter;
//import seedu.address.commons.events.ui.ShowInvalidAddressOverlayEvent;

/**
 * Handles changes to any Map user interfaces.
 */

public class MapManager {
    /**
     * Helps with retrieving the geocode from given address.
     */
    public static class GeocodeUtil {

        private static GeocodingService geocodingService = new GeocodingService();

        /**
         *  Retrieves geocode of specified person address and updates map accordingly.
         */
        public static class MyGeocodingServiceCallback implements GeocodingServiceCallback {
            private GoogleMap map;
            public MyGeocodingServiceCallback(GoogleMap map) {
                this.map = map;
            }
            @Override
            public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
                LatLong geocode;
                if (status == GeocoderStatus.ZERO_RESULTS) {
                    geocode = null;

                } else if (results.length > 1) {
                    geocode = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                } else {
                    geocode = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                }

                if (geocode != null) {
                    map.setZoom(17);
                    map.setCenter(geocode);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(geocode);

                    Marker marker = new Marker(markerOptions);
                    map.addMarker(marker);
                } else {
                    map.setZoom(10);
                    map.setCenter(new LatLong(1.3521, 103.8198));
                    map.clearMarkers();
                }
            }
        }

        /**
         *  Calls on geocodingService to update geocode and set map maker of specified person.
         */
        public static void setMapMarkerFromAddress(GoogleMap map, String address) {
            geocodingService.geocode(address, new MyGeocodingServiceCallback(map));
        }
    }
}






