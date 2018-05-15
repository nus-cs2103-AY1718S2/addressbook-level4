package seedu.address.model.book;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void hashCode_sameContent_returnsSameValue() {
        assertEquals(new Category("Category 1").hashCode(), new Category("Category 1").hashCode());
        assertEquals(new Category("Category x").hashCode(), new Category("Category x").hashCode());
    }
}
