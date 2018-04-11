package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.tag.Tag;

//@@author Kyomian

/**
 * Parses input arguments and creates a new EventCommand object
 */
public class EventCommandParser implements Parser<EventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EventCommand
     * and returns a EventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME,
                        PREFIX_LOCATION, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            DateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME)).get();
            DateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATETIME)).get();
            Optional<Location> location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION));
            Optional<Remark> remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK));
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Event event = new Event(name, startDateTime, endDateTime,
                    location.isPresent() ? location.get() : null,
                    remark.isPresent() ? remark.get() : null, tagList);

            return new EventCommand(event);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
