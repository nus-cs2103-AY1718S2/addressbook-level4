package seedu.address.model.lesson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author demitycho
public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Day(invalidDay));
    }

    @Test
    public void isValidDay() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid days
        assertFalse(Day.isValidDay(""));        // blank
        assertFalse(Day.isValidDay("monday"));  // long form names
        assertFalse(Day.isValidDay("mo"));      // short form names
        assertFalse(Day.isValidDay("f"));       // Single character day name
        assertFalse(Day.isValidDay("MON"));     // Capital letters
        assertFalse(Day.isValidDay("funday"));  // Wrong name

        // valid days
        assertTrue(Day.isValidDay("mon"));      //Valid tests
        assertTrue(Day.isValidDay("tue"));      //Valid tests
        assertTrue(Day.isValidDay("wed"));
        assertTrue(Day.isValidDay("thu"));      //Valid tests
        assertTrue(Day.isValidDay("fri"));
        assertTrue(Day.isValidDay("sat"));      //Valid tests
        assertTrue(Day.isValidDay("sun"));      //Valid tests
    }
}
