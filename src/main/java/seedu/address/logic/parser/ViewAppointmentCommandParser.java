//@@author Kyholmes
package seedu.address.logic.parser;

import java.util.Arrays;

import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewAppointmentCommand object
 */
public class ViewAppointmentCommandParser implements Parser<ViewAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAppointmentCommandParser
     * and returns an ViewAppointmentCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewAppointmentCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new ViewAppointmentCommand();
        }

        String trimmedArgs = args.trim();

        String[] nameKeys = trimmedArgs.split("\\s");

        return new ViewAppointmentCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeys)));
    }
}
