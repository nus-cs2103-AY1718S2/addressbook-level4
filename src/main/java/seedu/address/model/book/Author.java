package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a single book author.
 * Guarantees: immutable.
 */
public class Author {

    public final String fullName;

    /**
     * Constructs a {@code Author}.
     *
     * @param fullName A valid author name.
     */
    public Author(String fullName) {
        requireNonNull(fullName);
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Author // instanceof handles nulls
                && this.fullName.equals(((Author) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
