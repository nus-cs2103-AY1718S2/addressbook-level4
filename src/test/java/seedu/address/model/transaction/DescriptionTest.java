package seedu.address.model.transaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
//@@author ongkc
public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        Assert.assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid description
        assertTrue(Description.isValidDescription("peter jack")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("lunch meal")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Dinner Meal")); // with capital letters
        assertTrue(Description.isValidDescription("expense for meal and travel")); // long description
        assertTrue(Description.isValidDescription("*food*")); // contain non-alphanumeric characters
    }
}
