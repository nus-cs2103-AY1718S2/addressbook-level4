package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's ISBN.
 * Guarantees: immutable.
 */
public class Isbn {

    public final String isbn;

    /**
     * Constructs a {@code Description}.
     *
     * @param isbn A book's isbn.
     */
    public Isbn(String isbn) {
        requireNonNull(isbn);
        this.isbn = isbn;
    }


    @Override
    public String toString() {
        return isbn;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Isbn // instanceof handles nulls
                && this.isbn.equals(((Isbn) other).isbn)); // state check
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
