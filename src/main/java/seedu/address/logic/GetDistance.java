package seedu.address.logic;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

/**
 * Testing retrieve distance data
 */
public class GetDistance {
    /**
     * get driving distance from origin to destination
     */

    public double getDistance(String origin, String destination) {
        String distanceWithoutUnit = "";
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBWyCJkCym1dSouzHX_FxLk6Tj11C7F0Ao")
                .build();

        String[] origins = {origin};
        String[] destinations = {destination};

        DistanceMatrix matrix = null;
        try {
            matrix = DistanceMatrixApi.getDistanceMatrix(context, origins, destinations).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String distance = matrix.rows[0].elements[0].distance.toString();
        distanceWithoutUnit = distance.substring(0,distance.length()-3);
        return Double.parseDouble(distanceWithoutUnit);
    }

}
