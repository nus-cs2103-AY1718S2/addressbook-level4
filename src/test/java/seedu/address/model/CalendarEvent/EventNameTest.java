package seedu.address.model.CalendarEvent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author wayneong95

public class EventNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidEventName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventName(invalidEventName));
    }

    @Test
    public void isValidEventName() {
        // null eventName
        Assert.assertThrows(NullPointerException.class, () -> EventName.isValidEventName(null));

        // invalid eventName
        assertFalse(EventName.isValidEventName("")); // empty string
        assertFalse(EventName.isValidEventName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidEventName("Workout*")); // contains non-alphanumeric characters

        // valid eventName
        assertTrue(EventName.isValidEventName("weights training")); // alphabets only
        assertTrue(EventName.isValidEventName("12345")); // numbers only
        assertTrue(EventName.isValidEventName("2nd weight training")); // alphanumeric characters
        assertTrue(EventName.isValidEventName("Weight Training")); // with capital letters
        assertTrue(EventName.isValidEventName("Weight Training with Personal Trainer")); // long names
    }
}
