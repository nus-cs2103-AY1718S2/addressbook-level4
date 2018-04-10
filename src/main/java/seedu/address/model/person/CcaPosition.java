package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
//@@author chuakunhong

/**
 * Represents a remarks of the person in the address book.
 */
public class CcaPosition {

    public final String value;

    /**
     * Constructs a {@code ccaPosition}.
     *
     * @param ccaPosition A valid ccaPosition.
     */
    public CcaPosition(String ccaPosition) {
        requireNonNull(ccaPosition);
        this.value = ccaPosition;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaPosition // instanceof handles nulls
                && this.value.equals(((CcaPosition) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//@@author
