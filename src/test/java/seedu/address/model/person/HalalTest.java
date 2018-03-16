package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class HalalTest {

    @Test
    public void constructor_null_constructionSuccessValueNull() {
        assertNull(new Halal(null).value);
    }

    @Test
    public void constructor_invalidHalal_throwsIllegalArgumentException() {
        String invalidHalal = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Halal(invalidHalal));
    }

    @Test
    public void isValidHalal() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Halal.isValidHalal(null));

        // invalid phone numbers
        assertFalse(Halal.isValidHalal("")); // empty string
        assertFalse(Halal.isValidHalal(" ")); // spaces only
        assertFalse(Halal.isValidHalal("^")); // only non-alphanumeric characters
        assertFalse(Halal.isValidHalal("phone")); // unrelated
        assertFalse(Halal.isValidHalal("halal*")); // contains non-alphanumeric characters
        assertFalse(Halal.isValidHalal("93121534")); // numbers

        // valid phone numbers
        assertTrue(Halal.isValidHalal("Halal")); // exactly Halal
        assertTrue(Halal.isValidHalal("Non-halal")); // exactly Non-halal
    }
}
