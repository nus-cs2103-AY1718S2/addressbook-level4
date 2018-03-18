package seedu.address.model.money;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Money Balance in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMoney(String)}
 */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS = "Money values should be numbers";
    public static final String MONEY_VALIDATION_REGEX = "-?\\d+(\\.\\d+)?";

    public final Double balance;
    public final String value;

    /**
     * Constructs a {@code Money}.
     *
     * @param balance A valid money balance.
     */
    public Money(String balance) {
        requireNonNull(balance);
        checkArgument(isValidMoney(balance), MESSAGE_MONEY_CONSTRAINTS);
        this.balance = Double.parseDouble(balance);
        this.value = balance;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid money balance.
     */
    public static boolean isValidMoney(String test) {
        return test.matches(MONEY_VALIDATION_REGEX);
    }

    /**
     * Returns true if the user need to pay the contact certain amount of money
     * @return true/false
     */
    public boolean isNeedPaidMoney() {
        return balance < 0.0;
    }

    /**
     * Returns true if the user need to received certain amount of money from the contact
     * @return true/false
     */
    public boolean isNeedReceivedMoney() {
        return balance > 0.0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && this.value.equals(((Money) other).value)); // state check
    }


}
