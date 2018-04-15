package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author JoonKai1995
public class MatriculationNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MatriculationNumber(null));
    }

    @Test
    public void constructor_invalidMatricNumber_throwsIllegalArgumentException() {
        String invalidMatricNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MatriculationNumber(invalidMatricNumber));
    }

    @Test
    public void isValidMatricNumber() {
        // null matric number
        Assert.assertThrows(NullPointerException.class, () -> MatriculationNumber.isValidMatricNumber(null));

        // invalid matric numbers
        assertFalse(MatriculationNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatriculationNumber.isValidMatricNumber(" ")); // spaces only
        assertFalse(MatriculationNumber.isValidMatricNumber("91")); // only numbers
        assertFalse(MatriculationNumber.isValidMatricNumber("matricNumber")); // non-numeric
        assertFalse(MatriculationNumber.isValidMatricNumber("E0000000I")); // first letter is not A or U
        assertFalse(MatriculationNumber.isValidMatricNumber("A00000000E")); // More than 8 digits
        assertFalse(MatriculationNumber.isValidMatricNumber("a0000000I")); // first character not capitalised
        assertFalse(MatriculationNumber.isValidMatricNumber("A00000000t")); // last character not capitalised

        // valid matric numbers
        assertTrue(MatriculationNumber.isValidMatricNumber("A0156672X")); // Starting with A
        assertTrue(MatriculationNumber.isValidMatricNumber("U4812163G")); // Starting with U
    }
}
