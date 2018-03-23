package seedu.address.model.smplatform;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Link {
    public static final String MESSAGE_REMARK_CONSTRAINTS = "Person remarks can take any values, can even be blank";

    public final String value;

    public Link(String link) {
        requireNonNull(link);
        this.value = link;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && this.value.equals(((Link) other).value)); // state check
        }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
