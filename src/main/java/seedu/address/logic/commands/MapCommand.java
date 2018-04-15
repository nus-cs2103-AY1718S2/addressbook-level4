package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.GoogleMapsEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;

//@@author jingyinno
/**
 * Launches Google Maps with the specified location(s)
 */
public class MapCommand extends Command {
    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the location of the specified address(es). "
            + "Parameters: [ADDRESS] or [ADDRESS_START]/[ADDRESS_DESTINATION] \n"
            + "Example: " + COMMAND_WORD + " Tampines Mall/COM2 \n"
            + "Example: " + COMMAND_WORD + " 119077/117417 ";

    public static final String MESSAGE_SUCCESS = "Launching Google Maps ...";
    public static final String MESSAGE_NO_INTERNET = "Please check that you have internet connection";
    public static final String SPLIT_TOKEN = "/";
    public static final int TWO_LOCATIONS_WORD_LENGTH = 2;
    public static final int FIRST_LOCATION_INDEX = 0;

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
        locations = ParserUtil.parseLocations(this.locations);
        isOneLocation = !locations.contains(SPLIT_TOKEN);
        try {
            EventsCenter.getInstance().post(new GoogleMapsEvent(locations, isOneLocation));
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_NO_INTERNET);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && locations.equals(((MapCommand) other).locations));
    }
}
