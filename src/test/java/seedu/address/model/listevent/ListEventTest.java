package seedu.address.model.listevent;
//@@author crizyli
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.api.client.util.DateTime;

public class ListEventTest {

    @Test
    public void construct_success() {
        DateTime startTime = new DateTime("2018-04-30T18:00:00");
        ListEvent listEvent = new ListEvent("Test Event", "NUS", startTime);
        assertEquals("Test Event", listEvent.getTitle());
        assertEquals("NUS", listEvent.getLocation());
        assertEquals(startTime, listEvent.getStartTime());
        assertEquals("EVENT: Test Event  ||   LOCATION: NUS  ||   START AT: " + startTime.toString()
                .substring(0, startTime.toString().lastIndexOf("+")).replaceAll("T", " "),
                listEvent.toString());
    }
}
