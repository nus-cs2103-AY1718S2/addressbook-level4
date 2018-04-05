package seedu.address.external;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import seedu.address.model.ReadOnlySchedule;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 */
public class GCalendarService {
    private Credential credential;
    private HttpTransport httpTransport;
    private JsonFactory JSON_FACTORY;

    public GCalendarService() {}

    public GCalendarService(Credential credential,
                            HttpTransport httpTransport, JsonFactory JSON_FACTORY) {
        this.credential = credential;
        this.httpTransport = httpTransport;
        this.JSON_FACTORY = JSON_FACTORY;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setHttpTransport(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    public HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public void setJSON_FACTORY(JsonFactory JSON_FACTORY) {
        this.JSON_FACTORY = JSON_FACTORY;
    }

    public JsonFactory getJSON_FACTORY() {
        return JSON_FACTORY;
    }

    /**
     * Synchronizes the user's Google Calendar with the local version
     * @param schedule
     */
    public void synchronize(ReadOnlySchedule schedule) {
        Calendar service = new Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(GServiceManager.APPLICATION_NAME)
                .build();
        DateTime now = new DateTime(System.currentTimeMillis());
        try {
            Events events = service.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }

        } catch (Exception e) {
            System.out.println("Lmao " + e);
        }
    }
}
