package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName_null_throwsNullPointerException() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isValidTagColor() {
        assertTrue(Tag.isValidTagColor(VALID_TAG_COLOR_RED));
        assertFalse(Tag.isValidTagColor(INVALID_TAG_COLOR));
    }
}
