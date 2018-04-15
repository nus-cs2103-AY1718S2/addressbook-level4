package seedu.address.model.entry;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.TimeUtil;
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
        Assert.assertThrows(NullPointerException.class, () -> TimeUtil.isValidTime(null));

        // invalid start time
        assertFalse(TimeUtil.isValidTime("")); // empty string
        assertFalse(TimeUtil.isValidTime(" ")); // spaces only
        assertFalse(TimeUtil.isValidTime("wejo*21")); // invalid string
        assertFalse(TimeUtil.isValidTime("12-01")); // invalid format
        assertFalse(TimeUtil.isValidTime("24:01")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:79")); // invalid Minute
        assertFalse(TimeUtil.isValidTime("101:04")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:100")); // invalid Minute

        // valid start time
        assertTrue(TimeUtil.isValidTime("10:00")); // valid date
        assertTrue(TimeUtil.isValidTime("18:55")); // valid date (24Hr Format)
    }
}
