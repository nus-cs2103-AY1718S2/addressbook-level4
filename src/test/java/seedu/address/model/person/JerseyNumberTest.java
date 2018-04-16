package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author lithiumlkid
public class JerseyNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JerseyNumber(null));
    }

    @Test
    public void constructor_invalidJerseyNumber_throwsIllegalArgumentException() {
        String invalidJerseyNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JerseyNumber(invalidJerseyNumber));
    }

    @Test
    public void isValidJerseyNumber() {
        // null jerseyNumber number
        Assert.assertThrows(NullPointerException.class, () -> JerseyNumber.isValidJerseyNumber(null));

        // invalid jerseyNumber numbers
        assertFalse(JerseyNumber.isValidJerseyNumber("")); // empty string
        assertFalse(JerseyNumber.isValidJerseyNumber(" ")); // spaces only
        assertFalse(JerseyNumber.isValidJerseyNumber("-1")); // less than 0
        assertFalse(JerseyNumber.isValidJerseyNumber("100")); // larger than 99
        assertFalse(JerseyNumber.isValidJerseyNumber("1a")); // alphabets with digits
        assertFalse(JerseyNumber.isValidJerseyNumber("1 1")); // spaces within digits

        // valid jerseyNumber numbers
        assertTrue(JerseyNumber.isValidJerseyNumber("0")); // within 0 - 99
        assertTrue(JerseyNumber.isValidJerseyNumber("99"));
    }

    @Test
    public void isEqualJerseyNumber() {
        JerseyNumber x  = new JerseyNumber("0");
        JerseyNumber y = new JerseyNumber("0");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}

