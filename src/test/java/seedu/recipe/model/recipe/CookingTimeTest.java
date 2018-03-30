//@@author kokonguyen191
package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class CookingTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CookingTime(null));
    }

    @Test
    public void constructor_invalidCookingTime_throwsIllegalArgumentException() {
        String invalidCookingTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CookingTime(invalidCookingTime));
    }

    @Test
    public void isValidCookingTime() {
        // null CookingTime
        Assert.assertThrows(NullPointerException.class, () -> CookingTime.isValidCookingTime(null));

        // invalid CookingTime
        assertFalse(CookingTime.isValidCookingTime("")); // empty string
        assertFalse(CookingTime.isValidCookingTime(" ")); // spaces only
        assertFalse(CookingTime.isValidCookingTime("NaN")); // not a number
        assertFalse(CookingTime.isValidCookingTime("BLAHBLAHBLAH")); // non-numeric
        assertFalse(CookingTime.isValidCookingTime("123aaaa55555")); // unknown character p
        assertFalse(CookingTime.isValidCookingTime("1h  6666m")); // more spaces than needed

        // valid CookingTime
        assertTrue(CookingTime.isValidCookingTime("1h10m"));
        assertTrue(CookingTime.isValidCookingTime("1 hour 10 min"));
        assertTrue(CookingTime.isValidCookingTime("1 hour 10 mins"));
        assertTrue(CookingTime.isValidCookingTime("1 hour 10 minutes"));
        assertTrue(CookingTime.isValidCookingTime("70"));
        assertTrue(CookingTime.isValidCookingTime("70m"));
        assertTrue(CookingTime.isValidCookingTime("70min"));
        assertTrue(CookingTime.isValidCookingTime("70 mins"));
        assertTrue(CookingTime.isValidCookingTime("5h20m"));
        assertTrue(CookingTime.isValidCookingTime("5 hours 20 mins"));
        assertTrue(CookingTime.isValidCookingTime("5 hours 20 minutes"));
    }
}
