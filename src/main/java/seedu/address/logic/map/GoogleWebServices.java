package seedu.address.logic.map;

import java.io.IOException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
//@@author Damienskt
/**
 * Initialise connection to google server to use its API
 */
public class GoogleWebServices {

    // 6 API keys for accessing google server
    public static final String API_KEY_1 = "AIzaSyB78sjE5UtuPn9T7u1WHowRkWj2KFo92rI";
    public static final String API_KEY_2 = "AIzaSyAplrsZatzM_d2ynML097uqXd1-usgscOA";
    public static final String API_KEY_3 = "AIzaSyDdJMB6Jug8D_45K72FpbEL8S5XQF_98Oc";
    public static final String API_KEY_4 = "AIzaSyA-gBgtvaQU4NMEmO37UJEyx5YHnuFU30E";
    public static final String API_KEY_5 = "AIzaSyAD8_oIBJlzOp30VA9mOvQKp6GZe8SFsYY";
    public static final String API_KEY_6 = "AIzaSyBKemZo4WFMDaJ1q_vWxquZxTyYF24skCI";

    public static final String MESSAGE_FAIL_CONNECTION = "Api key reached max daily usage, "
            + "please wait till 3pm SGT for it to be reset";

    private static GeoApiContext context;
    private boolean isInitialised;

    /**
     * Initialises access to google server using Api keys
     */
    public GoogleWebServices() {
        initialiseConnection();
    }

    /**
     * Initialise with valid Api key and test connection to google server
     */
    private void initialiseConnection() {
        isInitialised = true;
        context = new GeoApiContext.Builder()
                .apiKey(API_KEY_5)
                .build();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    "Punggol").await();
            LatLng location = results[0].geometry.location;
        } catch (ApiException | InterruptedException | IOException | IndexOutOfBoundsException e) {
            isInitialised = false;
        }
    }

    public boolean checkInitialisedConnection() {
        return isInitialised;
    }

    public static GeoApiContext getGeoApiContext() {
        return context;
    }
}
