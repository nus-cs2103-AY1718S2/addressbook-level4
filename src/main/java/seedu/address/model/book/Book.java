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
    private final Gid gid;
    private final Isbn isbn;
    private final PublicationDate publicationDate;
    private final Publisher publisher;

    /**
     * Every field must be present and not null.
     */
    public Book(Gid gid, Isbn isbn, Set<Author> authors, Title title, Set<Category> categories,
                Description description, Publisher publisher, PublicationDate publicationDate) {
        requireAllNonNull(gid, isbn, authors, title, categories, description, publisher, publicationDate);
        this.gid = gid;
        this.isbn = isbn;
        this.authors = new UniqueList<>(authors);
        this.title = title;
        this.categories = new UniqueList<>(categories);
        this.description = description;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
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

    public Gid getGid() {
        return gid;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    public Publisher getPublisher() {
        return publisher;
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
        return otherBook.getIsbn().equals(this.getIsbn())
                && otherBook.getTitle().equals(this.getTitle())
                && otherBook.getAuthors().equals(this.getAuthors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" - Authors: ");
        getAuthors().forEach(author -> builder.append("[").append(author).append("]"));
        return builder.toString();
    }

}
