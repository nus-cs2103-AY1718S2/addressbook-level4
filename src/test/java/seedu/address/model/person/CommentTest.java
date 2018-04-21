package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Ang-YC
public class CommentTest {

    @Test
    public void constructor_null_constructionSuccessValueNull() {
        assertNull(new Comment(null).value);
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidComment = "With\nline";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Comment(invalidComment));
    }

    @Test
    public void isValidComment() {
        Assert.assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        // All values are accepted
        assertTrue(Comment.isValidComment("")); // Empty string
        assertTrue(Comment.isValidComment(" ")); // Whitespace only
        assertTrue(Comment.isValidComment("He is cool!")); // Normal string

        // Except new line
        assertFalse(Comment.isValidComment("\n")); // Disallow new line
    }
}
