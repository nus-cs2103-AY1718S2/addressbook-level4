//@@author philos22

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.CalendarTest;

/**
 * Ensures that the events are listed in the same way
 */
public class CalendarCommandTest {

    private static List<String> pseudoEventList = new ArrayList<>();

    @Before
    public void setUp() {
        pseudoEventList.add("[Event 1] \t EventName1 \t [EventStart1] to [EventEnd1] \tLocation: EventLocation1\n");
        pseudoEventList.add("[Event 2] \t EventName2 \t [EventStart2] to [EventEnd2] \tLocation: EventLocation2\n");
        pseudoEventList.add("[Event 3] \t EventName3 \t [EventStart3] to [EventEnd3] \tLocation: EventLocation3\n");
        pseudoEventList.add("[Event 4] \t EventName4 \t [EventStart4] to [EventEnd4] \tLocation: EventLocation4\n");
        pseudoEventList.add("[Event 5] \t EventName5 \t [EventStart5] to [EventEnd5] \tLocation: EventLocation5\n");
        pseudoEventList.add("[Event 6] \t EventName6 \t [EventStart6] to [EventEnd6] \tLocation: EventLocation6\n");
        pseudoEventList.add("[Event 7] \t EventName7 \t [EventStart7] to [EventEnd7] \tLocation: EventLocation7\n");
        pseudoEventList.add("[Event 8] \t EventName8 \t [EventStart8] to [EventEnd8] \tLocation: EventLocation8\n");
    }

    @Test
    public void calendarListEvents_showsSameList() {
        StringBuilder expectedOutput = new StringBuilder();
        for (String eventLine : pseudoEventList) {
            expectedOutput.append(eventLine);
        }
        assertEquals(CalendarTest.listEventsTest().toString(), expectedOutput.toString());
    }

}
