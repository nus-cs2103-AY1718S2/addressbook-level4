package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

//@@author kaisertanqr

/**
 * Represents a Person's SessionLog in the address book.
 * Guarantees: field values are validated, immutable.
 */
public class SessionLogs {

    public final StringBuilder value;

    /**
     * Constructs an {@code SessionLogs}.
     *
     * @param log A valid SessionLogs.
     */
    public SessionLogs(String log) {
        requireNonNull(log);
        this.value = new StringBuilder();
        this.value.append(log);
    }


    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SessionLogs // instanceof handles nulls
                && this.value.equals(((SessionLogs) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
