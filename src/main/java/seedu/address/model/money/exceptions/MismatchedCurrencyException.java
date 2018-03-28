package seedu.address.model.money.exceptions;

/**
 * Signals that the money objects do not have matching currencies.
 */

public class MismatchedCurrencyException extends RuntimeException {
    public MismatchedCurrencyException(String aMessage){
        super(aMessage);
    }
}