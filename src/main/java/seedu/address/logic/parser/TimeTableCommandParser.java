package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TimeTableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author yeggasd
/**
 * Parses input arguments and creates a new TimeTableCommand object
 */
public class TimeTableCommandParser implements Parser<TimeTableCommand> {
    private static final String SPLIT_TOKEN = " ";
    private static final int PERSON_INDEX = 0;
    private static final int ODD_EVEN_INDEX = 1;
    private static final int EVEN = 0;
    private static final int ODD = 1;
    /**
     * Parses the given {@code String} of arguments in the context of the TimeTableCommand
     * and returns an TimeTableCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TimeTableCommand parse(String args) throws ParseException {
        try {
            args = args.trim();
            String[] splitArgs = args.split(SPLIT_TOKEN);
            if (splitArgs.length != 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeTableCommand.MESSAGE_USAGE));
            }
            Index index = ParserUtil.parseIndex(splitArgs[PERSON_INDEX]);
            String oddEven = ParserUtil.parseOddEven(splitArgs[ODD_EVEN_INDEX]);
            if (oddEven.equalsIgnoreCase("odd")) {
                return new TimeTableCommand(index, ODD);
            } else {
                return new TimeTableCommand(index, EVEN);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeTableCommand.MESSAGE_USAGE));
        }
    }

}
