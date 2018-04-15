package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DistanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author ncaminh
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class DistanceCommandParser implements Parser<DistanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DistanceCommand
     * and returns an DistanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DistanceCommand parse(String args) throws ParseException {
        String[] selectedIndexes = args.split(" ");
        try {
            if (selectedIndexes.length == 3) {
                Index firstIndex = ParserUtil.parseIndex(selectedIndexes[1].trim());
                Index secondIndex = ParserUtil.parseIndex(selectedIndexes[2].trim());
                return new DistanceCommand(firstIndex, secondIndex);
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new DistanceCommand(index);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistanceCommand.MESSAGE_USAGE));
        }
    }
}
