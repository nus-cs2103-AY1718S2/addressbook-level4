package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TimeTableLinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TimeTableLink(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidLink = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TimeTableLink(invalidLink));
    }

    @Test
    public void isValidLink() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> TimeTableLink.isValidLink(null));

        // blank email
        assertFalse(TimeTableLink.isValidLink("")); // empty string
        assertFalse(TimeTableLink.isValidLink(" ")); // spaces only

        // missing parts
        assertFalse(TimeTableLink.isValidLink("MYwiD")); // missing http://modn.us/ URL head
        assertFalse(TimeTableLink.isValidLink("http://modn.nus/")); // missing trailing part

        // invalid parts
        assertFalse(TimeTableLink.isValidLink("https://modsn.us/MYwiD")); // https instead of http
        assertFalse(TimeTableLink.isValidLink("http://mods.nus/MYwiD")); // incorrect URL
        assertFalse(TimeTableLink.isValidLink("http:// modsn.us/MYwiD")); // spaces in URL
        assertFalse(TimeTableLink.isValidLink("http://modsn.us/MYw iD")); // spaces in trailing part
        assertFalse(TimeTableLink.isValidLink(" http://modsn.us/MYwiD")); // leading space
        assertFalse(TimeTableLink.isValidLink("http://modsn.us/MYwiD ")); // trailing space
        assertFalse(TimeTableLink.isValidLink("http://modsn.us//MYwiD")); // double '/' symbol
        assertFalse(TimeTableLink.isValidLink("http://modsn.us/MYw.iD")); // '.' symbol in trailing part

        // valid email
        assertTrue(TimeTableLink.isValidLink("http://modsn.us/MYwiD"));
    }
}
