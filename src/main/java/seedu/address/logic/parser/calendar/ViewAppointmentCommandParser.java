package seedu.address.logic.parser.calendar;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.calendar.ViewAppointmentCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.map.Map;

//@@author Damienskt
/**
 * Reads {@code args} and checks if the input has all the necessary values
 */
public class ViewAppointmentCommandParser implements Parser<ViewAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAppointmentCommand
     * and returns an ViewAppointmentCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ViewAppointmentCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewAppointmentCommand(index.getZeroBased());
        } catch (IllegalValueException ive) {
            Map.removeExistingMarker();
            Map.clearRoute();
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
