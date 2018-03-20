package seedu.address.model.building.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Signals that the operation is unable to find the specified building.
 */
public class BuildingNotFoundException extends CommandException {
    public BuildingNotFoundException() {
        super("Building does not exist.");
    }
}
