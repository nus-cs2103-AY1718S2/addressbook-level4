package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BalanceCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CurrencyCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ItemAddCommand;
import seedu.address.logic.commands.ItemDeleteCommand;
import seedu.address.logic.commands.ItemShowCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListNegativeBalanceCommand;
import seedu.address.logic.commands.ListPositiveBalanceCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.MaxCommand;
import seedu.address.logic.commands.MinCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.commands.TransactionCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WipeBalancesCommand;
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

        case AddCommand.COMMAND_SHORTCUT:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditCommand.COMMAND_SHORTCUT:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case SelectCommand.COMMAND_SHORTCUT:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_SHORTCUT:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_SHORTCUT:
            return new ClearCommand();

        case CurrencyCommand.COMMAND_WORD:
            return new CurrencyCommandParser().parse(arguments);

        case CurrencyCommand.COMMAND_SHORTCUT:
            return new CurrencyCommandParser().parse(arguments);

        case WipeBalancesCommand.COMMAND_WORD:
            return new WipeBalancesCommand();

        case WipeBalancesCommand.COMMAND_SHORTCUT:
            return new WipeBalancesCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindCommand.COMMAND_SHORTCUT:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCommand.COMMAND_SHORTCUT:
            return new ListCommand();

        case ListPositiveBalanceCommand.COMMAND_WORD:
            return new ListPositiveBalanceCommand();

        case ListPositiveBalanceCommand.COMMAND_SHORTCUT:
            return new ListPositiveBalanceCommand();

        case ListNegativeBalanceCommand.COMMAND_WORD:
            return new ListNegativeBalanceCommand();

        case ListNegativeBalanceCommand.COMMAND_SHORTCUT:
            return new ListNegativeBalanceCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case HistoryCommand.COMMAND_SHORTCUT:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HelpCommand.COMMAND_SHORTCUT:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case UndoCommand.COMMAND_SHORTCUT:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RedoCommand.COMMAND_SHORTCUT:
            return new RedoCommand();
        //@@author Articho28
        case BalanceCommand.COMMAND_SHORTCUT:
            return new BalanceCommand();

        case BalanceCommand.COMMAND_WORD:
            return new BalanceCommand();
        //@@author

        //@@author pkuhanan
        case MaxCommand.COMMAND_WORD:
            return new MaxCommand();

        case MaxCommand.COMMAND_SHORTCUT:
            return new MaxCommand();
        //@@author

            //@@author Articho28
        case MinCommand.COMMAND_WORD:
            return new MinCommand();

        case MinCommand.COMMAND_SHORTCUT:
            return new MinCommand();
        //@@author

        //@@author pkuhanan
        case SettleCommand.COMMAND_WORD:
            return new SettleCommandParser().parse(arguments);

        case SettleCommand.COMMAND_SHORTCUT:
            return new SettleCommandParser().parse(arguments);
        //@@author

        //@@author chenchongsong
        case RemoveTagCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);

        case RemoveTagCommand.COMMAND_SHORTCUT:
            return new RemoveTagCommandParser().parse(arguments);

        case SplitCommand.COMMAND_WORD:
            return new SplitCommandParser().parse(arguments);

        case SplitCommand.COMMAND_SHORTCUT:
            return new SplitCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case SortCommand.COMMAND_SHORTCUT:
            return new SortCommandParser().parse(arguments);

        case ItemShowCommand.COMMAND_WORD:
            return new ItemShowCommandParser().parse(arguments);

        case ItemShowCommand.COMMAND_SHORTCUT:
            return new ItemShowCommandParser().parse(arguments);

        case ItemAddCommand.COMMAND_WORD:
            return new ItemAddCommandParser().parse(arguments);

        case ItemAddCommand.COMMAND_SHORTCUT:
            return new ItemAddCommandParser().parse(arguments);

        case ItemDeleteCommand.COMMAND_WORD:
            return new ItemDeleteCommandParser().parse(arguments);

        case ItemDeleteCommand.COMMAND_SHORTCUT:
            return new ItemDeleteCommandParser().parse(arguments);
        //@@author

        //@@author Articho28
        case MapCommand.COMMAND_WORD:
            return new MapCommand();
        case MapCommand.COMMAND_SHORTCUT:
            return new MapCommand();
        //@@author

        //@@author pkuhanan
        case RemindCommand.COMMAND_WORD:
            return new RemindCommandParser().parse(arguments);
        case RemindCommand.COMMAND_SHORTCUT:
            return new RemindCommandParser().parse(arguments);

        case TransactionCommand.COMMAND_WORD:
            return new TransactionCommandParser().parse(arguments);

        case TransactionCommand.COMMAND_SHORTCUT:
            return new TransactionCommandParser().parse(arguments);
        //@@author

        //@@author Articho28
        case SearchTagCommand.COMMAND_WORD:
            return new SearchTagCommandParser().parse(arguments);
        case SearchTagCommand.COMMAND_SHORTCUT:
            return new SearchTagCommandParser().parse(arguments);
        //@@author
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
