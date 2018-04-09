package seedu.address.model.group;

//@@author jas5469
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Group's information in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInformation(String)}
 */
public class Information {

    public static final String MESSAGE_INFORMATION_CONSTRAINTS =
            "Group information should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the information must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INFORMATION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Information}.
     *
     * @param information A valid information.
     */
    public Information(String information) {
        requireNonNull(information);
        checkArgument(isValidInformation(information), MESSAGE_INFORMATION_CONSTRAINTS);
        this.value = information;
    }

    /**
     * Returns true if a given string is a valid to-do information.
     */
    public static boolean isValidInformation(String test) {
        return test.matches(INFORMATION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Information // instanceof handles nulls
                && this.value.equals(((Information) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
