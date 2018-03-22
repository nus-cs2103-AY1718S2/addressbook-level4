//@@Author kokonguyen191
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
        // null cookingTime number
        Assert.assertThrows(NullPointerException.class, () -> CookingTime.isValidCookingTime(null));

        // invalid cookingTime numbers
        assertFalse(CookingTime.isValidCookingTime("")); // empty string
        assertFalse(CookingTime.isValidCookingTime(" ")); // spaces only
        assertFalse(CookingTime.isValidCookingTime("NaN")); // not a number
        assertFalse(CookingTime.isValidCookingTime("cookingTime")); // non-numeric
        assertFalse(CookingTime.isValidCookingTime("9011p041")); // unknown character p
        assertFalse(CookingTime.isValidCookingTime("1h  1534m")); // more spaces than needed

        // valid cookingTime numbers
        assertTrue(CookingTime.isValidCookingTime("1h20m"));
        assertTrue(CookingTime.isValidCookingTime("1 hour 20 min"));
        assertTrue(CookingTime.isValidCookingTime("1 hour 20 mins"));
        assertTrue(CookingTime.isValidCookingTime("1 hour 20 minutes"));
        assertTrue(CookingTime.isValidCookingTime("80"));
        assertTrue(CookingTime.isValidCookingTime("80m"));
        assertTrue(CookingTime.isValidCookingTime("80min"));
        assertTrue(CookingTime.isValidCookingTime("80 mins"));
        assertTrue(CookingTime.isValidCookingTime("2h20m"));
        assertTrue(CookingTime.isValidCookingTime("2 hours 20 mins"));
        assertTrue(CookingTime.isValidCookingTime("2 hours 20 minutes"));
    }
}
