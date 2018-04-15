package seedu.address.model.CalendarEvent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author wayneong95

public class EventTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventStartTime(null));
        Assert.assertThrows(NullPointerException.class, () -> new EventEndTime(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidEventTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventStartTime(invalidEventTime));
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventEndTime(invalidEventTime));
    }

    @Test
    public void isValidEventTime() {
        // null event time
        Assert.assertThrows(NullPointerException.class, () -> EventStartTime.isValidTime(null));
        Assert.assertThrows(NullPointerException.class, () -> EventEndTime.isValidTime(null));

        // invalid event time
        assertFalse(EventStartTime.isValidTime("")); // empty string
        assertFalse(EventStartTime.isValidTime("17:MM")); // contains non-numeric characters
        assertFalse(EventStartTime.isValidTime("17:%%")); // contains non-alphanumeric characters
        assertFalse(EventStartTime.isValidTime("17:")); // missing minutes field
        assertFalse(EventStartTime.isValidTime(":30")); // missing hours field
        assertFalse(EventStartTime.isValidTime(":")); // missing both fields
        assertFalse(EventEndTime.isValidTime("")); // empty string
        assertFalse(EventEndTime.isValidTime("17:MM")); // contains non-numeric characters
        assertFalse(EventEndTime.isValidTime("17:%%")); // contains non-alphanumeric characters
        assertFalse(EventEndTime.isValidTime("17:")); // missing minutes field
        assertFalse(EventEndTime.isValidTime(":30")); // missing hours field
        assertFalse(EventEndTime.isValidTime(":")); // missing both fields

        // valid eventName
        assertTrue(EventStartTime.isValidTime("17:30")); // correct format
        assertTrue(EventStartTime.isValidTime("15:30")); // correct format
        assertTrue(EventEndTime.isValidTime("17:30")); // correct format
        assertTrue(EventEndTime.isValidTime("15:30")); // correct format
    }
}
