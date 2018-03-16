package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class VegetarianTest {

    @Test
    public void constructor_invalidVegetarian_throwsIllegalArgumentException() {
        String invalidVegetarian = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Vegetarian(invalidVegetarian));
    }

    @Test
    public void isValidVegetarian() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Vegetarian.isValidVegetarian(null));

        // invalid phone numbers
        assertFalse(Vegetarian.isValidVegetarian("")); // empty string
        assertFalse(Vegetarian.isValidVegetarian(" ")); // spaces only
        assertFalse(Vegetarian.isValidVegetarian("^")); // only non-alphanumeric characters
        assertFalse(Vegetarian.isValidVegetarian("phone")); // unrelated
        assertFalse(Vegetarian.isValidVegetarian("vegetarian*")); // contains non-alphanumeric characters
        assertFalse(Vegetarian.isValidVegetarian("93121534")); // numbers

        // valid phone numbers
        assertTrue(Vegetarian.isValidVegetarian("Vegetarian")); // exactly Halal
        assertTrue(Vegetarian.isValidVegetarian("Non-vegetarian")); // exactly Non-halal
    }
}
