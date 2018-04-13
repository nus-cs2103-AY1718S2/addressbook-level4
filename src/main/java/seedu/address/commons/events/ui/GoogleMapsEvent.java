package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

//@@author jingyinno
/**
 * Represents a Google Maps event in GoogleMapsDisplay
 */
public class GoogleMapsEvent extends BaseEvent {

    private String locations;
    private boolean isOneLocationEvent;
    private final String MAPS_URL = "https://www.google.com.sg/maps";

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
