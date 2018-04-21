package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author tanhengyeow
public class GradePointAverageTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GradePointAverage(null));
    }

    @Test
    public void constructor_invalidGradePointAverage_throwsIllegalArgumentException() {
        String invalidGradePointAverage = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GradePointAverage(invalidGradePointAverage));
    }

    @Test
    public void isValidGradePointAverage() {
        // null gradePointAverage
        Assert.assertThrows(NullPointerException.class, () ->
                GradePointAverage.isValidGradePointAverage(null));

        // invalid gradePointAverage
        assertFalse(GradePointAverage.isValidGradePointAverage(" ")); // spaces only
        assertFalse(GradePointAverage.isValidGradePointAverage("5.10")); // not in range
        assertFalse(GradePointAverage.isValidGradePointAverage("test")); // non-numeric
        assertFalse(GradePointAverage.isValidGradePointAverage("-4.00")); // negative number

        // valid gradePointAverage
        assertTrue(GradePointAverage.isValidGradePointAverage("4.93"));
        assertTrue(GradePointAverage.isValidGradePointAverage("4.75"));
    }
}
