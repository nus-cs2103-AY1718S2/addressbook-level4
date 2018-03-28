package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StartTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartTime(null));
    }

    @Test
    public void constructor_invalidStartTime_throwsIllegalArgumentException() {
        String invalidStartTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidStartTime));
    }

    @Test
    public void isValidStartTime() {
        // null start time
        Assert.assertThrows(NullPointerException.class, () -> StartTime.isValidStartTime(null));

        // invalid start time
        assertFalse(StartTime.isValidStartTime("")); // empty string
        assertFalse(StartTime.isValidStartTime(" ")); // spaces only
        assertFalse(StartTime.isValidStartTime("wejo*21")); // invalid string
        assertFalse(StartTime.isValidStartTime("12-01")); // invalid format
        assertFalse(StartTime.isValidStartTime("24:01")); // invalid Hour
        assertFalse(StartTime.isValidStartTime("00:79")); // invalid Minute
        assertFalse(StartTime.isValidStartTime("101:04")); // invalid Hour
        assertFalse(StartTime.isValidStartTime("00:100")); // invalid Minute

        // valid start time
        assertTrue(StartTime.isValidStartTime("10:00")); // valid date
        assertTrue(StartTime.isValidStartTime("18:55")); // valid date (24Hr Format)
    }
}
