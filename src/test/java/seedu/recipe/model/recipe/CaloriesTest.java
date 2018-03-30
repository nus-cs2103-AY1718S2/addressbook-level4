//@@author kokonguyen191
package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class CaloriesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Calories(null));
    }

    @Test
    public void constructor_invalidCalories_throwsIllegalArgumentException() {
        String invalidCalories = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Calories(invalidCalories));
    }

    @Test
    public void isValidCalories() {
        // null calories number
        Assert.assertThrows(NullPointerException.class, () -> Calories.isValidCalories(null));

        // invalid calories number
        assertFalse(Calories.isValidCalories("")); // empty string
        assertFalse(Calories.isValidCalories(" ")); // spaces only
        assertFalse(Calories.isValidCalories("NaN")); // not a number
        assertFalse(Calories.isValidCalories("BLAHBLAHBLAH")); // non-numeric
        assertFalse(Calories.isValidCalories(".1111..")); // non-numeric

        // valid calories number
        assertTrue(Calories.isValidCalories("1000"));
        assertTrue(Calories.isValidCalories("1111"));
    }
}
