//@@author Kyholmes
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewAppointmentCommand object
 */
public class ViewAppointmentCommandParser implements Parser<ViewAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAppointmentCommandParser
     * and returns an ViewAppointmentCommandParser object for execution.
     * If argument is empty, construct ViewAppointmentCommand object without parameter and return the object.
     * If arguement is not empty, parse argument to {@Index} object and construct ViewAppointmentCommand object with
     * parameter and return the object.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewAppointmentCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new ViewAppointmentCommand();
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewAppointmentCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAppointmentCommand.MESSAGE_USAGE_PATIENT_WITH_INDEX)));
        }
    }
}
