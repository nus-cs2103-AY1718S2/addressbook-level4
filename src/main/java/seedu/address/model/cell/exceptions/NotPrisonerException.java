//@@author sarahgoh97
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the person added to a cell is not a prisoner
 */
public class NotPrisonerException extends IllegalValueException {
    public NotPrisonerException() {
        super("Invalid person to add, not a prisoner.");
    }
}
