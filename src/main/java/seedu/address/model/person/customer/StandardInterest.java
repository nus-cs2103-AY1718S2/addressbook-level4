package seedu.address.model.person.customer;

//@@author melvintzw

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a customer's standard interest rate.
 * Guarantees: immutable;
 */
public class StandardInterest {


    public static final String MESSAGE_STANDARD_INTEREST_DOUBLE_ONLY =
            "MONEY_BORROWED can only contain numbers";
    public static final String MESSAGE_STANDARD_INTEREST_NO_NEGATIVE =
            "MONEY_BORROWED cannot be negative";


    public final double value;

    public StandardInterest() {
        value = 0;
    }

    /**
     * Constructs a {@code Phone}.
     *
     * @param value an amount borrowed form the loanshark
     */
    public StandardInterest(double value) {
        checkArgument(isValidInterest(value), MESSAGE_STANDARD_INTEREST_NO_NEGATIVE);
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
                || (other instanceof StandardInterest // instanceof handles nulls
                && this.value == ((StandardInterest) other).value); // state check
    }

    @Override
    public int hashCode() {
        return new Double(value).hashCode();
    }

}
