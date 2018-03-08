package seedu.address.model.book;

import java.util.Collections;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BookTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Book(null, null, null, null));
        Assert.assertThrows(NullPointerException.class, () -> new Book(null, new Title(""),
                Collections.emptySet(), new Description("")));
        Assert.assertThrows(NullPointerException.class, () -> new Book(Collections.emptySet(), null,
                Collections.emptySet(), new Description("")));
        Assert.assertThrows(NullPointerException.class, () -> new Book(Collections.emptySet(), new Title(""),
                null, new Description("")));
        Assert.assertThrows(NullPointerException.class, () -> new Book(Collections.emptySet(), new Title(""),
                Collections.emptySet(), null));
    }

}
