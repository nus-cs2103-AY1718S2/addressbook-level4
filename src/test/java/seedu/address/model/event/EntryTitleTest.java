package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author SuxianAlicia
public class EntryTitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EntryTitle(null));
    }

    @Test
    public void constructor_invalidEventTitle_throwsIllegalArgumentException() {
        String invalidEventTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EntryTitle(invalidEventTitle));
    }

    @Test
    public void isValidEventTitle() {
        // null event title
        Assert.assertThrows(NullPointerException.class, () -> EntryTitle.isValidEntryTitle(null));

        // invalid event title
        assertFalse(EntryTitle.isValidEntryTitle("")); // empty string
        assertFalse(EntryTitle.isValidEntryTitle(" ")); // spaces only

        // valid event title
        assertTrue(EntryTitle.isValidEntryTitle("Meet with bosses"));
        assertTrue(EntryTitle.isValidEntryTitle("Meet Client for stocks"));
        assertTrue(EntryTitle.isValidEntryTitle("Confectionery Boxes Order"));
    }
}
