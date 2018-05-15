package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.Priority;
import seedu.address.model.book.Status;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ARTEMIS = new BookBuilder().withAuthors("Andy Weir").withTitle("Artemis")
            .withCategories("Fiction").withDescription("This is Artemis.")
            .withStatus(Status.READ).withPriority(Priority.LOW).withRating(5).withGid("ry3GjwEACAAJ")
            .withIsbn("9780525572664").withPublisher("").withPublicationDate("2017-11-14").build();
    public static final Book BABYLON_ASHES = new BookBuilder().withAuthors("James S. A. Corey")
            .withTitle("Babylon's Ashes").withCategories("Fiction").withDescription("This is Babylon's Ashes.")
            .withStatus(Status.READING).withPriority(Priority.HIGH).withRating(4).withGid("3jsYCwAAQBAJ")
            .withIsbn("9780316217637").withPublisher("Orbit").withPublicationDate("2016-12-06").build();
    public static final Book COLLAPSING_EMPIRE = new BookBuilder().withAuthors("John Scalzi")
            .withTitle("The Collapsing Empire").withCategories("Fiction").withDescription("This is Collapsing Empire.")
            .withStatus(Status.READING).withPriority(Priority.LOW).withRating(-1).withGid("2SoaDAAAQBAJ")
            .withIsbn("9780765388896").withPublisher("Tor Books").withPublicationDate("2017-03-21").build();
    public static final Book CONSIDER_PHLEBAS = new BookBuilder().withAuthors("Iain M. Banks")
            .withTitle("Consider Phlebas").withCategories("Fiction", "Science Fiction")
            .withDescription("This is Consider Phlebas.")
            .withStatus(Status.UNREAD).withPriority(Priority.NONE).withRating(-1).withGid("3_bJKlAOecEC")
            .withIsbn("9780316095839").withPublicationDate("2009-12-01").withPublisher("Orbit").build();
    public static final Book WAKING_GODS = new BookBuilder().withAuthors("Sylvain Neuvel").withTitle("Waking Gods")
            .withCategories("Fiction", "Science Fiction").withDescription("This is Waking Gods.")
            .withStatus(Status.UNREAD).withPriority(Priority.NONE).withRating(-1).withGid("CIj1DAAAQBAJ")
            .withIsbn("9781405921909").withPublisher("Penguin UK").withPublicationDate("2017-04-06").build();

    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns an {@code BookShelf} with all the typical books.
     */
    public static BookShelf getTypicalBookShelf() {
        BookShelf bookShelf = new BookShelf();
        try {
            bookShelf.setBooks(getTypicalBooks());
        } catch (DuplicateBookException e) {
            throw new AssertionError("Unexpected DuplicateBookException.");
        }
        return bookShelf;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(ARTEMIS, BABYLON_ASHES,
                COLLAPSING_EMPIRE, CONSIDER_PHLEBAS, WAKING_GODS));
    }
}
