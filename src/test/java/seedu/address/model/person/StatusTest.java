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
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "0";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        //invalid status
        assertFalse(Status.isValidStatus("0")); //index out of range
        assertFalse(Status.isValidStatus("8"));
        assertFalse(Status.isValidStatus("I")); //not a valid index
        // valid status
        assertTrue(Status.isValidStatus("1"));
        assertTrue(Status.isValidStatus("2"));
        assertTrue(Status.isValidStatus("3"));
        assertTrue(Status.isValidStatus("4"));
        assertTrue(Status.isValidStatus("5"));
        assertTrue(Status.isValidStatus("6"));
        assertTrue(Status.isValidStatus("7"));
    }

    @Test
    public void constructor_validStatus_rightColor() {
        Status s = new Status("1");
        assertEquals(Color.GREY, s.color);
        s = new Status("5");
        assertEquals(Color.BROWN, s.color);
        s = new Status("7");
        assertEquals(Color.GREEN, s.color);
    }
}
