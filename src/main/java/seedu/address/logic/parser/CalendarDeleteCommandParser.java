//@@author ifalluphill

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;

import com.google.api.services.calendar.model.Event;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.OAuthManager;
import seedu.address.logic.commands.CalendarDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class CalendarDeleteCommandParser implements Parser<CalendarDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CalendarDeleteCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseCalendarDeleteIndex(args);

            Event eventToDelete = OAuthManager.getEventByIndexFromLastList(index);

            return new CalendarDeleteCommand(eventToDelete);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, CalendarDeleteCommand.MESSAGE_USAGE));
        }
    }

}

//@@author
