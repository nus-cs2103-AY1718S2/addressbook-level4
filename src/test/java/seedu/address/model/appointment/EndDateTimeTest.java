package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author trafalgarandre
public class EndDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EndDateTime(null));
    }

    @Test
    public void constructor_invalidEndDateTime_throwsIllegalArgumentException() {
        String invalidEndDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EndDateTime(invalidEndDateTime));
    }

    @Test
    public void isValidEndDateTime() {
        // null endDateTime
        Assert.assertThrows(NullPointerException.class, () -> EndDateTime.isValidEndDateTime(null));

        // invalid endDateTime
        assertFalse(EndDateTime.isValidEndDateTime("")); // empty string
        assertFalse(EndDateTime.isValidEndDateTime(" ")); // spaces only
        assertFalse(EndDateTime.isValidEndDateTime("abc")); // random string
        assertFalse(EndDateTime.isValidEndDateTime("23-09-2018 12:00")); // wrong order
        assertFalse(EndDateTime.isValidEndDateTime("2018-23-09")); // missing time

        // valid endDateTime
        assertTrue(EndDateTime.isValidEndDateTime("2018-03-26 12:00"));
    }
}
