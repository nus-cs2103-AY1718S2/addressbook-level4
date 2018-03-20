package seedu.address.logic;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

/**
 * Testing retrieve distance data
 */
public class RouteOptimization {
    public static void main(String []agrs) throws InterruptedException, ApiException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBWyCJkCym1dSouzHX_FxLk6Tj11C7F0Ao")
                .build();

        String[] origin = {"21 Lower Kent Ridge Rd, Singapore 119077"};
        String[] destination = {"50 Nanyang Ave, 639798"};

        DistanceMatrix matrix =
                DistanceMatrixApi.getDistanceMatrix(context, origin, destination).await();
        String distance = matrix.rows[0].elements[0].distance.toString();
        String distanceOnlyNumber = distance.substring(0, distance.length() - 3);
        System.out.println(Double.parseDouble(distanceOnlyNumber));
    }
}
