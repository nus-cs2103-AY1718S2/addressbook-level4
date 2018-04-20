//@@author Kyholmes
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.DateTime;

/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {

    private static final int NO_OF_ARGUMENTS = 3;
    private static final int PATIENT_INDEX_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int TIME_INDEX = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns an DeleteAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }

        String[] argsArray = trimmedArgs.split("\\s");

        if (argsArray.length < NO_OF_ARGUMENTS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            Index targetPatientIndex = ParserUtil.parseIndex(argsArray[PATIENT_INDEX_INDEX]);
            DateTime appointmentDateTime = ParserUtil.parseDateTime(argsArray[DATE_INDEX] + " "
                    + argsArray[TIME_INDEX]);
            return new DeleteAppointmentCommand(targetPatientIndex, appointmentDateTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }
    }
}
