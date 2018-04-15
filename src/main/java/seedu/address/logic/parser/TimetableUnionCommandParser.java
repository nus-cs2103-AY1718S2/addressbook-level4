package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TimetableUnionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author AzuraAiR
/**
 * Parses input arguments and creates a new TimetableUnionCommand object
 */
public class TimetableUnionCommandParser implements Parser<TimetableUnionCommand> {
    private static final String SPLIT_TOKEN = " ";
    private static final int ODD_EVEN_INDEX = 0;
    /**
     * Parses the given {@code String} of arguments in the context of the TimetableUnionCommand
     * and returns an TimetableUnionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TimetableUnionCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
            if (splitArgs.length < 3) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableUnionCommand.MESSAGE_USAGE));
            }
            ArrayList<Index> indexes = new ArrayList<Index>();
            String oddEven = ParserUtil.parseOddEven(splitArgs[ODD_EVEN_INDEX]);

            for (int i = ODD_EVEN_INDEX + 1; i < splitArgs.length; i++) {
                Index indexToAdd = ParserUtil.parseIndex(splitArgs[i]);
                if (!indexes.contains(indexToAdd)) {
                    indexes.add(indexToAdd);
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            TimetableUnionCommand.MESSAGE_USAGE));
                }

            }

            return new TimetableUnionCommand(indexes, oddEven);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableUnionCommand.MESSAGE_USAGE));
        }
    }

}

