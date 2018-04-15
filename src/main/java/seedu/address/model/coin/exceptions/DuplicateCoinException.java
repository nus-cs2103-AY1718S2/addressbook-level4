package seedu.address.model.coin.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Coin objects.
 */
public class DuplicateCoinException extends DuplicateDataException {
    public DuplicateCoinException() {
        super("Operation would result in duplicate coins");
    }
}
