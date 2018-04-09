package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.AddMembersToGroupCommand;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.CheckToDoCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditToDoCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListGroupMembersCommand;
import seedu.address.logic.commands.ListTagMembersCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.UnCheckToDoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class    AddressBookParser {

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

        case AddGroupCommand.COMMAND_WORD:
        case AddGroupCommand.COMMAND_ALIAS:
            return new AddGroupCommandParser().parse(arguments);

        case AddMembersToGroupCommand.COMMAND_WORD:
        case AddMembersToGroupCommand.COMMAND_ALIAS:
            return new AddMembersToGroupCommandParser().parse(arguments);

        case AddToDoCommand.COMMAND_WORD:
        case AddToDoCommand.COMMAND_ALIAS:
            return new AddToDoCommandParser().parse(arguments);

        case EditToDoCommand.COMMAND_WORD:
        case EditToDoCommand.COMMAND_ALIAS:
            return new EditToDoCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case CheckToDoCommand.COMMAND_WORD:
            return new CheckToDoCommandParser().parse(arguments);

        case UnCheckToDoCommand.COMMAND_WORD:
            return new UnCheckToDoCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DeleteToDoCommand.COMMAND_WORD:
        case DeleteToDoCommand.COMMAND_ALIAS:
            return new DeleteToDoCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case ListGroupMembersCommand.COMMAND_WORD:
        case ListGroupMembersCommand.COMMAND_ALIAS:
            return new ListGroupMembersCommandParser().parse(arguments);

        case ListTagMembersCommand.COMMAND_WORD:
        case ListTagMembersCommand.COMMAND_ALIAS:
            return new ListTagMembersCommandParser().parse(arguments);

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

        //@@author LeonidAgarth
        case ChangeTagColorCommand.COMMAND_WORD:
        case ChangeTagColorCommand.COMMAND_ALIAS:
            return new ChangeTagColorCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
        case AddEventCommand.COMMAND_ALIAS:
            return new AddEventCommandParser().parse(arguments);

        case SwitchCommand.COMMAND_WORD:
        case SwitchCommand.COMMAND_ALIAS:
            return new SwitchCommand();
        //@@author
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
