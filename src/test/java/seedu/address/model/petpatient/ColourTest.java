package seedu.address.model.petpatient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author chialejing
public class ColourTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Colour(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidBreed = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Colour(invalidBreed));
    }

    @Test
    public void isValidName() {
        // null colour
        Assert.assertThrows(NullPointerException.class, () -> Colour.isValidColour(null));

        // invalid colour
        assertFalse(Colour.isValidColour("")); // empty string
        assertFalse(Colour.isValidColour(" ")); // spaces only
        assertFalse(Colour.isValidColour("^")); // only non-alphanumeric characters
        assertFalse(Colour.isValidColour("white*")); // contains non-alphanumeric characters
        assertFalse(Colour.isValidColour("12345")); // numbers only
        assertFalse(Colour.isValidColour("black 234")); // alphanumeric characters

        // valid colour
        assertTrue(Colour.isValidColour("brown")); // alphabets only
        assertTrue(Colour.isValidColour("Orange Brown")); // with capital letters
        assertTrue(Colour.isValidColour("Orange Brown White Red Blue Yellow Green")); // long colour
    }
}
