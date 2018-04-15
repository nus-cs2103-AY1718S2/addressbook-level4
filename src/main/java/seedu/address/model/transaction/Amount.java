package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ongkc
/**
 * Represents the amount that a Payer paid in a SmartSplit transaction.
 */
public class Amount {

    public static final String MESSAGE_AMOUNT_CONSTRAINTS =
            "Amount can only take in a positive numerical number up to 2 decimal places, "
                    + "and it should not be blank";

    // The first character of the amount must not be a whitespace,
    // otherwise " " (a blank string) becomes a valid input.
    public static final String AMOUNT_VALIDATION_REGEX = "^\\d+(\\.\\d{1,2})?$";

    public final String value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_AMOUNT_CONSTRAINTS);
        this.value = amount;
    }
    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(AMOUNT_VALIDATION_REGEX);
    }

    public double getDoubleValue() {
        return Double.valueOf(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && this.value.equals(((Amount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

