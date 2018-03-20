package seedu.address.logic.parser;

import seedu.address.logic.commands.VacantCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class VacantCommandParser {

    private List<String> buildings = Arrays.asList("COM1", "COM2", "I3", "BIZ1", "BIZ2");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VacantCommand parse(String args) throws ParseException {
        String building = args.trim();
        if (building.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));
        } else if (!buildings.contains(building)) {
            throw new ParseException(
                    String.format(VacantCommand.MESSAGE_INVALID_BUILDING));
        }

        return new VacantCommand(building);
    }
}
