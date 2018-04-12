package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.CalendarJumpCommand;
import seedu.address.logic.commands.ChangeOrderStatusCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEntryCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCalendarEntryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewBackCommand;
import seedu.address.logic.commands.ViewCalendarCommand;
import seedu.address.logic.commands.ViewNextCommand;
import seedu.address.logic.commands.ViewTodayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case AddEntryCommand.COMMAND_WORD:
        case AddEntryCommand.COMMAND_ALIAS:
            return new AddEntryCommandParser().parse(arguments);

        case AddOrderCommand.COMMAND_WORD:
        case AddOrderCommand.COMMAND_ALIAS:
            return new AddOrderCommandParser().parse(arguments);

        case CalendarJumpCommand.COMMAND_WORD:
        case CalendarJumpCommand.COMMAND_ALIAS:
            return new CalendarJumpCommandParser().parse(arguments);

        case ChangeOrderStatusCommand.COMMAND_WORD:
        case ChangeOrderStatusCommand.COMMAND_ALIAS:
            return new ChangeOrderStatusCommandParser().parse(arguments);

        case ChangeThemeCommand.COMMAND_WORD:
        case ChangeThemeCommand.COMMAND_ALIAS:
            return new ChangeThemeCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case EditEntryCommand.COMMAND_WORD:
        case EditEntryCommand.COMMAND_ALIAS:
            return new EditEntryCommandParser().parse(arguments);

        case EditOrderCommand.COMMAND_WORD:
        case EditOrderCommand.COMMAND_ALIAS:
            return new EditOrderCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DeleteEntryCommand.COMMAND_WORD:
        case DeleteEntryCommand.COMMAND_ALIAS:
            return new DeleteEntryCommandParser().parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD:
        case DeleteGroupCommand.COMMAND_ALIAS:
            return new DeleteGroupCommandParser().parse(arguments);

        case DeleteOrderCommand.COMMAND_WORD:
        case DeleteOrderCommand.COMMAND_ALIAS:
            return new DeleteOrderCommandParser().parse(arguments);

        case DeletePreferenceCommand.COMMAND_WORD:
        case DeletePreferenceCommand.COMMAND_ALIAS:
            return new DeletePreferenceCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FindGroupCommand.COMMAND_WORD:
        case FindGroupCommand.COMMAND_ALIAS:
            return new FindGroupCommandParser().parse(arguments);

        case FindPreferenceCommand.COMMAND_WORD:
        case FindPreferenceCommand.COMMAND_ALIAS:
            return new FindPreferenceCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case ListOrderCommand.COMMAND_WORD:
        case ListOrderCommand.COMMAND_ALIAS:
            return new ListOrderCommand();

        case ListCalendarEntryCommand.COMMAND_WORD:
        case ListCalendarEntryCommand.COMMAND_ALIAS:
            return new ListCalendarEntryCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case ViewBackCommand.COMMAND_WORD:
        case ViewBackCommand.COMMAND_ALIAS:
            return new ViewBackCommand();

        case ViewCalendarCommand.COMMAND_WORD:
        case ViewCalendarCommand.COMMAND_ALIAS:
            return new ViewCalendarCommand(arguments);

        case ViewNextCommand.COMMAND_WORD:
        case ViewNextCommand.COMMAND_ALIAS:
            return new ViewNextCommand();

        case ViewTodayCommand.COMMAND_WORD:
        case ViewTodayCommand.COMMAND_ALIAS:
            return new ViewTodayCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
