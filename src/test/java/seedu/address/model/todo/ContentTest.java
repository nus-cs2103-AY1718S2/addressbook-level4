package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ContentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Content(null));
    }

    @Test
    public void constructor_invalidContent_throwsIllegalArgumentException() {
        String invalidContent = "&";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Content(invalidContent));
    }

    @Test
    public void isValidContent() {
        // null content
        Assert.assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid content
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only
        assertFalse(Content.isValidContent("^")); // only non-alphanumeric characters
        assertFalse(Content.isValidContent("Something to do*")); // contains non-alphanumeric characters

        // valid content
        assertTrue(Content.isValidContent("hello world")); // alphabets only
        assertTrue(Content.isValidContent("12345")); // numbers only
        assertTrue(Content.isValidContent("hello world the 2nd")); // alphanumeric characters
        assertTrue(Content.isValidContent("Hello World")); // with capital letters
        assertTrue(Content.isValidContent("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isSameContentHashCode() {
        Content firstContent = new Content(VALID_CONTENT);
        Content secondContent = new Content(VALID_CONTENT);
        assertTrue(firstContent.hashCode() == secondContent.hashCode());
    }
}
