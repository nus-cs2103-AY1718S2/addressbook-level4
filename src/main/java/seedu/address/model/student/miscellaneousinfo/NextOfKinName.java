package seedu.address.model.student.miscellaneousinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@author samuel
/**
 * Represents a Student's next of kin's name component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidNextOfKinName(String)}
 */
public class NextOfKinName {

    public static final String MESSAGE_NEXTOFKINNAME_CONSTRAINTS =
            "Student next of kin's name can take any values, and it should not be blank";

    /*
     * The first character of the next of kin's name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NEXTOFKINNAME_VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Construct a {@code NextOfKinName} with initial default value
     */
    public NextOfKinName() {
        value = "Not updated";
    }

    /**
     * Constructs a {@code NextOfKinName}.
     *
     * @param nextOfKinName A valid next of kin name.
     */
    public NextOfKinName(String nextOfKinName) {
        requireNonNull(nextOfKinName);
        checkArgument(isValidNextOfKinName(nextOfKinName), MESSAGE_NEXTOFKINNAME_CONSTRAINTS);
        this.value = nextOfKinName;
    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidNextOfKinName(String test) {
        return test.matches(NEXTOFKINNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextOfKinName // instanceof handles nulls
                && this.value.equals(((NextOfKinName) other).value)); // state check
    }
}
//@author
