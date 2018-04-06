package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

/**
 * Represents a Player's jersey number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJerseyNumber(String)}
 */
public class JerseyNumber {

    public static final String MESSAGE_JERSEY_NUMBER_CONSTRAINTS =
            "Player's jersey number should be an integer from 0 - 99.";


    public static final String RATING_VALIDATION_REGEX = "[0-9]|[1-8][0-9]|9[0-9]";

    public final String value;

    /**
     * Constructs an {@code JerseyNumber}.
     *
     * @param jerseyNumber A valid jersey number.
     */
    public JerseyNumber(String jerseyNumber) {
        requireNonNull(jerseyNumber);
        checkArgument(isValidJerseyNumber(jerseyNumber), MESSAGE_JERSEY_NUMBER_CONSTRAINTS);
        this.value = jerseyNumber;
    }

    /**
     * Returns true if a given string is a valid player's jersey number.
     */
    public static boolean isValidJerseyNumber(String test) {
        return test.matches(RATING_VALIDATION_REGEX) || test.equals(UNSPECIFIED_FIELD);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JerseyNumber // instanceof handles nulls
                && this.value.equals(((JerseyNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
