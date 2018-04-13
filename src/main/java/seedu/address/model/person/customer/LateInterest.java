package seedu.address.model.person.customer;

//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author melvintzw

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a customer's late interest rate.
 * Guarantees: immutable;
 */
public class LateInterest {

    /*
    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    */
    public static final String MESSAGE_LATE_INTEREST_DOUBLE_ONLY =
            "MONEY_BORROWED can only contain numbers";
    public static final String MESSAGE_LATE_INTEREST_NO_NEGATIVE =
            "MONEY_BORROWED cannot be negative";

    public final double value;

    public LateInterest() {
        value = 0;
    }

    /**
     * Constructs a {@code Phone}.
     *
     * @param value an amount borrowed form the loanshark
     */
    public LateInterest(double value) {
        checkArgument(isValidInterest(value), MESSAGE_LATE_INTEREST_NO_NEGATIVE);
        this.value = value;
    }

    /**
     * Returns true if a give value is zero or positive, returns false otherwise
     */
    public static boolean isValidInterest(double test) {
        return (!(test < 0));
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LateInterest // instanceof handles nulls
                && this.value == ((LateInterest) other).value); // state check
    }

    @Override
    public int hashCode() {
        return new Double(value).hashCode();
    }

}
