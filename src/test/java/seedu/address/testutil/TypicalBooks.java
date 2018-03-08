package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BookShelf;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ARTEMIS = new BookBuilder().withAuthors("Andy Weir").withTitle("Artemis")
            .withCategories("Fiction").withDescription("This is Artemis.").build();
    public static final Book BABYLON_ASHES = new BookBuilder().withAuthors("James S. A. Corey")
            .withTitle("Babylon's Ashes").withCategories("Fiction").withDescription("This is Babylon's Ashes.").build();
    public static final Book COLLAPSING_EMPIRE = new BookBuilder().withAuthors("John Scalzi")
            .withTitle("The Collapsing Empire").withCategories("Fiction")
            .withDescription("This is The Collapsing Empire.").build();
    public static final Book WAKING_GODS = new BookBuilder().withAuthors("Sylvain Neuvel").withTitle("Waking Gods")
            .withCategories("Fiction", "Science Fiction").withDescription("This is Waking Gods.").build();

    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns an {@code BookShelf} with all the typical books.
     */
    public static BookShelf getTypicalBookShelf() {
        BookShelf bookShelf = new BookShelf();
        for (Book book : getTypicalBooks()) {
            try {
                bookShelf.addBook(book);
            } catch (DuplicateBookException e) {
                throw new AssertionError("not possible");
            }
        }
        return bookShelf;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(ARTEMIS, BABYLON_ASHES, COLLAPSING_EMPIRE, WAKING_GODS));
    }
}
