package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.building.Building.BUILDINGS;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.VacantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.building.Building;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parses input arguments and creates a new VacantCommand object
 */
public class VacantCommandParser {

    private String[] buildingArray = BUILDINGS;
    private List<String> buildings = new ArrayList<String>(Arrays.asList(buildingArray));

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VacantCommand parse(String args) throws ParseException {
        String buildingName = args.trim();
        if (buildingName.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));
        }

        try {
            Building building = ParserUtil.parseBuilding(buildingName);
            return new VacantCommand(building);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
