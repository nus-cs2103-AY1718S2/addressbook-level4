//@@author anh2111
package seedu.address.model.report;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Proportion of a report in the address book.
 */
public class Proportion {
    public final String tagName;
    public final int value;
    public final int totalPersons;

    /**
     * Constructs a {@code Proportion}.
     *
     * @param tagName a valid tag name.
     */
    public Proportion(String tagName, int value, int totalPersons) {
        requireNonNull(tagName);
        assert value >= 0 && totalPersons >= 0;

        this.tagName = tagName;
        this.value = value;
        this.totalPersons = totalPersons;
    }

    public String toString() {
        return tagName + ": " + Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Proportion // instanceof handles nulls
                && this.tagName.equals(((Proportion) other).tagName)) // state check
                && this.value == ((Proportion) other).value
                && this.totalPersons == ((Proportion) other).totalPersons;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, value, totalPersons);
    }
}
//@@author


