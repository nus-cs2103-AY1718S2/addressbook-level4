package seedu.address.model.person;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author Robert-Peng
/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNRIC(String)}
 */
public class Nric {
    public static final String MESSAGE_NRIC_CONSTRAINTS = "Contact NRIC should be of the format #0000000@ "
        + "where # is a letter that can be S T F or G,\n"
        + "0000000 represents 7 digits which can be any number from 0-9,\n"
        + "@ can be any alphabet A-Z.\n"
        + "Both # and @ must be in upper case.";

    private static final String FIRST_CHAR_REGEX = "[STFG]";
    private static final String MIDDLE_NUM_REGEX = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
    private static final String LAST_CHAR_REGEX = "[A-Z]";
    public static final String NRIC_VALIDATION_REGEX = FIRST_CHAR_REGEX + MIDDLE_NUM_REGEX
        + LAST_CHAR_REGEX;
    public final String value;

    /**
     * Constructs a NRIC.
     *
     * @param nric A valid NRIC number
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_NRIC_CONSTRAINTS);
        this.value = nric;
    }

    /**
     * Returns if a given String is a valid NRIC
     * @param test
     * @return
     */
    public static boolean isValidNric(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Nric // instanceof handles nulls
            && this.value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
