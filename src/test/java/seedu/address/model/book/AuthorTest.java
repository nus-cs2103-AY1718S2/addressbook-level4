package seedu.address.model.book;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void hashCode_sameContent_returnsSameValue() {
        assertEquals(new Author("Author 1").hashCode(), new Author("Author 1").hashCode());
        assertEquals(new Author("Author x").hashCode(), new Author("Author x").hashCode());
    }
}
