//@@author ongkuanyang
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEZONE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentName;
import seedu.address.model.appointment.AppointmentTime;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATETIME, PREFIX_TIMEZONE);

        List<Index> indexes;

        try {
            indexes = ParserUtil.parseIndexes(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATETIME, PREFIX_TIMEZONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            AppointmentName name = ParserUtil.parseAppointmentName(argMultimap.getValue(PREFIX_NAME)).get();
            AppointmentTime time = ParserUtil.parseAppointmentTime(argMultimap.getValue(PREFIX_DATETIME),
                    argMultimap.getValue(PREFIX_TIMEZONE)).get();
            return new AddAppointmentCommand(name, time, indexes);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
//@@author
