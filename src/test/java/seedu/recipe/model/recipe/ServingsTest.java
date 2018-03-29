//@@author kokonguyen191
package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class ServingsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Servings(null));
    }

    @Test
    public void constructor_invalidServings_throwsIllegalArgumentException() {
        String invalidServings = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Servings(invalidServings));
    }

    @Test
    public void isValidServings() {
        // null servings number
        Assert.assertThrows(NullPointerException.class, () -> Servings.isValidServings(null));

        // invalid servings number
        assertFalse(Servings.isValidServings("")); // empty string
        assertFalse(Servings.isValidServings(" ")); // spaces only
        assertFalse(Servings.isValidServings("NaN")); // not a number
        assertFalse(Servings.isValidServings("BLAHBLAHBLAH")); // non-numeric
        assertFalse(Servings.isValidServings(".1111..")); // non-numeric

        // valid servings number
        assertTrue(Servings.isValidServings("2"));
        assertTrue(Servings.isValidServings("10"));
    }
}
