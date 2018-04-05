package seedu.address.model.building.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author Caijun7
/**
 * Signals that there is no rooms available in the building.
 */
public class NoRoomsInBuildingException extends CommandException {
    public NoRoomsInBuildingException() {
        super("Building has no rooms available.");
    }
}
