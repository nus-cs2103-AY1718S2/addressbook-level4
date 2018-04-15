//@@author pkuhanan
package seedu.address.model.money;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Money Balance in the TravelBanker.
 * Guarantees: immutable; is valid as declared in {@link #isValidMoney(String)}
 */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS = "Money values should be numbers and a maximum "
            + "of 16 digits long";
    public static final String MONEY_VALIDATION_REGEX = "-?\\d+(\\.\\d+)?(E-?\\d+)?";

    public final double balance;
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

    public Double toDouble() {
        return balance;
    }

    /**
     * Returns true if a given string is a valid money balance.
     */
    public static boolean isValidMoney(String test) {
        return test.matches(MONEY_VALIDATION_REGEX);
    }
    //@@author Articho28
    /**
     * Limits the input for money to 16 digits. This is when we start the lose precision for balance command.
     * @param test
     * @return
     */
    public static boolean isNumberLowEnough(String test) {
        char[] testArray = test.toCharArray();
        int size = testArray.length;
        if (size > 16) {
            return false;
        }
        return true;
    }

    //@@author pkuhanan
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

    //@@author pkuhanan
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && this.value.equals(((Money) other).value)); // state check
    }
    //@@author

    public int compareTo(Money other) {
        return toDouble().compareTo(other.toDouble());
    }

}
//@@author
