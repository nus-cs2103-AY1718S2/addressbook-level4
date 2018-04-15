//@@author philos22

package seedu.address;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

/**
 * This class is meant to override some properties of Calendar so that it will be suited for
 * testing
 */
public class CalendarTest extends Calendar {

    private static ArrayList<String> eventIDs = new ArrayList<>();

    static {
        addEventIDs("one1");
        addEventIDs("two2");
        addEventIDs("three3");
        addEventIDs("four4");
        addEventIDs("five5");
        addEventIDs("six6");
        addEventIDs("seven7");
        addEventIDs("eight8");
    }

    private static List<String> pseudoEventList = new ArrayList<>();

    static {
        pseudoEventList.add("[Event 1] \t EventName1 \t [EventStart1] to [EventEnd1] \tLocation: EventLocation1\n");
        pseudoEventList.add("[Event 2] \t EventName2 \t [EventStart2] to [EventEnd2] \tLocation: EventLocation2\n");
        pseudoEventList.add("[Event 3] \t EventName3 \t [EventStart3] to [EventEnd3] \tLocation: EventLocation3\n");
        pseudoEventList.add("[Event 4] \t EventName4 \t [EventStart4] to [EventEnd4] \tLocation: EventLocation4\n");
        pseudoEventList.add("[Event 5] \t EventName5 \t [EventStart5] to [EventEnd5] \tLocation: EventLocation5\n");
        pseudoEventList.add("[Event 6] \t EventName6 \t [EventStart6] to [EventEnd6] \tLocation: EventLocation6\n");
        pseudoEventList.add("[Event 7] \t EventName7 \t [EventStart7] to [EventEnd7] \tLocation: EventLocation7\n");
        pseudoEventList.add("[Event 8] \t EventName8 \t [EventStart8] to [EventEnd8] \tLocation: EventLocation8\n");
    }

    public static void addEventIDs(String event) {
        eventIDs.add(event);
    }

    /**
     * Manually creating a list of events for testing
     */
    public static String listEventsTest() {
        StringBuilder result = new StringBuilder();
        for (String eventLine : pseudoEventList) {
            result.append(eventLine);
        }
        return result.toString();
    }

    @Test
    public void addEventTest() {
        String eventName = "eventName";
        String eventLocation = "eventLoc";

        TemporalAccessor taStart = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse("2018-05-05 12:00:00");
        String strStartDateTime = LocalDateTime.from(taStart).toString() + ":00+08:00";

        TemporalAccessor taEnd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse("2018-05-05 13:00:00");
        String strEndDateTime = LocalDateTime.from(taEnd).toString() + ":00+08:00";

        DateTime startDateTime = new DateTime(strStartDateTime);
        DateTime endDateTime = new DateTime(strEndDateTime);

        Event event = new Event()
                .setSummary(eventName)
                .setLocation(eventLocation);

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("");
        event.setEnd(end);

        //EventName matches
        assertEquals(event.getSummary(), eventName);

        //Location matches
        assertEquals(event.getLocation(), eventLocation);

        //Start matches
        assertEquals(event.getStart().toString(), "{dateTime=" + startDateTime + ", timeZone=}");

        //End matches
        assertEquals(event.getEnd().toString(), "{dateTime=" + endDateTime + ", timeZone=}");
    }

}
