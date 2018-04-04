package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SCREEN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CINEMA_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOVIE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMOFTHEATERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCREENING_DATE_TIME;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddScreeningCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author qwlai
/**
 * Parses input arguments and creates a new AddScreeningCommand object
 */
public class AddScreeningCommandParser implements Parser<AddScreeningCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddScreeningCommand
     * and returns an AddScreeningCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddScreeningCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MOVIE_INDEX, PREFIX_CINEMA_INDEX,
                        PREFIX_NUMOFTHEATERS, PREFIX_SCREENING_DATE_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_MOVIE_INDEX, PREFIX_CINEMA_INDEX, PREFIX_NUMOFTHEATERS,
                PREFIX_SCREENING_DATE_TIME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScreeningCommand.MESSAGE_USAGE));
        }

        try {
            Index cinemaIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CINEMA_INDEX).get());
            Index movieIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MOVIE_INDEX).get());
            int theaterNumber = ParserUtil.parseTheaterNumber(argMultimap.getValue(PREFIX_NUMOFTHEATERS).get());
            LocalDateTime screeningDateTime = ParserUtil.parseScreeningDateTime(
                    argMultimap.getValue(PREFIX_SCREENING_DATE_TIME).get());

            return new AddScreeningCommand(movieIndex, cinemaIndex, theaterNumber, screeningDateTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_SCREEN_DATE_TIME, dtpe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
