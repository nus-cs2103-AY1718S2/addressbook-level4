package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.UniqueList;

/**
 * Contains data about a single book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    private final UniqueList<Author> authors;
    private final Title title;
    private final UniqueList<Category> categories;
    private final Description description;

    // TODO add more fields: gid, isbn13, publisher, publishedDate

    /**
     * Every field must be present and not null.
     */
    public Book(Set<Author> authors, Title title, Set<Category> categories, Description description) {
        requireAllNonNull(authors, title, categories, description);
        this.authors = new UniqueList<>(authors);
        this.title = title;
        this.categories = new UniqueList<>(categories);
        this.description = description;
    }

    /**
     * Returns an immutable authors set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors.toSet());
    }

    public Title getTitle() {
        return title;
    }

    /**
     * Returns an immutable categories set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories.toSet());
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getTitle().equals(this.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Author: ");
        getAuthors().forEach(builder::append);
        builder.append(" Categories: ");
        getCategories().forEach(builder::append);
        return builder.toString();
    }

}
