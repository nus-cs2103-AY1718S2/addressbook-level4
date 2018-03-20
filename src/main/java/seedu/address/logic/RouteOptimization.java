package seedu.address.logic;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

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
        String distanceOnlyNumber = distance.substring(0,distance.length()-3);
        System.out.println(Double.parseDouble(distanceOnlyNumber));
    }
}
