package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DetailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Detail(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidDetail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Detail(invalidDetail));
    }

    @Test
    public void isValidDetail() {
        // null detail
        Assert.assertThrows(NullPointerException.class, () -> Detail.isValidDetail(null));

        // invalid detail
        assertFalse(Detail.isValidDetail("")); // empty string
        assertFalse(Detail.isValidDetail(" ")); // spaces only
        assertFalse(Detail.isValidDetail("^")); // only non-alphanumeric characters
        assertFalse(Detail.isValidDetail("tennis*")); // contains non-alphanumeric characters

        // valid detail
        assertTrue(Detail.isValidDetail("likes tennis")); // alphabets only
        assertTrue(Detail.isValidDetail("12345")); // numbers only
        assertTrue(Detail.isValidDetail("has 3 dogs")); // alphanumeric characters
        assertTrue(Detail.isValidDetail("Likes tennis")); // with capital letters
    }
}
