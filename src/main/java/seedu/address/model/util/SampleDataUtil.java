package seedu.address.model.util;

import java.util.Collections;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Gid;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.PublicationDate;
import seedu.address.model.book.Publisher;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Contains utility methods for populating {@code BookShelf} with sample data.
 */
public class SampleDataUtil {

    public static Book[] getSampleBooks() {
        return new Book[]{
            new Book(new Gid("ry3GjwEACAAJ"), new Isbn("9780525572664"),
                Collections.singleton(new Author("Andy Weir")), new Title("Artemis"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction")),
                new Description("This is Artemis."), new Publisher(""), new PublicationDate("2017-11-14")),
            new Book(new Gid("CIj1DAAAQBAJ"), new Isbn("9781405921909"),
                Collections.singleton(new Author("Sylvain Neuvel")), new Title("Waking Gods"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction")),
                new Description("This is Waking Gods."), new Publisher("Penguin UK"),
                new PublicationDate("2017-04-06")),
            new Book(new Gid("3jsYCwAAQBAJ"), new Isbn("9780316217637"),
                Collections.singleton(new Author("James S. A. Corey")), new Title("Babylon's Ashes"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                    new Category("Space Opera")),
                new Description("This is Babylon's Ashes."), new Publisher("Orbit"),
                new PublicationDate("2016-12-06")),
            new Book(new Gid("2SoaDAAAQBAJ"), new Isbn("9780765388896"),
                Collections.singleton(new Author("John Scalzi")), new Title("The Collapsing Empire"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                    new Category("Space Opera")),
                new Description("This is The Collapsing Empire."), new Publisher("Tor Books"),
                new PublicationDate("2017-03-21")),
            new Book(new Gid("3_bJKlAOecEC"), new Isbn("9780316095839"),
                Collections.singleton(new Author("Iain M. Banks")), new Title("Consider Phlebas"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                    new Category("Space Opera")),
                new Description("This is Consider Phlebas."), new Publisher("Orbit"), new PublicationDate("2009-12-01"))
        };
    }

    public static ReadOnlyBookShelf getSampleBookShelf() {
        try {
            BookShelf sampleBookShelf = new BookShelf();
            for (Book sampleBook : getSampleBooks()) {
                sampleBookShelf.addBook(sampleBook);
            }
            return sampleBookShelf;
        } catch (DuplicateBookException e) {
            throw new AssertionError("sample data cannot contain duplicate books", e);
        }
    }

}
