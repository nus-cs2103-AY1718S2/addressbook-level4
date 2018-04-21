package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author kexiaowen
public class JobAppliedTest {

    private final JobApplied jobApplied = new JobApplied("Software Engineer");

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

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(jobApplied.equals(jobApplied));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        JobApplied jobAppliedCopy = new JobApplied("Software Engineer");
        assertTrue(jobApplied.equals(jobAppliedCopy));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        assertFalse(jobApplied.equals(1));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(jobApplied.equals(null));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        JobApplied differentJobApplied = new JobApplied("Front-end Developer");
        assertFalse(jobApplied.equals(differentJobApplied));
    }
}
