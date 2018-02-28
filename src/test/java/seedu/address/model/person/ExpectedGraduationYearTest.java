package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpectedGraduationYearTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpectedGraduationYear(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidExpectedGraduationYear = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Phone(invalidExpectedGraduationYear));
    }

    @Test
    public void isValidPhone() {
        // null expectedGraduationYear
        Assert.assertThrows(NullPointerException.class, () ->
                ExpectedGraduationYear.isValidExpectedGraduationYear(null));

        // invalid phone numbers
        assertFalse(ExpectedGraduationYear.isValidExpectedGraduationYear("")); // empty string
        assertFalse(ExpectedGraduationYear.isValidExpectedGraduationYear(" ")); // spaces only
        assertFalse(ExpectedGraduationYear.isValidExpectedGraduationYear("91")); // less than 4 numbers
        assertFalse(ExpectedGraduationYear.isValidExpectedGraduationYear("year2018")); // non-numeric
        assertFalse(ExpectedGraduationYear.isValidExpectedGraduationYear("-2018")); // negative symbol before digits
        assertFalse(ExpectedGraduationYear.isValidExpectedGraduationYear("201 8")); // spaces within digits
        assertFalse(ExpectedGraduationYear.isValidExpectedGraduationYear("2018.5")); // year and month
        // valid phone numbers
        assertTrue(ExpectedGraduationYear.isValidExpectedGraduationYear("2018")); // exactly 3 numbers
        assertTrue(ExpectedGraduationYear.isValidExpectedGraduationYear("2025"));
    }
}
