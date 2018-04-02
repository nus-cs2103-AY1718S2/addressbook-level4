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

    /**
     * Parses the given {@code String} of arguments in the context of the VacantCommand
     * and returns a VacantCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VacantCommand parse(String args) throws ParseException {
        args = args.trim();
        int length;
        String[] buildingName = args.split("\\s+");
        if ("".equals(args) || "\\s+".equals(args)) {
            length = 0;
        } else {
            length = buildingName.length;
        }
        if (length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));
        }

        try {
            Building building = ParserUtil.parseBuilding(buildingName[0]);
            return new VacantCommand(building);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
