package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMOFTHEATERS;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTheaterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cinema.Theater;
//@@author tinyjy94
/**
 * Parses input arguments and creates a new AddTheaterCommand object
 */
public class AddTheaterCommandParser implements Parser<AddTheaterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTheaterCommand
     * and returns an AddTheaterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTheaterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NUMOFTHEATERS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NUMOFTHEATERS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTheaterCommand.MESSAGE_USAGE));
        }

        Index index;
        ArrayList<Theater> newTheaters;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTheaterCommand.MESSAGE_USAGE));
        }

        try {
            newTheaters = ParserUtil.parseTheaters(argMultimap.getValue(PREFIX_NUMOFTHEATERS)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddTheaterCommand(index, newTheaters.size());
    }

    /**
      * Returns true if none of the prefixes contains empty {@code Optional} values in the given
      * {@code ArgumentMultimap}.
      */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
