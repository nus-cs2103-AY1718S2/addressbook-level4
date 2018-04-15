//@@author kokonguyen191
package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class PreparationTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PreparationTime(null));
    }

    @Test
    public void constructor_invalidPreparationTime_throwsIllegalArgumentException() {
        String invalidPreparationTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PreparationTime(invalidPreparationTime));
    }

    @Test
    public void isValidPreparationTime() {
        // null PreparationTime
        Assert.assertThrows(NullPointerException.class, () -> PreparationTime.isValidPreparationTime(null));

        // invalid PreparationTime
        assertFalse(PreparationTime.isValidPreparationTime("")); // empty string
        assertFalse(PreparationTime.isValidPreparationTime(" ")); // spaces only
        assertFalse(PreparationTime.isValidPreparationTime("NaN")); // not a number
        assertFalse(PreparationTime.isValidPreparationTime("preparationTime")); // non-numeric
        assertFalse(PreparationTime.isValidPreparationTime("9011p041")); // unknown character p
        assertFalse(PreparationTime.isValidPreparationTime("1h  1534m")); // more spaces than needed

        // valid PreparationTime
        assertTrue(PreparationTime.isValidPreparationTime("1h20m"));
        assertTrue(PreparationTime.isValidPreparationTime("1 hour 20 min"));
        assertTrue(PreparationTime.isValidPreparationTime("1 hour 20 mins"));
        assertTrue(PreparationTime.isValidPreparationTime("1 hour 20 minutes"));
        assertTrue(PreparationTime.isValidPreparationTime("80"));
        assertTrue(PreparationTime.isValidPreparationTime("80m"));
        assertTrue(PreparationTime.isValidPreparationTime("80min"));
        assertTrue(PreparationTime.isValidPreparationTime("80 mins"));
        assertTrue(PreparationTime.isValidPreparationTime("2h20m"));
        assertTrue(PreparationTime.isValidPreparationTime("2 hours 20 mins"));
        assertTrue(PreparationTime.isValidPreparationTime("2 hours 20 minutes"));
    }
}
