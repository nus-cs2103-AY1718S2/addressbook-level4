package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.building.Building.retrieveNusBuildingIfExist;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.GoogleMapsEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Launches Google Maps with specified location(s)
 */
public class MapCommand extends Command {
    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the location of the specified address(es) \n"
            + "Parameters: [ADDRESS] or \n"
            + "Parameters: [ADDRESS_START]/[ADDRESS_DESTINATION] \n"
            + "Example: " + COMMAND_WORD + " Tampines Mall/COM2 \n"
            + "Example: " + COMMAND_WORD + " 119077/117417 ";

    public static final String MESSAGE_SUCCESS = "Launching Google Maps ...";

    private String locations;
    private boolean isOneLocation;

    /**
     * Creates a MapCommand to pass locations to Google Maps
     */
    public MapCommand(String locations) {
        requireNonNull(locations);
        this.locations = locations;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        String[] locationsArray = locations.split("/");
        for (int i = 0; i < locationsArray.length; i++) {
            locationsArray[i] = locationsArray[i].trim();
            locationsArray[i] = retrieveNusBuildingIfExist(locationsArray[i]);
        }

        if (locationsArray.length >= 2) {
            locations = String.join("/", locationsArray);
            isOneLocation = false;
        } else {
            locations = Arrays.toString(locationsArray);
            isOneLocation = true;
        }

        EventsCenter.getInstance().post(new GoogleMapsEvent(locations, isOneLocation));
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && locations.equals(((MapCommand) other).locations));
    }
}
