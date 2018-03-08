package seedu.address.model.book;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Category(null));
    }

}
