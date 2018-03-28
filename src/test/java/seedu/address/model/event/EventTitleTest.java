package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventTitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventTitle(null));
    }

    @Test
    public void constructor_invalidEventTitle_throwsIllegalArgumentException() {
        String invalidEventTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventTitle(invalidEventTitle));
    }

    @Test
    public void isValidOrderInformation() {
        // null event title
        Assert.assertThrows(NullPointerException.class, () -> EventTitle.isValidEventTitle(null));

        // invalid event title
        assertFalse(EventTitle.isValidEventTitle("")); // empty string
        assertFalse(EventTitle.isValidEventTitle(" ")); // spaces only
        assertFalse(EventTitle.isValidEventTitle("Too long sentences exceeding forty characters"));
        // Exceed character limit

        // valid event title
        assertTrue(EventTitle.isValidEventTitle("Meet with bosses"));
        assertTrue(EventTitle.isValidEventTitle("Meet Client for stocks"));
        assertTrue(EventTitle.isValidEventTitle("Confectionery Boxes Order"));
    }
}
