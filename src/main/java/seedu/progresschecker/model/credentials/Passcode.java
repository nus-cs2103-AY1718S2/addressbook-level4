package seedu.progresschecker.model.credentials;

import static java.util.Objects.requireNonNull;

/**
 * Represents a github passcode
 */
public class Passcode {

    public final String passcode;

    /**
     * Constructs a {@code Passcode}.
     *
     * @param passcode A valid assignees.
     */
    public Passcode(String passcode) {
        requireNonNull(passcode);
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return passcode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.credentials.Passcode // instanceof handles nulls
                && this.passcode.equals(((Passcode) other).passcode)); // state check
    }

    @Override
    public int hashCode() {
        return passcode.hashCode();
    }

}

