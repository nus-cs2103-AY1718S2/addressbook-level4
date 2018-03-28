package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TITLE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.Event;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand
     * and returns an AddEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_EVENT_TITLE, PREFIX_EVENT_DESCRIPTION, PREFIX_EVENT_LOCATION, PREFIX_EVENT_DATETIME);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_EVENT_TITLE, PREFIX_EVENT_DESCRIPTION, PREFIX_EVENT_LOCATION, PREFIX_EVENT_DATETIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        String title = argMultimap.getValue(PREFIX_EVENT_TITLE).get();
        String description = argMultimap.getValue(PREFIX_EVENT_DESCRIPTION).get();
        String location = argMultimap.getValue(PREFIX_EVENT_LOCATION).get();
        String datetime = argMultimap.getValue(PREFIX_EVENT_DATETIME).get();

        CalendarEvent event = new Event(title, description, location, datetime);

        return new AddEventCommand(event);
    }
}
