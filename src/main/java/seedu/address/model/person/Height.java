package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author wenhao53
/**
 * Represents a Person's height (in cm) in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {


    public static final String MESSAGE_HEIGHT_CONSTRAINTS =
            "Height(in cm) can only contain numbers and decimals, and should be at between 10.0 to 400.0";
    public static final String HEIGHT_VALIDATION_REGEX = "\\d{2,}(\\.\\d+)?";
    public final String value;

    /**
     * Constructs a {@code Height}.
     *
     * @param height A valid height (in cm).
     */
    public Height(String height) {
        requireNonNull(height);
        checkArgument(isValidHeight(height), MESSAGE_HEIGHT_CONSTRAINTS);
        this.value = height;
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(HEIGHT_VALIDATION_REGEX) && heightWithinRange(test);
    }

    /**
     * Returns true if a given Height string is within the allowable range of input
     */
    private static boolean heightWithinRange(String test) {
        return Double.parseDouble(test) >= 10.0 && Double.parseDouble(test) <= 400.0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
                && this.value.equals(((Height) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
