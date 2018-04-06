package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
//@@author chuakunhong

/**
 * Represents a remarks of the person in the address book.
 */
public class Cca {


    public static final String MESSAGE_REMARK_CONSTRAINTS = "Remark can contain anything that you want.";
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Cca(String remark) {
        requireNonNull(remark);
        this.value = remark;
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
