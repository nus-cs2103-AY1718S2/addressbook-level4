package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddConditionCommand;
import seedu.address.logic.commands.AddPatientQueueCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteConditionCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.PrintCommand;
import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemovePatientQueueCommand;
import seedu.address.logic.commands.RemoveRecordCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.logic.login.LoginManager;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ImdbParser {

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
        int state = LoginManager.getUserState();

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (state == LoginManager.NO_USER) {
            switch (commandWord) {
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case ExitCommand.COMMAND_ALIAS:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case HelpCommand.COMMAND_ALIAS:
                return new HelpCommand();

            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();

            case HistoryCommand.COMMAND_ALIAS:
                return new HistoryCommand();

            case LoginCommand.COMMAND_WORD:
                return new LoginCommandParser().parse(arguments);

            case LoginCommand.COMMAND_ALIAS:
                return new LoginCommandParser().parse(arguments);

            case AddCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case AddCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case EditCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case EditCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case SelectCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case SelectCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case DeleteCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case DeleteCommand.COMMAND_ALIAS1:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case DeleteCommand.COMMAND_ALIAS2:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case ClearCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case ClearCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case FindCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case FindCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case ListCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case ListCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case UndoCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case UndoCommand.COMMAND_ALIAS1:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case UndoCommand.COMMAND_ALIAS2:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RedoCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RedoCommand.COMMAND_ALIAS1:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RedoCommand.COMMAND_ALIAS2:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RemarkCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RemarkCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case ViewAppointmentCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case ViewAppointmentCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case AddPatientQueueCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case AddPatientQueueCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RemovePatientQueueCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RemovePatientQueueCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case AddConditionCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case AddConditionCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case PrintCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case PrintCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case DeleteConditionCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        DeleteConditionCommand.MESSAGE_USAGE));

            case DeleteConditionCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        DeleteConditionCommand.MESSAGE_USAGE));

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else if (state == LoginManager.DOCTOR_LOGIN) {
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

            case DeleteCommand.COMMAND_ALIAS1:
                return new DeleteCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_ALIAS2:
                return new DeleteCommandParser().parse(arguments);

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

            case LoginCommand.COMMAND_WORD:
                throw new ParseException(LoginCommand.MESSAGE_ALREADY_LOGGED_IN);

            case LoginCommand.COMMAND_ALIAS:
                throw new ParseException(LoginCommand.MESSAGE_ALREADY_LOGGED_IN);

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case UndoCommand.COMMAND_ALIAS1:
                return new UndoCommand();

            case UndoCommand.COMMAND_ALIAS2:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case RedoCommand.COMMAND_ALIAS1:
                return new RedoCommand();

            case RedoCommand.COMMAND_ALIAS2:
                return new RedoCommand();

            case RemarkCommand.COMMAND_WORD:
                return new RemarkCommandParser().parse(arguments);

            case RemarkCommand.COMMAND_ALIAS:
                return new RemarkCommandParser().parse(arguments);

            case ViewAppointmentCommand.COMMAND_WORD:
                return new ViewAppointmentCommandParser().parse(arguments);

            case ViewAppointmentCommand.COMMAND_ALIAS:
                return new ViewAppointmentCommandParser().parse(arguments);

            case RecordCommand.COMMAND_WORD:
                return new RecordCommandParser().parse(arguments);

            case RecordCommand.COMMAND_ALIAS:
                return new RecordCommandParser().parse(arguments);

            case RemoveRecordCommand.COMMAND_WORD:
                return new RemoveRecordCommandParser().parse(arguments);

            case RemoveRecordCommand.COMMAND_ALIAS:
                return new RemoveRecordCommandParser().parse(arguments);

            case AddConditionCommand.COMMAND_WORD:
                return new AddConditionCommandParser().parse(arguments);

            case AddConditionCommand.COMMAND_ALIAS:
                return new AddConditionCommandParser().parse(arguments);

            case DeleteConditionCommand.COMMAND_WORD:
                return new DeleteConditionCommandParser().parse(arguments);

            case DeleteConditionCommand.COMMAND_ALIAS:
                return new DeleteConditionCommandParser().parse(arguments);

            case PrintCommand.COMMAND_WORD:
                return new PrintCommandParser().parse(arguments);

            case PrintCommand.COMMAND_ALIAS:
                return new PrintCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else if (state == LoginManager.MEDICAL_STAFF_LOGIN) {
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

            case DeleteCommand.COMMAND_ALIAS1:
                return new DeleteCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_ALIAS2:
                return new DeleteCommandParser().parse(arguments);

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

            case LoginCommand.COMMAND_WORD:
                throw new ParseException(LoginCommand.MESSAGE_ALREADY_LOGGED_IN);

            case LoginCommand.COMMAND_ALIAS:
                throw new ParseException(LoginCommand.MESSAGE_ALREADY_LOGGED_IN);

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case UndoCommand.COMMAND_ALIAS1:
                return new UndoCommand();

            case UndoCommand.COMMAND_ALIAS2:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            case RedoCommand.COMMAND_ALIAS1:
                return new RedoCommand();

            case RedoCommand.COMMAND_ALIAS2:
                return new RedoCommand();

            case RemarkCommand.COMMAND_WORD:
                return new RemarkCommandParser().parse(arguments);

            case RemarkCommand.COMMAND_ALIAS:
                return new RemarkCommandParser().parse(arguments);

            case ViewAppointmentCommand.COMMAND_WORD:
                return new ViewAppointmentCommandParser().parse(arguments);

            case ViewAppointmentCommand.COMMAND_ALIAS:
                return new ViewAppointmentCommandParser().parse(arguments);

            case DeleteAppointmentCommand.COMMAND_WORD:
                return new DeleteAppointmentCommandParser().parse(arguments);

            case DeleteAppointmentCommand.COMMAND_ALIAS:
                return new DeleteAppointmentCommandParser().parse(arguments);
            case AddAppointmentCommand.COMMAND_WORD:
                return new AddAppointmentCommandParser().parse(arguments);
            case AddAppointmentCommand.COMMAND_ALIAS:
                return new AddAppointmentCommandParser().parse(arguments);

            case AddPatientQueueCommand.COMMAND_WORD:
                return new AddPatientQueueCommandParser().parse(arguments);

            case AddPatientQueueCommand.COMMAND_ALIAS:
                return new AddPatientQueueCommandParser().parse(arguments);

            case RemovePatientQueueCommand.COMMAND_WORD:
                return new RemovePatientQueueCommandParser().parse(arguments);

            case RemovePatientQueueCommand.COMMAND_ALIAS:
                return new RemovePatientQueueCommandParser().parse(arguments);

            case AddConditionCommand.COMMAND_WORD:
                return new AddConditionCommandParser().parse(arguments);

            case AddConditionCommand.COMMAND_ALIAS:
                return new AddConditionCommandParser().parse(arguments);

            case DeleteConditionCommand.COMMAND_WORD:
                return new DeleteConditionCommandParser().parse(arguments);

            case DeleteConditionCommand.COMMAND_ALIAS:
                return new DeleteConditionCommandParser().parse(arguments);

            case PrintCommand.COMMAND_WORD:
                return new PrintCommandParser().parse(arguments);

            case PrintCommand.COMMAND_ALIAS:
                return new PrintCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            // should never get here, this is just for bug detection
            throw new ParseException("Invalid State!");
        }
    }
}
