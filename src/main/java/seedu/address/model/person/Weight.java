package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author wayneong95

/**
 * Represents a Person's weight (in Kg) in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {


    public static final String MESSAGE_WEIGHT_CONSTRAINTS =
            "Weight(in Kg) can only contain numbers and decimals, and should be at least 2 digits long";
    public static final String WEIGHT_VALIDATION_REGEX = "\\d{2,}(\\.\\d+)?";
    public final String value;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight (in Kg).
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_WEIGHT_CONSTRAINTS);
        this.value = weight;
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(WEIGHT_VALIDATION_REGEX) && weightWithinRange(test);
    }

    //@@author wenhao53
    /**
     * Returns true if a given Weight string is within the allowable range of input
     */
    private static boolean weightWithinRange(String test) {
        return Double.parseDouble(test) >= 10.0 && Double.parseDouble(test) <= 400.0;
    }

    //@@author

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight // instanceof handles nulls
                && this.value.equals(((Weight) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
