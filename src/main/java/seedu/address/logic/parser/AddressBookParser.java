package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.BirthdaysCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemovePasswordCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TimetableUnionCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UploadCommand;
import seedu.address.logic.commands.VacantCommand;
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
     * @param commandWord commandWord from the user input
     * @param arguments arguments from the user input
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
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

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case UploadCommand.COMMAND_WORD:
            return new UploadCommandParser().parse(arguments);

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);

        case UnaliasCommand.COMMAND_WORD:
            return new UnaliasCommandParser().parse(arguments);

        case VacantCommand.COMMAND_WORD:
            return new VacantCommandParser().parse(arguments);

        case PasswordCommand.COMMAND_WORD:
            return new PasswordCommandParser().parse(arguments);

        case BirthdaysCommand.COMMAND_WORD:
            return new BirthdaysCommandParser().parse(arguments);

        case RemovePasswordCommand.COMMAND_WORD:
            return new RemovePasswordCommandParser().parse(arguments);

        case MapCommand.COMMAND_WORD:
            return new MapCommandParser().parse(arguments);

        case TimetableUnionCommand.COMMAND_WORD:
            return new TimetableUnionCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author jingyinno
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return String[] of size 2, consists of commandWord and arguments
     * @throws ParseException if the user input does not conform the expected format
     */
    public String[] extractCommandArgs(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        return new String[] {matcher.group("commandWord"), matcher.group("arguments")};
    }
    //@@author
}
