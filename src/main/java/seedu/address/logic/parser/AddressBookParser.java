package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAppointmentToPetCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPetCommand;
import seedu.address.logic.commands.AddVetTechToAppointmentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePetCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveAppointmentFromPetCommand;
import seedu.address.logic.commands.RemoveVetTechFromAppointmentCommand;
import seedu.address.logic.commands.RescheduleCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SortAppointmentCommand;
import seedu.address.logic.commands.SortClientCommand;
import seedu.address.logic.commands.SortPetCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnscheduleCommand;
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

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommandParser().parse(arguments);

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

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        case RescheduleCommand.COMMAND_WORD:
            return new RescheduleCommandParser().parse(arguments);

        case UnscheduleCommand.COMMAND_WORD:
            return new UnscheduleCommandParser().parse(arguments);

        case AddPetCommand.COMMAND_WORD:
            return new AddPetCommandParser().parse(arguments);

        case DeletePetCommand.COMMAND_WORD:
        case DeletePetCommand.COMMAND_ALIAS:
            return new DeletePetCommandParser().parse(arguments);

        case SortClientCommand.COMMAND_WORD:
            return new SortClientCommand();

        case SortPetCommand.COMMAND_WORD:
            return new SortPetCommand();

        case SortAppointmentCommand.COMMAND_WORD:
            return new SortAppointmentCommand();

        case AddAppointmentToPetCommand.COMMAND_WORD:
            return new AddAppointmentToPetCommandParser().parse(arguments);

        case RemoveAppointmentFromPetCommand.COMMAND_WORD:
            return new RemoveAppointmentFromPetParser().parse(arguments);

        case AddVetTechToAppointmentCommand.COMMAND_WORD:
            return new AddVetTechToAppointmentCommandParser().parse(arguments);

        case RemoveVetTechFromAppointmentCommand.COMMAND_WORD:
            return new RemoveVetTechFromAppointmentCommandParser().parse(arguments);

        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
