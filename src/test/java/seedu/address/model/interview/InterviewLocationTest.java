//author@@ deeheenguyen
package seedu.address.model.interview;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class InterviewLocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new InterviewLocation(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidInterviewLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new InterviewLocation(invalidInterviewLocation));
    }

    @Test
    public void isValidLocation() {
        // null location
        Assert.assertThrows(NullPointerException.class, () -> InterviewLocation.isValidLocation(null));

        // invalid location
        assertFalse(InterviewLocation.isValidLocation("")); // empty string
        assertFalse(InterviewLocation.isValidLocation(" ")); // spaces only

        // valid addresses
        assertTrue(InterviewLocation.isValidLocation("NUS")); // Only alphabetic characters
        assertTrue(InterviewLocation.isValidLocation("UTOWN-NUS ")); // Contains non-alphanumeric characters
    }
}
