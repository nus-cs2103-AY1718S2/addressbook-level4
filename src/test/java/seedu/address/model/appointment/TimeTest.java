package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author jlks96
public class TimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("1234")); // invalid time
        assertFalse(Time.isValidTime("time")); // non-numeric
        assertFalse(Time.isValidTime("eleven 30")); // alphabets within time
        assertFalse(Time.isValidTime("12 :30")); // spaces within time
        assertFalse(Time.isValidTime("25:00")); // invalid hour

        // valid time
        assertTrue(Time.isValidTime("11:00"));
        assertTrue(Time.isValidTime("00:00"));
    }

    @Test
    public void isEqual_equalTimes_success() {
        assertTrue(new Time("12:00").equals(new Time("12:00")));
    }
}
