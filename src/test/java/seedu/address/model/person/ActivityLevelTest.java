package seedu.address.model.person;
//@@author hypertun

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ActivityLevelTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ActivityLevel(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidActivityLevel = "1.44";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ActivityLevel(invalidActivityLevel));
    }

    @Test
    public void isValidAge() {
        // null ActivityLevel
        Assert.assertThrows(NullPointerException.class, () -> ActivityLevel.isValidActivityLevel(null));

        // invalid activityLevel
        assertFalse(ActivityLevel.isValidActivityLevel("")); // empty string
        assertFalse(ActivityLevel.isValidActivityLevel(" ")); // space only
        assertFalse(ActivityLevel.isValidActivityLevel("activityLevel")); // non-numeric
        assertFalse(ActivityLevel.isValidActivityLevel("9p.2")); // alphabets within digits
        assertFalse(ActivityLevel.isValidActivityLevel("9 3")); // spaces within digits
        assertFalse(ActivityLevel.isValidActivityLevel("1.40")); // wrong number

        // valid activityLevel numbers
        assertTrue(ActivityLevel.isValidActivityLevel("1.2")); // basal
        assertTrue(ActivityLevel.isValidActivityLevel("1.375"));
        assertTrue(ActivityLevel.isValidActivityLevel("1.9")); // active
    }
}
