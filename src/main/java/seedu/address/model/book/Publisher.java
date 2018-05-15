package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's publisher.
 * Guarantees: immutable.
 */
public class Publisher {

    public final String publisher;

    /**
     * Constructs a {@code Publisher}.
     *
     * @param publisher A book's publisher.
     */
    public Publisher(String publisher) {
        requireNonNull(publisher);
        this.publisher = publisher;
    }


    @Override
    public String toString() {
        return publisher;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Publisher // instanceof handles nulls
                && this.publisher.equals(((Publisher) other).publisher)); // state check
    }

    @Override
    public int hashCode() {
        return publisher.hashCode();
    }
}
