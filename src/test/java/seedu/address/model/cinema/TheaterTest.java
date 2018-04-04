package seedu.address.model.cinema;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
//@@author tinyjy94
public class TheaterTest {

    @Test
    public void constructor_invalidTheaterNumber_throwsIllegalArgumentException() {
        int invalidTheaterNumber = 0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Theater(invalidTheaterNumber));
    }

    @Test
    public void isValidTheaterNumber() {
        // invalid theater numbers
        assertFalse(Theater.isValidTheater("")); // empty string
        assertFalse(Theater.isValidTheater(" ")); // spaces only
        assertFalse(Theater.isValidTheater("phone")); // non-numeric
        assertFalse(Theater.isValidTheater("a9011p041")); // alphabets and digits
        assertFalse(Theater.isValidTheater("9312 1534")); // spaces within digits

        // valid theater numbers
        assertTrue(Theater.isValidTheater("1")); // 1 digit
        assertTrue(Theater.isValidTheater("123456")); // multiple digits
    }
}

