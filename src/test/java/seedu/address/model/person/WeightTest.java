package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author wayneong95

public class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        String invalidWeight = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    public void isValidWeight() {
        // null weight
        Assert.assertThrows(NullPointerException.class, () -> Weight.isValidWeight(null));

        // invalid weight
        assertFalse(Weight.isValidWeight("")); // empty string
        assertFalse(Weight.isValidWeight(" ")); // spaces only
        assertFalse(Weight.isValidWeight("weight")); // non-numeric
        assertFalse(Weight.isValidWeight("9p.2")); // alphabets within digits
        assertFalse(Weight.isValidWeight("9 3")); // spaces within digits

        // valid weight numbers
        assertTrue(Weight.isValidWeight("91.1")); // exactly 3 numbers
        assertTrue(Weight.isValidWeight("95"));
        assertTrue(Weight.isValidWeight("105")); // heavy weight
    }
}
