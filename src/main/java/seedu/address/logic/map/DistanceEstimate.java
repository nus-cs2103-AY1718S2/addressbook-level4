package seedu.address.logic.map;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
//@@author Damienskt
/**
 * Calculates distance and travel duration between two location.
 */
public class DistanceEstimate extends GoogleWebServices {

    private String distBetweenOriginDest;
    private String travelTime;

    /**
     * Extract time duration details from {@code matrixDetails}
     * @return time needed to travel between two location
     */
    private static Duration extractDurationDetailsToString(DistanceMatrix matrixDetails) {
        DistanceMatrixRow[] dataRow = matrixDetails.rows;
        DistanceMatrixElement[] dataElements = dataRow[0].elements;

        return dataElements[0].duration;
    }

    /**
     * Extract travel distance details from {@code matrixDetails}
     * @return distance between two location
     */
    private static Distance extractDistanceDetailsToString(DistanceMatrix matrixDetails) {
        DistanceMatrixRow[] dataRow = matrixDetails.rows;
        DistanceMatrixElement[] dataElements = dataRow[0].elements;

        return dataElements[0].distance;
    }

    /**
     * Request new approval for each access
     * @return successful approval from google server
     */
    private static DistanceMatrixApiRequest getApprovalForRequest(GeoApiContext context) {
        return DistanceMatrixApi.newRequest(context);
    }

    /**
     * Initialises the calculation of time and distance between two location by sending request with
     * {@code startPostalCode},{@code endPostalCode} and {@code modeOfTravel} to google server, details
     * extracted from result {@code estimate} and stored into {@code distBetweenOriginDest} and {@code travelTime}
     */
    public void calculateDistanceMatrix(LatLng startLocation, LatLng endLocation, TravelMode modeOfTravel) {
        DistanceMatrixApiRequest request = getApprovalForRequest(GoogleWebServices.getGeoApiContext());
        DistanceMatrix estimate = null;
        try {
            estimate = request.origins(startLocation)
                    .destinations(endLocation)
                    .mode(modeOfTravel)
                    .language("en-EN")
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        distBetweenOriginDest = String.valueOf(extractDistanceDetailsToString(estimate));
        travelTime = String.valueOf(extractDurationDetailsToString(estimate));
    }

    public String getTravelTime() {
        return travelTime;
    }

    public String getDistBetweenOriginDest() {
        return distBetweenOriginDest;
    }
}
