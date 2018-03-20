package seedu.address.logic.commands;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.building.Building;
import seedu.address.model.building.exceptions.BuildingNotFoundException;

import static java.util.Objects.requireNonNull;

public class VacantCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "vacant";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds vacant study rooms in a building \n"
            + "Parameters: [BUILDING_NAME]\n"
            + "Example: " + COMMAND_WORD + " COM1";

    public static final String MESSAGE_SUCCESS = "List of rooms in building successfully retrieved.";
    public static final String MESSAGE_INVALID_BUILDING = "Building does not exist.";

    private final Building building;

    /**
     * Creates an ImportCommand to import the specified {@code AddressBook} from filepath to
     * current {@code AddressBook}
     */
    public VacantCommand(Building building) {
        requireNonNull(building);
        this.building = building;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.getRoomsFrom(building);
        } catch (BuildingNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_BUILDING);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VacantCommand // instanceof handles nulls
                && building.equals(((VacantCommand) other).building));
    }
}
