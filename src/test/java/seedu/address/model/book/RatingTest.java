package seedu.address.model.book;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void hashCode_sameContent_returnsSameValue() {
        assertEquals(new Rating(-1).hashCode(), new Rating(-1).hashCode());
        assertEquals(new Rating(0).hashCode(), new Rating(0).hashCode());
    }
}
