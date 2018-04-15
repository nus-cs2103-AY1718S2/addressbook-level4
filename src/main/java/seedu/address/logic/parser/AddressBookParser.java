package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTemplateCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteBeforeCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTemplateCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportContactsCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GoBackwardCommand;
import seedu.address.logic.commands.GoForwardCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ZoomInCommand;
import seedu.address.logic.commands.ZoomOutCommand;
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

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        //@@author luca590
        case ImportContactsCommand.COMMAND_WORD: //import contacts from csv
            return new ImportContactsCommandParser().parse(arguments);

        case ImportContactsCommand.COMMAND_ALIAS:
            return new ImportContactsCommandParser().parse(arguments);
        //@@author

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
        //@@author ng95junwei
        case EmailCommand.COMMAND_WORD:
            return new EmailCommandParser().parse(arguments);

        case EmailCommand.COMMAND_ALIAS:
            return new EmailCommandParser().parse(arguments);

        case AddTemplateCommand.COMMAND_WORD:
            return new AddTemplateCommandParser().parse(arguments);

        case AddTemplateCommand.COMMAND_ALIAS:
            return new AddTemplateCommandParser().parse(arguments);

        case DeleteTemplateCommand.COMMAND_WORD:
            return new DeleteTemplateCommandParser().parse(arguments);

        case DeleteTemplateCommand.COMMAND_ALIAS:
            return new DeleteTemplateCommandParser().parse(arguments);
        //@@author

        //@@author luca590
        case ExportContactsCommand.COMMAND_WORD: //export contacts from csv
            return new ExportContactsCommandParser().parse(arguments);

        case ExportContactsCommand.COMMAND_ALIAS:
            return new ExportContactsCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();
        //@@author

        //@@author jlks96
        case DeleteBeforeCommand.COMMAND_WORD:
            return new DeleteBeforeCommandParser().parse(arguments);

        case DeleteBeforeCommand.COMMAND_ALIAS:
            return new DeleteBeforeCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_ALIAS:
            return new AddAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_ALIAS:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case ZoomInCommand.COMMAND_WORD:
            return new ZoomInCommand();

        case ZoomInCommand.COMMAND_ALIAS:
            return new ZoomInCommand();

        case ZoomOutCommand.COMMAND_WORD:
            return new ZoomOutCommand();

        case ZoomOutCommand.COMMAND_ALIAS:
            return new ZoomOutCommand();

        case GoBackwardCommand.COMMAND_WORD:
            return new GoBackwardCommand();

        case GoBackwardCommand.COMMAND_ALIAS:
            return new GoBackwardCommand();

        case GoForwardCommand.COMMAND_WORD:
            return new GoForwardCommand();

        case GoForwardCommand.COMMAND_ALIAS:
            return new GoForwardCommand();
        //@@author

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
