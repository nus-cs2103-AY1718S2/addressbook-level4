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

public class MapManager {
    /**
     * Helps with retrieving the geocode from given address.
     */

    public static class GeocodeUtil {

        private static GeocodingService geocodingService = new GeocodingService();

        /**
         *  Calls on geocodingService to update geocode.
         */
        public static class MyGeocodingServiceCallback implements GeocodingServiceCallback {
            private GoogleMap map;
            public MyGeocodingServiceCallback(GoogleMap map){
                this.map = map;
            }
            @Override
            public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
                LatLong coordination;
                if (status == GeocoderStatus.ZERO_RESULTS) {
                    coordination = null;
                } else if (results.length > 1) {
                    coordination = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                } else {
                    coordination = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                }

                if (coordination != null) {
                    map.setZoom(17);
                    map.setCenter(coordination);

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(coordination);

                    Marker marker = new Marker(markerOptions);
                    map.addMarker(marker);

            }
        }

        /**
         *  @return geocode
         */
        public static void setMapMarkerFromAddress(GoogleMap map, String address) {
            geocodingService.geocode(address, new MyGeocodingServiceCallback(map));
        }
    }
}






