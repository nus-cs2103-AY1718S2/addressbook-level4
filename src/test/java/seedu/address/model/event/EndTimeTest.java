package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.TimeUtil;
import seedu.address.testutil.Assert;

//@@author SuxianAlicia
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
        Assert.assertThrows(NullPointerException.class, () -> TimeUtil.isValidTime(null));

        // invalid end time
        assertFalse(TimeUtil.isValidTime("")); // empty string
        assertFalse(TimeUtil.isValidTime(" ")); // spaces only
        assertFalse(TimeUtil.isValidTime("wejo*21")); // invalid string
        assertFalse(TimeUtil.isValidTime("12-01")); // invalid format
        assertFalse(TimeUtil.isValidTime("24:01")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:79")); // invalid Minute
        assertFalse(TimeUtil.isValidTime("101:04")); // invalid Hour
        assertFalse(TimeUtil.isValidTime("00:100")); // invalid Minute

        // valid end time
        assertTrue(TimeUtil.isValidTime("10:00")); // valid date
        assertTrue(TimeUtil.isValidTime("18:55")); // valid date (24Hr Format)
    }
}
