//@@author deeheenguyen
package seedu.address.model.interview;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class InterviewTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new InterviewTitle(null));
    }

    @Test
    public void constructor_invalidJobTitle_throwsIllegalArgumentException() {
        String invalidInterviewTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new InterviewTitle(invalidInterviewTitle));
    }

    @Test
    public void isValidInterviewTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> InterviewTitle.isValidTitle(null));

        // invalid name
        assertFalse(InterviewTitle.isValidTitle("")); // empty string
        assertFalse(InterviewTitle.isValidTitle(" ")); // spaces only
        assertFalse(InterviewTitle.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(InterviewTitle.isValidTitle("$$*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(InterviewTitle.isValidTitle("backend interview")); // alphabets only
        assertTrue(InterviewTitle.isValidTitle("11111")); // numbers only
        assertTrue(InterviewTitle.isValidTitle("SE interview")); // alphanumeric characters
        assertTrue(InterviewTitle.isValidTitle("INTERNSHIP INTERNVIEW")); // with capital letters
    }
}

