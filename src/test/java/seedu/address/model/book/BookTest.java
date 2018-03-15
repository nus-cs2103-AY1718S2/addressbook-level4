package seedu.address.model.book;

import java.util.Collections;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BookTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Book(null, null,
                null, null, null, null, null, null));

        Assert.assertThrows(NullPointerException.class, () -> new Book(null, new Isbn(""),
                Collections.emptySet(), new Title(""), Collections.emptySet(),
                new Description(""), new Publisher(""), new PublicationDate("")));

        Assert.assertThrows(NullPointerException.class, () -> new Book(new Gid(""), null,
                Collections.emptySet(), new Title(""), Collections.emptySet(),
                new Description(""), new Publisher(""), new PublicationDate("")));

        Assert.assertThrows(NullPointerException.class, () -> new Book(new Gid(""), new Isbn(""),
                null, new Title(""), Collections.emptySet(),
                new Description(""), new Publisher(""), new PublicationDate("")));

        Assert.assertThrows(NullPointerException.class, () -> new Book(new Gid(""), new Isbn(""),
                Collections.emptySet(), null, Collections.emptySet(),
                new Description(""), new Publisher(""), new PublicationDate("")));

        Assert.assertThrows(NullPointerException.class, () -> new Book(new Gid(""), new Isbn(""),
                Collections.emptySet(), new Title(""), null,
                new Description(""), new Publisher(""), new PublicationDate("")));

        Assert.assertThrows(NullPointerException.class, () -> new Book(new Gid(""), new Isbn(""),
                Collections.emptySet(), new Title(""), Collections.emptySet(),
                null, new Publisher(""), new PublicationDate("")));

        Assert.assertThrows(NullPointerException.class, () -> new Book(new Gid(""), new Isbn(""),
                Collections.emptySet(), new Title(""), Collections.emptySet(),
                new Description(""), null, new PublicationDate("")));

        Assert.assertThrows(NullPointerException.class, () -> new Book(new Gid(""), new Isbn(""),
                Collections.emptySet(), new Title(""), Collections.emptySet(),
                new Description(""), new Publisher(""), null));
    }

}
