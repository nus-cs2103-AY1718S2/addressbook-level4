package seedu.address.model.lesson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author demitycho
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
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid addresses
        assertFalse(Time.isValidTime(""));          // blank
        assertFalse(Time.isValidTime("9:00"));      // short form time
        assertFalse(Time.isValidTime("09:60"));     // Invalid minute
        assertFalse(Time.isValidTime("24:00"));     // Boundary value
        assertFalse(Time.isValidTime("09-10"));     // Using dash -
        assertFalse(Time.isValidTime("09.10"));     // Using .

        // valid addresses
        assertTrue(Time.isValidTime("10:00"));
        assertTrue(Time.isValidTime("22:00"));
        assertTrue(Time.isValidTime("00:00"));      //boundary
        assertTrue(Time.isValidTime("23:59"));
    }
}
