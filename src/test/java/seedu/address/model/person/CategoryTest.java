package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
//@@author shookshire
public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Category(invalidSubject));
    }

    @Test
    public void isValidCategory() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // invalid subject
        assertFalse(Category.isValidCategory("")); // empty string
        assertFalse(Category.isValidCategory(" ")); // spaces only
        assertFalse(Category.isValidCategory("a")); // character apart from s or t
        assertFalse(Category.isValidCategory("st")); // more than just character s or t

        // valid subject
        assertTrue(Category.isValidCategory("s"));
        assertTrue(Category.isValidCategory("t"));
    }
}
