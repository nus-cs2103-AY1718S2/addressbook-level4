//@@author sarahgoh97
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the cell indicated is not within boundaries
 */
public class NonExistentCellException extends IllegalValueException {
    public NonExistentCellException() {
        super("No such cell exists.");
    }
}
