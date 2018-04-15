package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

//@@author steven-jia
/**
 * Represents a Person's balance or debt in SmartSplit.
 * Guarantees: immutable; is valid as declared in {@link #isValidBalance(String)}
 */
public class Balance {

    public static final String MESSAGE_BALANCE_CONSTRAINTS =
            "Balance should only contain numeric characters up to 2 digits, and it should not be blank,";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BALANCE_VALIDATION_REGEX = "^-?\\d*\\.\\d{2}$";

    public final String value;

    /**
     * Constructs a {@code Balance}.
     *
     * @param balance A valid balance.
     */
    public Balance(String balance) {
        requireNonNull(balance);
        checkArgument(isValidBalance(balance), MESSAGE_BALANCE_CONSTRAINTS);
        this.value = balance;
    }

    /**
     * Returns true if a given string is a valid person balance.
     */
    public static boolean isValidBalance(String test) {
        return test.matches(BALANCE_VALIDATION_REGEX);
    }


    public double getDoubleValue() {
        return Double.valueOf(value);
    }

    public Balance getInverse() {
        if (this.value.contains("-")) {
            return new Balance(this.value.substring(1));
        }
        return new Balance("-" + this.value);
    }

    /**
     * Adds the value of two balance
     * @param balance to add.
     * @return Balance with the value of the sum.
     */
    public Balance add(Balance balance) {
        double addition = this.getDoubleValue() + balance.getDoubleValue();
        DecimalFormat formatter = new DecimalFormat("#.00");
        return new Balance(String.valueOf(formatter.format(addition)));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Balance // instanceof handles nulls
                && this.value.equals(((Balance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
