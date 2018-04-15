package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddSessionLogCommand;
import seedu.address.logic.commands.CalendarAddCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.CalendarDeleteCommand;
import seedu.address.logic.commands.CalendarListCommand;
import seedu.address.logic.commands.ChangeUserPasswordCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CreateUserCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteUserCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ErrorLogCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.NavigateCommand;
import seedu.address.logic.commands.OAuthTestCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShowScheduleCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.UndoCommand;

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
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CalendarCommand.COMMAND_WORD:
            return new CalendarCommand();

        case CalendarAddCommand.COMMAND_WORD:
            return new CalendarAddCommandParser().parse(arguments);

        case CalendarDeleteCommand.COMMAND_WORD:
            return new CalendarDeleteCommandParser().parse(arguments);

        case CalendarListCommand.COMMAND_WORD:
            return new CalendarListCommand();

        case ShowScheduleCommand.COMMAND_WORD:
            return new ShowScheduleCommandParser().parse(arguments);

        case ErrorLogCommand.COMMAND_WORD:
            return new ErrorLogCommand();

        case OAuthTestCommand.COMMAND_WORD:
            return new OAuthTestCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        case NavigateCommand.COMMAND_WORD:
            return new NavigateCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case CreateUserCommand.COMMAND_WORD:
            return new CreateUserCommandParser().parse(arguments);

        case DeleteUserCommand.COMMAND_WORD:
            return new DeleteUserCommandParser().parse(arguments);

        case ChangeUserPasswordCommand.COMMAND_WORD:
            return new ChangeUserPasswordCommandParser().parse(arguments);

        case AddSessionLogCommand.COMMAND_WORD:
            return new AddSessionLogCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
