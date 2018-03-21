package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatusIndex_throwsIllegalArgumentException() {
        int invalidStatusIndex = 0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatusIndex));
    }

    @Test
    public void isValidStatus() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        //invalid status
        assertFalse(Status.isValidStatus(0)); //index out of range
        assertFalse(Status.isValidStatus(Status.STATUS_TYPE_CONUT + 1));
        // valid status
        assertTrue(Status.isValidStatus(Status.INDEX_STATUS_REJECTED));
        assertTrue(Status.isValidStatus(Status.INDEX_STATUS_INTERVIEW_SECOND_ROUND));
        assertTrue(Status.isValidStatus(Status.INDEX_STATUS_OFFERED));
        assertTrue(Status.isValidStatus(Status.INDEX_STATUS_WITHDRAWN));
    }

    @Test
    public void constructor_validStatus_rightColor() {
        Status s = new Status(Status.INDEX_STATUS_INTERVIEW_FIRST_ROUND);
        assertEquals(Status.COLOR_INTERVIEW_FIRST_ROUND, s.color);
        s = new Status(Status.INDEX_STATUS_WAITLIST);
        assertEquals(Status.COLOR_WAITLIST, s.color);
        s = new Status(Status.INDEX_STATUS_ACCEPTED);
        assertEquals(Status.COLOR_ACCEPTED, s.color);
        s = new Status();
        assertEquals(Status.COLOR_NEW, s.color);
    }

    @Test
    public void equals() {
        Status status1 = new Status();
        Status status2 = new Status(Status.STATUS_NEW);
        assertEquals(status1, status2);
    }
}
