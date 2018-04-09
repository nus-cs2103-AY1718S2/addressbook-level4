package seedu.address.model.petpatient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author chialejing
public class SpeciesTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Species(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidSpecies = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Species(invalidSpecies));
    }

    @Test
    public void isValidName() {
        // null species
        Assert.assertThrows(NullPointerException.class, () -> Species.isValidSpecies(null));

        // invalid species
        assertFalse(Species.isValidSpecies("")); // empty string
        assertFalse(Species.isValidSpecies(" ")); // spaces only
        assertFalse(Species.isValidSpecies("^")); // only non-alphanumeric characters
        assertFalse(Species.isValidSpecies("cat*")); // contains non-alphanumeric characters
        assertFalse(Species.isValidSpecies("12345")); // numbers only
        assertFalse(Species.isValidSpecies("cat 234")); // alphanumeric characters

        // valid species
        assertTrue(Species.isValidSpecies("dog")); // alphabets only
        assertTrue(Species.isValidSpecies("Some Species")); // with capital letters
        assertTrue(Species.isValidSpecies("Some Species That Has Very Long Naming Term")); // long species
    }
}
