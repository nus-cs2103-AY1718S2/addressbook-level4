//@@author jaronchan
package seedu.address.ui;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.logic.OAuthManager;

/**
 * An UI component that displays information of a {@code Event}.
 */

public class ScheduledEventCard extends UiPart<Region> {

    private static final String FXML = "ScheduledEventCard.fxml";
    private static final String EVENT_NUM_HEADER = "EVENT NUM: ";
    private static final String EVENT_TITLE_HEADER = "TITLE: ";
    private static final String EVENT_TIMING_HEADER = "TIME: ";
    private static final String EVENT_LOCATION_HEADER = "LOCATION: ";
    private static final String EVENT_PERSON_HEADER = "NAME: ";
    private static final String EVENT_CONDITION_HEADER = "CONDITION: ";
    private static final String EVENT_MOBILE_HEADER = "MOBILE: ";
    private static final String FUTURE_IMPLEMENTATION = "TO BE IMPLEMENTED IN 2.0";
    private static final String EVENT_DIVIDER = "================================ \n";

    public final Event event;

    private String formattedScheduledEvent;

    @FXML
    private TextArea eventInfo;


    public ScheduledEventCard(Event event, int eventIndex) {
        super(FXML);
        this.event = event;

        formattedScheduledEvent = scheduledEventFormatter(this.event, eventIndex);
        eventInfo.setWrapText(true);
        eventInfo.setText(formattedScheduledEvent);
    }

    /**
     * A method to format the scheduled event information of a {@code Event}.
     */
    private String scheduledEventFormatter(Event event, int eventIndex) {
        String title = event.getSummary();
        DateTime startAsDateTime = event.getStart().getDateTime();
        DateTime endAsDateTime = event.getEnd().getDateTime();
        String location = event.getLocation();

        String start = OAuthManager.getDateTimeAsHumanReadable(startAsDateTime);
        String end = OAuthManager.getDateTimeAsHumanReadable(endAsDateTime);

        if (start == null) {
            start = "Unable to retrieve start time";
        }
        if (end == null) {
            end = "Unable to retrieve end time";
        }
        if (location == null) {
            location = "No Location Specified";
        }
        String eventAsString = EVENT_NUM_HEADER + eventIndex + "\n"
                + EVENT_TITLE_HEADER + title + "\n"
                + EVENT_TIMING_HEADER + start + " - " + end + "\n"
                + EVENT_DIVIDER
                + EVENT_LOCATION_HEADER + location + "\n"
                + EVENT_PERSON_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_MOBILE_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_CONDITION_HEADER + FUTURE_IMPLEMENTATION + "\n"
                + EVENT_DIVIDER + EVENT_DIVIDER;
        System.out.printf(eventAsString);

        return eventAsString;
    }
}
