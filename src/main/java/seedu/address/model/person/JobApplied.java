package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a candidate's job applied in HR+.
 * Guarantees: immutable; is valid as declared in {@link #isValidJobApplied(String)}
 */
public class JobApplied {
    public static final String MESSAGE_JOB_APPLIED_CONSTRAINTS =
            "Job applied can take any values, and it should not be blank";

    /**
     * The first character of the job applied must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String JOB_APPLIED_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code JobApplied}.
     *
     * @param jobApplied A valid job title.
     */
    public JobApplied(String jobApplied) {
        requireNonNull(jobApplied);
        checkArgument(isValidJobApplied(jobApplied), MESSAGE_JOB_APPLIED_CONSTRAINTS);
        this.value = jobApplied;
    }

    /**
     * Returns true if a given string is a valid job title.
     */
    public static boolean isValidJobApplied(String test) {
        return test.matches(JOB_APPLIED_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobApplied // instanceof handles nulls
                && this.value.equals(((JobApplied) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
