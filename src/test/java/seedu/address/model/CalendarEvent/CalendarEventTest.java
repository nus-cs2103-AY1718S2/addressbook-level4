package seedu.address.model.CalendarEvent;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

//@@author wayneong95

public class CalendarEventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CalendarEvent(null,
                null, null, null, null));
    }

    @Test
    public void calendarEventNameTest () {
        //Default calendar event built by event builder
        CalendarEvent calendarEvent1 = new EventBuilder().build();
        //Calendar event built by event builder with individual fields provided
        CalendarEvent calendarEvent2 = new EventBuilder().withEventName(VALID_EVENT_NAME)
                .withStartDate(VALID_START_DATE).withStartTime(VALID_START_TIME)
                .withEndDate(VALID_END_DATE).withEndTime(VALID_END_TIME).build();

        assertTrue(calendarEvent1.toString().equals(calendarEvent2.toString()));

    }
}
