package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
//@@author chuakunhong

/**
 * Represents a remarks of the person in the address book.
 */
public class InjuriesHistory {

    public final String value;

    /**
     * Constructs a {@code InjuriesHistory}.
     *
     * @param injurieshistory A valid injurieshistory.
     */
    public InjuriesHistory(String injurieshistory) {
        requireNonNull(injurieshistory);
        this.value = injurieshistory;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InjuriesHistory // instanceof handles nulls
                && this.value.equals(((InjuriesHistory) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//@@author
