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
            String distance = matrix.rows[0].elements[0].distance.toString();
            distanceWithoutUnit = distance.substring(0, distance.length() - 3);

        } catch (InterruptedException e) {
            e.getStackTrace();
        } catch (ApiException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return Double.parseDouble(distanceWithoutUnit);
    }

    /**
     * Build Google Map API Geo context with API key
     */
    private GeoApiContext buildGeoContext() {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyA1bGYFWFjIe4GknIrK_2fHZSOaPcGu7Io")
                .build();

        return context;
    }
}
