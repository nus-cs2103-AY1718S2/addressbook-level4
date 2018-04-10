package seedu.address.model.student.miscellaneousinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author samuel
/**
 * Represents a Student's next of kin phone number component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class NextOfKinPhone {


    public static final String MESSAGE_NEXTOFKINPHONE_CONSTRAINTS =
            "Next of kin phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String NEXTOFKINPHONE_VALIDATION_REGEX = "\\d{3,}";

    private final String value;

    /**
     * Construct a {@code NextOfKinPhone} with initial default value
     */
    public NextOfKinPhone() {
        value = "000";
    }
    /**
     * Constructs a {@code NextOFKinPhone}.
     *
     * @param nextOfKinPhone A valid next of kin phone number.
     */
    public NextOfKinPhone(String nextOfKinPhone) {
        requireNonNull(nextOfKinPhone);
        checkArgument(isValidNextOfKinPhone(nextOfKinPhone), MESSAGE_NEXTOFKINPHONE_CONSTRAINTS);
        this.value = nextOfKinPhone;
    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidNextOfKinPhone(String test) {
        return test.matches(NEXTOFKINPHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextOfKinPhone // instanceof handles nulls
                && this.value.equals(((NextOfKinPhone) other).value)); // state check
    }
}
//author
