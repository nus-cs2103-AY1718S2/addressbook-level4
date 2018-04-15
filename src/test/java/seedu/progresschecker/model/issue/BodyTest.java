package seedu.progresschecker.model.issue;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.progresschecker.model.issues.Body;
import seedu.progresschecker.testutil.Assert;

//@@author adityaa1998
public class BodyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Body(null));
    }

    @Test
    public void isValidBody() {
        // null name
        assertFalse(Body.isValidBody(null));

        // valid name
        assertTrue(Body.isValidBody("peter jack")); // alphabets only
        assertTrue(Body.isValidBody("12345")); // numbers only
        assertTrue(Body.isValidBody("peter the 2nd")); // alphanumeric characters
        assertTrue(Body.isValidBody("Capital Tan")); // with capital letters
        assertTrue(Body.isValidBody("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Body.isValidBody("^")); // only non-alphanumeric characters
        assertTrue(Body.isValidBody("peter*")); // contains non-alphanumeric characters
    }
}
