package seedu.address.model.book;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void hashCode_sameContent_returnsSameValue() {
        assertEquals(new Title("Title 1").hashCode(), new Title("Title 1").hashCode());
        assertEquals(new Title("Title x").hashCode(), new Title("Title x").hashCode());
    }
}
