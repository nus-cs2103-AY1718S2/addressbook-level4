//@@author QiuHaohao
package seedu.address.model.account.exceptions;

/**
 * Signals that the operation is unable to find the specified account.
 */
public class AccountNotFoundException extends Exception {

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public AccountNotFoundException (String message) {
        super (message);
    }

    public  AccountNotFoundException() {}
}
