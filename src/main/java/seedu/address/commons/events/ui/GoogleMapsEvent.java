package seedu.address.commons.events.ui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import seedu.address.commons.events.BaseEvent;

//@@author jingyinno
/**
 * Represents a Google Maps event in GoogleMapsDisplay
 */
public class GoogleMapsEvent extends BaseEvent {

    private static final String MAPS_URL = "https://www.google.com.sg/maps";
    private String locations;
    private boolean isOneLocationEvent;

    public GoogleMapsEvent(String locations, boolean isOneLocationEvent) throws IOException {
        this.locations = locations;
        this.isOneLocationEvent = isOneLocationEvent;
        checkInternetConnection();
    }

    public String getLocations() {
        return locations;
    }

    public boolean getIsOneLocationEvent() {
        return isOneLocationEvent;
    }

    /**
     * Checks if there is internet connection.
     *
     * @throws IOException if there is no internet connection
     */
    private boolean checkInternetConnection() throws IOException {
        URL url = new URL(MAPS_URL);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.connect();
        return HttpURLConnection.HTTP_OK == urlConn.getResponseCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
