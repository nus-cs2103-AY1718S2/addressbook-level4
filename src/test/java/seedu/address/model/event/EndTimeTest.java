package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EndTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EndTime(null));
    }

    @Test
    public void constructor_invalidEndTime_throwsIllegalArgumentException() {
        String invalidEndTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EndTime(invalidEndTime));
    }

    @Test
    public void isValidEndTime() {
        // null end time
        Assert.assertThrows(NullPointerException.class, () -> EndTime.isValidEndTime(null));

        // invalid end time
        assertFalse(EndTime.isValidEndTime("")); // empty string
        assertFalse(EndTime.isValidEndTime(" ")); // spaces only
        assertFalse(EndTime.isValidEndTime("wejo*21")); // invalid string
        assertFalse(EndTime.isValidEndTime("12-01")); // invalid format
        assertFalse(EndTime.isValidEndTime("24:01")); // invalid Hour
        assertFalse(EndTime.isValidEndTime("00:79")); // invalid Minute
        assertFalse(EndTime.isValidEndTime("101:04")); // invalid Hour
        assertFalse(EndTime.isValidEndTime("00:100")); // invalid Minute

        // valid end time
        assertTrue(EndTime.isValidEndTime("10:00")); // valid date
        assertTrue(EndTime.isValidEndTime("18:55")); // valid date (24Hr Format)
    }
}
