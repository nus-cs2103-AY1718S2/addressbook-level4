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

        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyA1bGYFWFjIe4GknIrK_2fHZSOaPcGu7Io")
                    .build();
            String[] origins = {origin};
            String[] destinations = {destination};

            DistanceMatrix matrix =
                    DistanceMatrixApi.getDistanceMatrix(context, origins, destinations).await();
            String distance = matrix.rows[0].elements[0].duration.toString();
            distanceWithoutUnit = distance.substring(0, distance.length() - 3);

        } catch (InterruptedException e) {
            e.getMessage();
        } catch (ApiException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        return Double.parseDouble(distanceWithoutUnit);
    }

}
