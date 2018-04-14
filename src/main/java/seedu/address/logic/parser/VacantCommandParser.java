package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.VacantCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.building.Building;

//@@author jingyinno
/**
 * Parses input arguments and creates a new VacantCommand object
 */
public class VacantCommandParser implements Parser<VacantCommand> {
    private static final String SPLIT_TOKEN = "\\s+";
    private static final String EMPTY_STRING = "";
    private static final int NO_ARGS_LENGTH = 0;
    private static final int CORRECT_ARGS_LENGTH = 1;
    private static final int BUILDING_NAME_INDEX = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the VacantCommand
     * and returns a VacantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VacantCommand parse(String args) throws ParseException {
        String[] buildingName = validateNumberOfArgs(args);
        try {
            Building building = ParserUtil.parseBuilding(buildingName[BUILDING_NAME_INDEX]);
            return new VacantCommand(building);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns a String Array of valid number of elements after slicing the user input.
     */
    private String[] validateNumberOfArgs(String args) throws ParseException {
        int length;
        String trimmedArgs = args.trim();
        String[] buildingName = trimmedArgs.split(SPLIT_TOKEN);
        if (EMPTY_STRING.equals(trimmedArgs) || SPLIT_TOKEN.equals(trimmedArgs)) {
            length = NO_ARGS_LENGTH;
        } else {
            length = buildingName.length;
        }
        if (length != CORRECT_ARGS_LENGTH) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));
        }
        return buildingName;
    }
}
