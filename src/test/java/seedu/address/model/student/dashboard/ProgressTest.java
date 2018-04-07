package seedu.address.model.student.dashboard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProgressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Progress(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidProgress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Progress(invalidProgress));
    }

    @Test
    public void isValidProgress() {
        // null progress
        Assert.assertThrows(NullPointerException.class, () -> Progress.isValidProgress(null));

        // invalid format
        assertFalse(Progress.isValidProgress("1/"));
        assertFalse(Progress.isValidProgress("/1"));
        assertFalse(Progress.isValidProgress("1"));

        // invalid values
        assertFalse(Progress.isValidProgress("2/1")); // number completed more than total completed
        assertFalse(Progress.isValidProgress("2/0")); // number completed more than total completed
        assertFalse(Progress.isValidProgress("-2/2")); // negative number completed
        assertFalse(Progress.isValidProgress("-2/-1")); // negative number completed and total completed

        // valid progress
        assertTrue(Progress.isValidProgress("1/2"));
        assertTrue(Progress.isValidProgress("0/2"));
        assertTrue(Progress.isValidProgress("0/0"));
    }


}
