//@@author jaronchan
package seedu.address.commons.util;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

/**
 * Helps with retrieving the geocode from given address.
 */

public class GeocodeUtil {

    private static GeocodingService geocodingService;
    private static LatLong geocode;

    /**
     *  @return geocode
     */

    public static LatLong getGeocode(String address) {
        convertAddressToGeocode(address);
        return geocode;
    }

    /**
     *  Calls on geocodingService to update geocode.
     */

    private static void convertAddressToGeocode(String address) {
        geocodingService = new GeocodingService();
        geocodingService.geocode(address, (GeocodingResult[] results, GeocoderStatus status) -> {

            geocode = null;
            if (status == GeocoderStatus.ZERO_RESULTS) {
                return;
            } else if (results.length > 1) {
                geocode = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                        results[0].getGeometry().getLocation().getLongitude());
            } else {
                geocode = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                        results[0].getGeometry().getLocation().getLongitude());
            }
        });
        return;
    }
}
