package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

//@@author jordancjq-reused
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remark can contain any values, can even be blank";

    public final String value;
    private boolean isPrivate;

    /**
     * Constructs a {@code Remark}
     *
     * @param remark Any remark
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
        this.isPrivate = false;
    }

    public Remark(String remark, boolean isPrivate) {
        this(remark);
        this.setPrivate(isPrivate);
    }

    @Override
    public String toString() {
        if (isPrivate) {
            return "<Private Remarks>";
        }
        return value;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void togglePrivacy() {
        this.isPrivate = isPrivate ? false : true;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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
