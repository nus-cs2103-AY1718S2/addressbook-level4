//@@author sarahgoh97
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the prisoner being added is already in a cell in the prison.
 */
public class AlreadyInCellException extends IllegalValueException {
    public AlreadyInCellException() {
        super("Prisoner already in prison.");
    }
}
