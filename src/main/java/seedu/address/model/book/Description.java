package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's description.
 * Guarantees: immutable.
 */
public class Description {

    public final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A book description.
     */
    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.description.equals(((Description) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
