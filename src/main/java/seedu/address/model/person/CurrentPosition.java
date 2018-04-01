package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's current position in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCurrentPosition(String)}
 */
public class CurrentPosition {

    public static final String MESSAGE_CURRENT_POSITION_CONSTRAINTS =
            "Person's current position should only contain alphanumeric characters and spaces, and it should "
                    + "not be blank";

    /*
     * The first character of the current position must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CURRENT_POSITION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code CurrentPosition}.
     *
     * @param address A valid current position.
     */
    public CurrentPosition(String currentPosition) {
        requireNonNull(currentPosition);
        checkArgument(isValidCurrentPosition(currentPosition), MESSAGE_CURRENT_POSITION_CONSTRAINTS);
        this.value = currentPosition;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidCurrentPosition(String test) {
        return test.matches(CURRENT_POSITION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CurrentPosition // instanceof handles nulls
                && this.value.equals(((CurrentPosition) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
