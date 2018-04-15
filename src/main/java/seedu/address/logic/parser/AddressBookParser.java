package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CompleteMoreOrderCommand;
import seedu.address.logic.commands.CompleteOneOrderCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.PathCommand;
import seedu.address.logic.commands.ProcessMoreCommand;
import seedu.address.logic.commands.ProcessNextCommand;
import seedu.address.logic.commands.ProcessOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TagOrderCommand;
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

        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case ProcessOrderCommand.COMMAND_WORD:
            return new ProcessOrderCommandParser().parse(arguments);

        case ProcessOrderCommand.COMMAND_ALIAS:
            return new ProcessOrderCommandParser().parse(arguments);

        case ProcessNextCommand.COMMAND_WORD:
            return new ProcessNextCommandParser().parse(arguments);

        case ProcessNextCommand.COMMAND_ALIAS:
            return new ProcessNextCommandParser().parse(arguments);

        case ProcessMoreCommand.COMMAND_WORD:
            return new ProcessMoreCommandParser().parse(arguments);

        case ProcessMoreCommand.COMMAND_ALIAS:
            return new ProcessMoreCommandParser().parse(arguments);

        case CompleteOneOrderCommand.COMMAND_WORD:
            return new CompleteOneOrderCommandParser().parse(arguments);

        case CompleteOneOrderCommand.COMMAND_ALIAS:
            return new CompleteOneOrderCommandParser().parse(arguments);

        case CompleteMoreOrderCommand.COMMAND_WORD:
            return new CompleteMoreOrderCommandParser().parse(arguments);

        case CompleteMoreOrderCommand.COMMAND_ALIAS:
            return new CompleteMoreOrderCommandParser().parse(arguments);

        case TagOrderCommand.COMMAND_WORD:
            return new TagOrderCommandParser().parse(arguments);

        case LoadCommand.COMMAND_WORD:
            return new LoadCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case PathCommand.COMMAND_WORD:
            return new PathCommandParser().parse(arguments);

        case PathCommand.COMMAND_ALIAS:
            return new PathCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
