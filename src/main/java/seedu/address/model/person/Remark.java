package seedu.address.model.person;

//@@author tiny767
/**
 * Represents a Person's remark in the address book.
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remark can take any values, and it can be blank";

    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid address.
     */
    public Remark(String remark) {
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
