package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCellCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddUserCommand;
import seedu.address.logic.commands.CalendarAddCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.CalendarDeleteCommand;
import seedu.address.logic.commands.CheckStatusCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCellCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteUserCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCellCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ShowCellsCommand;
import seedu.address.logic.commands.ShowUsersCommand;
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
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();
        //@@author philos22
        case CalendarCommand.COMMAND_WORD:
        case CalendarCommand.COMMAND_ALIAS:
            return new CalendarCommand();

        case CalendarAddCommand.COMMAND_WORD:
        case CalendarAddCommand.COMMAND_ALIAS:
            return new CalendarAddCommandParser().parse(arguments);

        case CalendarDeleteCommand.COMMAND_WORD:
        case CalendarDeleteCommand.COMMAND_ALIAS:
            return new CalendarDeleteCommandParser().parse(arguments);

        //@@author zacci
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case CheckStatusCommand.COMMAND_WORD:
            return new CheckStatusCommand();

        case ShowUsersCommand.COMMAND_WORD:
            return new ShowUsersCommand();

        case AddUserCommand.COMMAND_WORD:
            return new AddUserCommandParser().parse(arguments);

        case DeleteUserCommand.COMMAND_WORD:
            return new DeleteUserCommandParser().parse(arguments);
        //@@author

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        //@@author sarahgoh97
        case ShowCellsCommand.COMMAND_WORD:
        case ShowCellsCommand.COMMAND_ALIAS:
            return new ShowCellsCommand();

        case AddCellCommand.COMMAND_WORD:
        case AddCellCommand.COMMAND_ALIAS:
            return new AddCellCommandParser().parse(arguments);

        case DeleteCellCommand.COMMAND_WORD:
        case DeleteCellCommand.COMMAND_ALIAS:
            return new DeleteCellCommandParser().parse(arguments);

        case ListCellCommand.COMMAND_WORD:
        case ListCellCommand.COMMAND_ALIAS:
            return new ListCellCommandParser().parse(arguments);
        //@@author

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
