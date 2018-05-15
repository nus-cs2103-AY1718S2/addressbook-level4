package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a book's title.
 * Guarantees: immutable.
 */
public class Title implements Comparable<Title> {

    public final String title;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A book title.
     */
    public Title(String title) {
        requireNonNull(title);
        this.title = title;
    }


    @Override
    public int compareTo(Title other) {
        return title.compareTo(other.title);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
