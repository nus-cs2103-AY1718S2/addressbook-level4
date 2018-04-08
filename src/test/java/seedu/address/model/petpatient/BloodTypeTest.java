package seedu.address.model.petpatient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author chialejing
public class BloodTypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidBloodType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null blood type
        Assert.assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood type
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // one space only
        assertFalse(BloodType.isValidBloodType("       ")); // spaces only

        // valid blood type
        assertTrue(BloodType.isValidBloodType("dea")); // alphabets only
        assertTrue(BloodType.isValidBloodType("12345")); // numbers only
        assertTrue(BloodType.isValidBloodType("dea 1")); // alphanumeric characters
        assertTrue(BloodType.isValidBloodType("DEA 1")); // with capital letters
        assertTrue(BloodType.isValidBloodType("Some Blood Type That Has A Very Long Name")); // long
        assertTrue(BloodType.isValidBloodType("DEA 1.0+")); // with special characters
        assertTrue(BloodType.isValidBloodType(
                "Some Blood Type That Has A Very Long Name DEA 1.0-")); // long with special character
    }
}
