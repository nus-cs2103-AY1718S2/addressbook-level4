package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
//@@author chuakunhong
/**
 * Represents a remarks of the person in the address book.
 */
public class Remark {


    public static final String MESSAGE_REMARK_CONSTRAINTS = "Remark can contain anything that you want.";
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
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
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//@@author
