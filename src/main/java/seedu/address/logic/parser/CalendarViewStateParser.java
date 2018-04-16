package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearEventsCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.ui.CalendarView;
import seedu.address.ui.CalendarViewUpdate;

//@@author x3tsunayh

/**
* Parses user input to update calendar UI state.
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

        //Checks whether CalendarView is null
        requireNonNull(calendarView);

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");

        if (commandWord.equals(AddEventCommand.COMMAND_WORD)
                || commandWord.equals(DeleteEventCommand.COMMAND_WORD)
                || commandWord.equals(ClearCommand.COMMAND_WORD)
                || commandWord.equals(ClearCommand.COMMAND_ALIAS)
                || commandWord.equals(UndoCommand.COMMAND_WORD)
                || commandWord.equals(UndoCommand.COMMAND_ALIAS)
                || commandWord.equals(RedoCommand.COMMAND_WORD)
                || commandWord.equals(RedoCommand.COMMAND_ALIAS)
                || commandWord.equals(ClearEventsCommand.COMMAND_WORD)) {
            CalendarViewUpdate.updateViewState(calendarView);
        } else if (commandWord.equals(FindEventCommand.COMMAND_WORD)) {
            CalendarViewUpdate.updateFindState(calendarView, model);
        }
    }
}
