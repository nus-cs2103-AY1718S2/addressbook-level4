//@@author sarahgoh97
package seedu.address.model.cell.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the operation will be removing a person not in jail from the jail.
 */
public class NotImprisonedException extends IllegalValueException {
    public NotImprisonedException() {
        super("This person is not imprisoned in this prison.");
    }
}
