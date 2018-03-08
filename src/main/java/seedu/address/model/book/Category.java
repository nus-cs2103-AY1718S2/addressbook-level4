package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a single book category.
 * Guarantees: immutable.
 */
public class Category {

    public final String category;

    /**
     * Constructs a {@code Category}.
     *
     * @param category A book category.
     */
    public Category(String category) {
        requireNonNull(category);
        this.category = category;
    }


    @Override
    public String toString() {
        return category;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.category.equals(((Category) other).category)); // state check
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

}
