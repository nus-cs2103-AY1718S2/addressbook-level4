package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
//@@author chuakunhong

/**
 * Represents a remarks of the person in the address book.
 */
public class Cca {

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param cca A valid remark.
     */
    public Cca(String cca) {
        requireNonNull(cca);
        this.value = cca;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cca // instanceof handles nulls
                && this.value.equals(((Cca) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//@@author
