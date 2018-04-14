package seedu.progresschecker.model.credentials;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author adityaa1998
/**
 * Represents a github passcode
 */
public class Passcode {

    public static final String MESSAGE_PASSCODE_CONSTRAINTS =
            "Passcode must contain atleast one lower case character, one numeral "
                    + "and should be atleast 7 characters long";

    /*
     * Password must contain one lowercase character,
     * one number and minimum 7 characters
     */
    public static final String PASSCODE_VALIDATION_REGEX = "((?=.*\\d)(?=.*[a-z]).{7,100})";

    public final String passcode;

    /**
     * Constructs a {@code Passcode}.
     *
     * @param passcode A valid assignees.
     */
    public Passcode(String passcode) {
        requireNonNull(passcode);
        checkArgument(isValidPasscode(passcode), MESSAGE_PASSCODE_CONSTRAINTS);
        this.passcode = passcode;
    }

    /**
     * Returns true if a given string is a valid github passcode.
     */
    public static boolean isValidPasscode(String test) {
        return test.matches(PASSCODE_VALIDATION_REGEX);
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

