package seedu.address.model.util;

import java.util.Collections;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Contains utility methods for populating {@code BookShelf} with sample data.
 */
public class SampleDataUtil {

    public static Book[] getSampleBooks() {
        return new Book[]{
            new Book(Collections.singleton(new Author("Andy Weir")), new Title("Artemis"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction")),
                new Description("This is Artemis.")),
            new Book(Collections.singleton(new Author("Sylvain Neuvel")), new Title("Waking Gods"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction")),
                new Description("This is Waking Gods.")),
            new Book(Collections.singleton(new Author("James S. A. Corey")), new Title("Babylon's Ashes"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                    new Category("Space Opera")),
                new Description("This is Babylon's Ashes.")),
            new Book(Collections.singleton(new Author("John Scalzi")), new Title("The Collapsing Empire"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                    new Category("Space Opera")),
                new Description("This is The Collapsing Empire.")),
            new Book(Collections.singleton(new Author("Iain M. Banks")), new Title("Consider Phlebas"),
                    CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                            new Category("Space Opera")),
                    new Description("This is Consider Phlebas."))
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
