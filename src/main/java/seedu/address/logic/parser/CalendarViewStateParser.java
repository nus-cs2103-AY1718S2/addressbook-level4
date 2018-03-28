package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.ui.CalendarView;

/**
* Parses user input for the calendar UI state.
*/
public class CalendarViewStateParser {

    private static UserPrefs userPrefs;
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final Model model;
    private CalendarView calendarView;

    public CalendarViewStateParser(UserPrefs userPrefs, Model model, CalendarView calendarView) {
        this.userPrefs = userPrefs;
        this.model = model;
        this.calendarView = calendarView;
    }

    /**
     * update the state of the calendar UI object with reference to the user input
     *
     * @param userInput
     * @throws ParseException
     */
    public void updateViewState(String userInput) throws ParseException {
        //Check whether CalendarView is a null object
        if (calendarView == null) {
            return;
        }
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

    }
}
