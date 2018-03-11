package seedu.address.model.book;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void hashCode_sameContent_returnsSameValue() {
        assertEquals(new Description("Desc 1").hashCode(), new Description("Desc 1").hashCode());
        assertEquals(new Description("Desc x").hashCode(), new Description("Desc x").hashCode());
    }
}
