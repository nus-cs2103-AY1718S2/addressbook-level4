package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    //@@author aussiroth
    @Test
    public void isValidTagType() {
        //incorrect string
        assertFalse(Tag.isValidTagType("NOTATYPE"));
        //correct type
        assertTrue(Tag.isValidTagType("SUBJECT"));
        assertTrue(Tag.isValidTagType("STATUS"));
    }

    @Test
    public void toStringMethod() {
        Tag target = new Tag("Math", Tag.AllTagTypes.SUBJECT);
        assertTrue("[Math]".equals(target.toString()));
    }
}
