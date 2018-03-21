package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.paint.Color;
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
        assertFalse(Status.isValidStatus(8));
        // valid status
        assertTrue(Status.isValidStatus(1));
        assertTrue(Status.isValidStatus(2));
        assertTrue(Status.isValidStatus(3));
        assertTrue(Status.isValidStatus(4));
        assertTrue(Status.isValidStatus(5));
        assertTrue(Status.isValidStatus(6));
        assertTrue(Status.isValidStatus(7));
    }

    @Test
    public void constructor_validStatus_rightColor() {
        Status s = new Status(1);
        assertEquals(Color.GREY, s.color);
        s = new Status(5);
        assertEquals(Color.BROWN, s.color);
        s = new Status(7);
        assertEquals(Color.GREEN, s.color);
        s = new Status();
        assertEquals(Color.GREY, s.color);
    }

    @Test
    public void equals() {
        Status status1 = new Status();
        Status status2 = new Status("new");
        assertEquals(status1, status2);
    }
}
