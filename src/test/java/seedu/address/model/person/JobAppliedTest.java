package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author kexiaowen
public class JobAppliedTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobApplied(null));
    }

    @Test
    public void constructor_invalidJobApplied_throwsIllegalArgumentException() {
        String invalidJobApplied = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JobApplied(invalidJobApplied));
    }

    @Test
    public void isValidJobApplied() {
        // null jobApplied
        Assert.assertThrows(NullPointerException.class, () -> JobApplied.isValidJobApplied(null));

        // invalid jobApplied
        assertFalse(JobApplied.isValidJobApplied("")); // empty string
        assertFalse(JobApplied.isValidJobApplied(" ")); // spaces only

        // valid jobApplied
        assertTrue(JobApplied.isValidJobApplied("software engineer")); // alphabets only
        assertTrue(JobApplied.isValidJobApplied("12345")); // numbers only
        assertTrue(JobApplied.isValidJobApplied("1 software engineer 2 DevOps")); // alphanumeric characters
        assertTrue(JobApplied.isValidJobApplied("Software Engineer")); // with capital letters
        assertTrue(JobApplied.isValidJobApplied("Software Engineer & Web Developer")); // long names
    }
}
