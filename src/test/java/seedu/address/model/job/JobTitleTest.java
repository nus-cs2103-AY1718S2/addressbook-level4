package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author ChengSashankh
public class JobTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobTitle(null));
    }

    @Test
    public void constructor_invalidJobTitle_throwsIllegalArgumentException() {
        String invalidJobTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JobTitle(invalidJobTitle));
    }

    @Test
    public void isValidJobTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> JobTitle.isValidTitle(null));

        // invalid name
        assertFalse(JobTitle.isValidTitle("")); // empty string
        assertFalse(JobTitle.isValidTitle(" ")); // spaces only
        assertFalse(JobTitle.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(JobTitle.isValidTitle("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(JobTitle.isValidTitle("backend software engineer")); // alphabets only
        assertTrue(JobTitle.isValidTitle("20180001")); // numbers only
        assertTrue(JobTitle.isValidTitle("Software Engineer Intern 2018")); // alphanumeric characters
        assertTrue(JobTitle.isValidTitle("Backend software Engineer")); // with capital letters
        assertTrue(JobTitle.isValidTitle("Summer Software Engineering Intern Cloud Operations")); // long names
    }
}
//@@author
