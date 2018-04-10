package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author trafalgarandre
public class StartDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartDateTime(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidStartDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartDateTime(invalidStartDateTime));
    }

    @Test
    public void isValidStartDateTime() {
        // null startDateTime
        Assert.assertThrows(NullPointerException.class, () -> StartDateTime.isValidStartDateTime(null));

        // invalid startDateTime
        assertFalse(StartDateTime.isValidStartDateTime("")); // empty string
        assertFalse(StartDateTime.isValidStartDateTime(" ")); // spaces only
        assertFalse(StartDateTime.isValidStartDateTime("abc")); // random string
        assertFalse(StartDateTime.isValidStartDateTime("23-09-2018 12:00")); // wrong order
        assertFalse(StartDateTime.isValidStartDateTime("2018-23-09")); // missing time

        // valid startDateTime
        assertTrue(StartDateTime.isValidStartDateTime("2018-03-26 12:00"));
    }
}
