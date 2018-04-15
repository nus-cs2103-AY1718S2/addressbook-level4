package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author hypertun
/**
 * Represents a Person's activity in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidActivityLevel(String)}
 */
public class ActivityLevel {

    public static final String MESSAGE_ACTIVITYLEVEL_CONSTRAINTS =
            "Activity Level can only be Basal Metabolic Rate(1.0), Sedentary(1.2) - little to no exercise per week,"
                    + " Lightly Active(1.375) - exercise 1-3 times per week,"
                    + " Moderately Active(1.55) - exercise 3-5 times per week,"
                    + " Very Active(1.725) -  exercise 6-7 times per week,"
                    + " Extra Active(1.9) - very hard exercise or physical job";
    public final String value;

    /**
     * Constructs a {@code ActivityLevel}.
     *
     * @param activityLevel A valid ActivityLevel.
     */
    public ActivityLevel(String activityLevel) {
        requireNonNull(activityLevel);
        checkArgument(isValidActivityLevel(activityLevel), MESSAGE_ACTIVITYLEVEL_CONSTRAINTS);
        this.value = activityLevel;
    }

    /**
     * Returns true if a given string is a valid activityLevel.
     */
    public static boolean isValidActivityLevel(String test) {
        if (test.equals("1.0") || test.equals("1.2") || test.equals("1.375") || test.equals("1.55")
                || test.equals("1.725") || test.equals("1.9")) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityLevel // instanceof handles nulls
                && this.value.equals(((ActivityLevel) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

