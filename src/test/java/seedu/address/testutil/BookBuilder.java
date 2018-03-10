package seedu.address.testutil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Title;
import seedu.address.model.util.BookDataUtil;

/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {

    public static final String DEFAULT_AUTHOR = "Andy Weir";
    public static final String DEFAULT_TITLE = "Artemis";
    public static final String DEFAULT_CATEGORY = "Science Fiction";
    public static final String DEFAULT_DESCRIPTION = "This is a description for Artemis.";

    private Set<Author> authors;
    private Title title;
    private Set<Category> categories;
    private Description description;

    public BookBuilder() {
        authors = Collections.singleton(new Author(DEFAULT_AUTHOR));
        title = new Title(DEFAULT_TITLE);
        categories = Collections.singleton(new Category(DEFAULT_CATEGORY));
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        authors = new HashSet<>(bookToCopy.getAuthors());
        title = bookToCopy.getTitle();
        categories = new HashSet<>(bookToCopy.getCategories());
        description = bookToCopy.getDescription();
    }

    /**
     * Parses the {@code authors} into a {@code Set<Author>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withAuthors(String... authors) {
        this.authors = BookDataUtil.getAuthorSet(authors);
        return this;
    }

    /**
     * Sets the {@code Title} of the {@code Book} that we are building.
     */
    public BookBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withCategories(String... categories) {
        this.categories = BookDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Book} that we are building.
     */
    public BookBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Book build() {
        return new Book(authors, title, categories, description);
    }

}
