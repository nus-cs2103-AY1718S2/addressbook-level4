package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

//@@author aussiroth
public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void isValidStatus() {

        // invalid statuses
        assertFalse(Status.isValidStatus("notastatus")); // not listed statuses
        assertFalse(Status.isValidStatus("somewhatmatched"));

        // valid status
        assertTrue(Status.isValidStatus("not Matched"));
        assertTrue(Status.isValidStatus("matched"));
    }

    @Test
    public void checkStatusEquality() {
        //test status against non-status type
        assertFalse(new Status("matched").equals(null));
        assertFalse(new Status("matched").equals(new Tag("matched")));
        //test correctly returns equal if status string is the same
        assertTrue(new Status("matched").equals(new Status("m")));
        assertTrue(new Status("not matched").equals(new Status("nm")));
    }

    @Test
    public void checkStatusHashCode() {
        Status status = new Status("not Matched");
        assertTrue(status.hashCode() == status.value.hashCode());
        status = new Status("matched");
        assertTrue(status.hashCode() == status.value.hashCode());
    }

}
