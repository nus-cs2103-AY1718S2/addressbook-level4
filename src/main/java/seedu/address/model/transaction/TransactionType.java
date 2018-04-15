package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
//@@author ongkc
/**
 * Types of transaction
 */
public class TransactionType {

    public static final String MESSAGE_TRANSACTION_TYPE_CONSTRAINTS =
            "Transaction type can only be \"paydebt\" or \"payment\" "
                    + "and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TRANSACTION_TYPE_PAYMENT = "payment";
    public static final String TRANSACTION_TYPE_PAYDEBT = "paydebt";

    public final String value;

    /**
     * Constructs an {@code TransactionType}.
     *
     * @param type a valid transaction type.
     */
    public TransactionType(String type) {
        requireNonNull(type);
        checkArgument(isValidTransactionType(type), MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        this.value = type;
    }
    /**
     * Returns true if a given string is a valid transaction type.
     */
    public static boolean isValidTransactionType(String test) {
        if (test.toLowerCase().equals(TRANSACTION_TYPE_PAYMENT) || test.toLowerCase().equals(
                TRANSACTION_TYPE_PAYDEBT)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionType // instanceof handles nulls
                && this.value.equals(((TransactionType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
