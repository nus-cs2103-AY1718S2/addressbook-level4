package seedu.address.model.building.exceptions;

import java.util.Arrays;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.building.Building;

//@@author Caijun7
/**
 * Signals that the operation is unable to find the specified building.
 */
public class BuildingNotFoundException extends CommandException {
    public BuildingNotFoundException() {
        super("Building is not in the list of NUS Buildings given below: \n"
                + Arrays.toString(Building.NUS_BUILDINGS));
    }
}
