package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommandParser
     * and returns an AddAppointmentCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        String[] argsArray = trimmedArgs.split("\\s");
        return null;
    }
}
