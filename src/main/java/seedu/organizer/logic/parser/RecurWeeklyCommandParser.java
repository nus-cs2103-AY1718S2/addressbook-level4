package seedu.organizer.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TIMES;

import java.util.Optional;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.RecurWeeklyCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

//@@author natania
/**
 * Parses input arguments and creates a new RecurWeeklyCommand object
 */
public class RecurWeeklyCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the RecurWeeklyCommand
     * and returns an RecurWeeklyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecurWeeklyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIMES);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurWeeklyCommand.MESSAGE_USAGE));
        }

        Optional<Integer> times;
        try {
            times = ParserUtil.parseTimes(argMultimap.getValue(PREFIX_TIMES));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        int time = -1;
        if (times.isPresent()) {
            time = times.get();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurWeeklyCommand.MESSAGE_USAGE));
        }

        return new RecurWeeklyCommand(index, time);
    }
}
