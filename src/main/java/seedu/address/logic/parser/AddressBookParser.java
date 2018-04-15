package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AccountCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditDetailsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GoogleSetLocationCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.LinkedInLoginCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShareToLinkedInCommand;
import seedu.address.logic.commands.SortCommand;
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
    public Command parseCommand(String userInput) throws ParseException, IOException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        //@@author zhuleyan
        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return new RemarkCommandParser().parse(arguments);
        //@@author davidten
        case ShareToLinkedInCommand.COMMAND_WORD:
        case ShareToLinkedInCommand.COMMAND_ALIAS:
            return new ShareToLinkedInCommandParser().parse(arguments);

        case LinkedInLoginCommand.COMMAND_WORD:
        case LinkedInLoginCommand.COMMAND_ALIAS:
            return new LinkedInLoginCommand();

        case GoogleSetLocationCommand.COMMAND_WORD:
        case GoogleSetLocationCommand.COMMAND_ALIAS:
            return new GoogleSetLocationCommandParser().parse(arguments);
        //@@author
        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        //@@author WoodyLau
        case EditDetailsCommand.COMMAND_WORD:
        case EditDetailsCommand.COMMAND_ALIAS:
            return new EditDetailsCommandParser().parse(arguments);

        case ConvertCommand.COMMAND_WORD:
        case ConvertCommand.COMMAND_ALIAS:
            return new ConvertCommandParser().parse(arguments);

        case AccountCommand.COMMAND_WORD:
            return new AccountCommandParser().parse(arguments);
        //@@author

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ChangeThemeCommand.COMMAND_WORD:
            return new ChangeThemeCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        //@@author Sheikh-Umar
        case DisplayCommand.COMMAND_WORD:
        case DisplayCommand.COMMAND_ALIAS:
            return new DisplayCommandParser().parse(arguments);
        //@@author

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
        //@@author zhuleyan
        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();

        case ImportCommand.COMMAND_WORD:
        case ImportCommand.COMMAND_ALIAS:
            return new ImportCommandParser().parse(arguments);
        //@@author
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
