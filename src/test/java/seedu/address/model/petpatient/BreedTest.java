package seedu.address.model.petpatient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author chialejing
public class BreedTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Breed(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidBreed = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Breed(invalidBreed));
    }

    @Test
    public void isValidName() {
        // null breed
        Assert.assertThrows(NullPointerException.class, () -> Breed.isValidBreed(null));

        // invalid breed
        assertFalse(Breed.isValidBreed("")); // empty string
        assertFalse(Breed.isValidBreed(" ")); // spaces only
        assertFalse(Breed.isValidBreed("^")); // only non-alphanumeric characters
        assertFalse(Breed.isValidBreed("ragdoll*")); // contains non-alphanumeric characters
        assertFalse(Breed.isValidBreed("12345")); // numbers only
        assertFalse(Breed.isValidBreed("persian 234")); // alphanumeric characters

        // valid breed
        assertTrue(Breed.isValidBreed("poodle")); // alphabets only
        assertTrue(Breed.isValidBreed("Domestic Shorthair")); // with capital letters
        assertTrue(Breed.isValidBreed("Domestic Shorthair Persian Ragdoll")); // long breed
    }
}
