//@@author ifalluphill

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;

import java.util.List;

import com.google.api.services.calendar.model.Event;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.InvalidCalendarEventCountException;
import seedu.address.logic.OAuthManager;
import seedu.address.logic.commands.NavigateCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class NavigateCommandParser implements Parser<NavigateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NavigateCommand parse(String args) throws ParseException {
        try {
            int index = ParserUtil.parseCalendarDeleteIndex(args);

            List<Event> eventPair = OAuthManager.getEventByIndexPairFromDailyList(index);

            return new NavigateCommand(eventPair);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX));
        } catch (InvalidCalendarEventCountException e) {
            throw new ParseException(
                    String.format(NavigateCommand.MESSAGE_NO_EVENT));
        }
    }

}

//@@author
