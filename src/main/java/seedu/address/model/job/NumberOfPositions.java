package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Job's number of positions in contactHeRo.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class NumberOfPositions {

    public static final String MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS =
            "Job positions can only contain numbers";

    public static final String NUMBER_OF_POSITIONS_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs a {@code NumberOfPositions}.
     *
     * @param numberOfPositions A valid number of positions.
     */
    public NumberOfPositions(String numberOfPositions) {
        requireNonNull(numberOfPositions);
        checkArgument(isValidNumberOfPositions(numberOfPositions), MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS);
        this.value = numberOfPositions;
    }

    /**
     * Returns true if a given string is a valid number of positions.
     */
    public static boolean isValidNumberOfPositions(String test) {
        return test.matches(NUMBER_OF_POSITIONS_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumberOfPositions // instanceof handles nulls
                && this.value.equals(((NumberOfPositions) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
