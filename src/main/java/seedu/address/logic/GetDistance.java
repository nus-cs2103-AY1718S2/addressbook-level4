package seedu.address.logic;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

//@@author
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
        int space = distance.indexOf(" ");
        String units = distance.substring(space + 1, distance.length());
        double metres;
        distanceWithoutUnit = distance.substring(0, space);
        if (units.equals("m")) {
            metres = Double.parseDouble(distanceWithoutUnit) / 1000.0;
            return metres;
        } else {
            return Double.parseDouble(distanceWithoutUnit);
        }
    }

    public double getTime(String origin, String destination) {
        String durationWithoutUnit = "";
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
        String duration = matrix.rows[0].elements[0].duration.toString();
        System.out.println(matrix);
        System.out.println(duration);
        int space = duration.indexOf(" ");
        String units = duration.substring(space + 1, duration.length());
        double metres;
        durationWithoutUnit = duration.substring(0, space);

        return Double.parseDouble(durationWithoutUnit);
    }



}
