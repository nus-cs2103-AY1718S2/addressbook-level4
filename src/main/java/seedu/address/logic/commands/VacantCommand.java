package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.VenueTableEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.building.Building;
import seedu.address.model.building.exceptions.BuildingNotFoundException;
import seedu.address.model.building.exceptions.CorruptedVenueInformationException;

/**
 * Retrieves all vacant rooms in a given building
 */
public class VacantCommand extends Command {
    public static final String COMMAND_WORD = "vacant";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds vacant study rooms in a building \n"
            + "Parameters: [BUILDING_NAME]\n"
            + "Example: " + COMMAND_WORD + " COM1";

    public static final String MESSAGE_SUCCESS = "List of rooms in building successfully retrieved.";
    public static final String MESSAGE_INVALID_BUILDING =
            "Building is not in the list of NUS Buildings given below: \n"
            + Arrays.toString(Building.NUS_BUILDINGS);
    public static final String MESSAGE_CORRUPTED_VENUE_INFORMATION_FILE =
            "Unable to read from venueinformation.json, file is corrupted. Please re-download the file.";

    private final Building building;

    /**
     * Creates a VacantCommand to retrieve all vacant rooms in a given building
     */
    public VacantCommand(Building building) {
        requireNonNull(building);
        this.building = building;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            ArrayList<ArrayList<String>> allRoomsSchedule = model.retrieveAllRoomsSchedule(building);
            ObservableList<ArrayList<String>> schedule = FXCollections.observableArrayList(allRoomsSchedule);
            EventsCenter.getInstance().post(new VenueTableEvent(schedule));
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (BuildingNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_BUILDING);
        } catch (CorruptedVenueInformationException e) {
            throw new CommandException(MESSAGE_CORRUPTED_VENUE_INFORMATION_FILE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VacantCommand // instanceof handles nulls
                && building.equals(((VacantCommand) other).building));
    }
}
