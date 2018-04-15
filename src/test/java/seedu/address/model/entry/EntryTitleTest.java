package seedu.address.model.entry;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EntryTitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EntryTitle(null));
    }

    @Test
    public void constructor_invalidEntryTitle_throwsIllegalArgumentException() {
        String invalidEntryTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EntryTitle(invalidEntryTitle));
    }

    @Test
    public void isValidEntryTitle() {

        // null entry title
        Assert.assertThrows(NullPointerException.class, () -> EntryTitle.isValidEntryTitle(null));

        // invalid entry title
        assertFalse(EntryTitle.isValidEntryTitle("")); // empty string
        assertFalse(EntryTitle.isValidEntryTitle(" ")); // spaces only

        // valid entry title
        assertTrue(EntryTitle.isValidEntryTitle("Meet with bosses"));
        assertTrue(EntryTitle.isValidEntryTitle("Meet Client for stocks"));
        assertTrue(EntryTitle.isValidEntryTitle("Confectionery Boxes Order"));
    }
}
