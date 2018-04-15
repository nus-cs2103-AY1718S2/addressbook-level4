package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearHistoryCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GoogleLoginCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdatePasswordCommand;
import seedu.address.logic.commands.UpdateUsernameCommand;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.appointment.CalendarCommand;
import seedu.address.logic.commands.appointment.DateCommand;
import seedu.address.logic.commands.appointment.DateTimeCommand;
import seedu.address.logic.commands.appointment.DeleteAppointmentCommand;
import seedu.address.logic.commands.appointment.MonthCommand;
import seedu.address.logic.commands.appointment.WeekCommand;
import seedu.address.logic.commands.appointment.YearCommand;
import seedu.address.logic.commands.job.JobAddCommand;
import seedu.address.logic.commands.job.JobDeleteCommand;
import seedu.address.logic.commands.job.JobEditCommand;
import seedu.address.logic.commands.job.JobFindCommand;
import seedu.address.logic.commands.job.JobListCommand;
import seedu.address.logic.commands.job.JobMatchCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.LinkedInCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.SelectCommand;
import seedu.address.logic.parser.appointment.AddAppointmentCommandParser;
import seedu.address.logic.parser.appointment.DateCommandParser;
import seedu.address.logic.parser.appointment.DateTimeCommandParser;
import seedu.address.logic.parser.appointment.DeleteAppointmentCommandParser;
import seedu.address.logic.parser.appointment.MonthCommandParser;
import seedu.address.logic.parser.appointment.WeekCommandParser;
import seedu.address.logic.parser.appointment.YearCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.job.JobAddCommandParser;
import seedu.address.logic.parser.job.JobDeleteCommandParser;
import seedu.address.logic.parser.job.JobEditCommandParser;
import seedu.address.logic.parser.job.JobFindCommandParser;
import seedu.address.logic.parser.job.JobMatchCommandParser;
import seedu.address.logic.parser.person.AddCommandParser;
import seedu.address.logic.parser.person.DeleteCommandParser;
import seedu.address.logic.parser.person.EditCommandParser;
import seedu.address.logic.parser.person.FindCommandParser;
import seedu.address.logic.parser.person.LinkedInCommandParser;
import seedu.address.logic.parser.person.SelectCommandParser;


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

        case ClearHistoryCommand.COMMAND_WORD:
            return new ClearHistoryCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case UpdateUsernameCommand.COMMAND_WORD:
            return new UpdateUsernameCommandParser().parse(arguments);

        case UpdatePasswordCommand.COMMAND_WORD:
            return new UpdatePasswordCommandParser().parse(arguments);

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

        case LinkedInCommand.COMMAND_WORD:
            return new LinkedInCommandParser().parse(arguments);

        case EmailCommand.COMMAND_WORD:
            return new EmailCommandParser().parse(arguments);

        case GoogleLoginCommand.COMMAND_WORD:
            return new GoogleLoginCommand();

        case CalendarCommand.COMMAND_WORD:
            return new CalendarCommand();

        case JobAddCommand.COMMAND_WORD:
            return new JobAddCommandParser().parse(arguments);

        case JobMatchCommand.COMMAND_WORD:
            return new JobMatchCommandParser().parse(arguments);

        case JobEditCommand.COMMAND_WORD:
            return new JobEditCommandParser().parse(arguments);

        case JobDeleteCommand.COMMAND_WORD:
            return new JobDeleteCommandParser().parse(arguments);

        case JobFindCommand.COMMAND_WORD:
            return new JobFindCommandParser().parse(arguments);

        case JobListCommand.COMMAND_WORD:
            return new JobListCommand();

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case DateCommand.COMMAND_WORD:
            return new DateCommandParser().parse(arguments);

        case DateTimeCommand.COMMAND_WORD:
            return new DateTimeCommandParser().parse(arguments);

        case WeekCommand.COMMAND_WORD:
            return new WeekCommandParser().parse(arguments);

        case MonthCommand.COMMAND_WORD:
            return new MonthCommandParser().parse(arguments);

        case YearCommand.COMMAND_WORD:
            return new YearCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
