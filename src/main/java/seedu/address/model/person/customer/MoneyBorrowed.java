package seedu.address.model.person.customer;

//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author melvintzw

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a customer's amount of money that he/she borrowed.
 * Guarantees: immutable;
 */
public class MoneyBorrowed {

    public static final String MESSAGE_MONEY_BORROWED_DOUBLE_ONLY =
            "MONEY_BORROWED can only contain numbers";
    public static final String MESSAGE_MONEY_BORROWED_NO_NEGATIVE =
            "MONEY_BORROWED cannot be negative";

    public final double value;

    public MoneyBorrowed() {
        value = 0;
    }

    /**
     * Constructs a {@code Phone}.
     *
     * @param value an amount borrowed form the loanshark
     */
    public MoneyBorrowed(double value) {
        checkArgument(isValidMoneyBorrowed(value), MESSAGE_MONEY_BORROWED_NO_NEGATIVE);
        this.value = value;
    }

    /**
     * Returns true if a given value is zero or positive, returns false otherwise
     */
    public static boolean isValidMoneyBorrowed(double test) {
        return (!(test < 0));
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoneyBorrowed // instanceof handles nulls
                && this.value == ((MoneyBorrowed) other).value); // state check
    }

    @Override
    public int hashCode() {
        return new Double(value).hashCode();
    }

}
