package seedu.address.model.CalendarEvent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author wayneong95

public class EventDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventStartDate(null));
        Assert.assertThrows(NullPointerException.class, () -> new EventEndDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidEventDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventStartDate(invalidEventDate));
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventEndDate(invalidEventDate));
    }

    @Test
    public void isValidEventDate() {
        // null eventDate
        Assert.assertThrows(NullPointerException.class, () -> EventStartDate.isValidDate(null));
        Assert.assertThrows(NullPointerException.class, () -> EventEndDate.isValidDate(null));

        // invalid eventDate
        assertFalse(EventStartDate.isValidDate("")); // empty string
        assertFalse(EventStartDate.isValidDate("2018-MM-20")); // contains non-numeric characters
        assertFalse(EventStartDate.isValidDate("2018-%%-20")); // contains non-alphanumeric characters
        assertFalse(EventStartDate.isValidDate("05-20")); // missing year field
        assertFalse(EventStartDate.isValidDate("2018-05")); // missing month/day field
        assertFalse(EventStartDate.isValidDate("2018")); // missing month and day field
        assertFalse(EventStartDate.isValidDate("18-05-20")); // invalid year format
        assertFalse(EventEndDate.isValidDate("")); // empty string
        assertFalse(EventEndDate.isValidDate("2018-MM-20")); // contains non-numeric characters
        assertFalse(EventEndDate.isValidDate("2018-%%-20")); // contains non-alphanumeric characters
        assertFalse(EventEndDate.isValidDate("05-20")); // missing year field
        assertFalse(EventEndDate.isValidDate("2018-05")); // missing month/day field
        assertFalse(EventEndDate.isValidDate("2018")); // missing month and day field
        assertFalse(EventEndDate.isValidDate("18-05-20")); // invalid year format


        // valid eventName
        assertTrue(EventStartDate.isValidDate("2018-05-20")); // correct format
        assertTrue(EventStartDate.isValidDate("2017-04-25")); // correct format
        assertTrue(EventEndDate.isValidDate("2018-05-20")); // correct format
        assertTrue(EventEndDate.isValidDate("2017-04-25")); // correct format
    }
}
