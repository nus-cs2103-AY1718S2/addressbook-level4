//@@author sarahgoh97
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the operation will be adding a prisoner to a full cell.
 */
public class FullCellException extends IllegalValueException {
    public FullCellException() {
        super("Invalid cell. Cell is already full.");
    }
}
