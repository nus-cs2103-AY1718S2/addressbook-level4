package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.AddSubtaskCommand;
import seedu.organizer.logic.commands.ClearCommand;
import seedu.organizer.logic.commands.Command;
import seedu.organizer.logic.commands.DeleteCommand;
import seedu.organizer.logic.commands.DeleteSubtaskCommand;
import seedu.organizer.logic.commands.EditCommand;
import seedu.organizer.logic.commands.ExitCommand;
import seedu.organizer.logic.commands.FindDeadlineCommand;
import seedu.organizer.logic.commands.FindDescriptionCommand;
import seedu.organizer.logic.commands.FindMultipleFieldsCommand;
import seedu.organizer.logic.commands.FindNameCommand;
import seedu.organizer.logic.commands.HelpCommand;
import seedu.organizer.logic.commands.HistoryCommand;
import seedu.organizer.logic.commands.ListCommand;
import seedu.organizer.logic.commands.RedoCommand;
import seedu.organizer.logic.commands.SelectCommand;
import seedu.organizer.logic.commands.ToggleCommand;
import seedu.organizer.logic.commands.ToggleSubtaskCommand;
import seedu.organizer.logic.commands.UndoCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class OrganizerParser {

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

        case ToggleCommand.COMMAND_WORD:
            return new ToggleCommandParser().parse(arguments);

        case ToggleCommand.COMMAND_ALIAS:
            return new ToggleCommandParser().parse(arguments);

        case ToggleSubtaskCommand.COMMAND_WORD:
            return new ToggleSubtaskCommandParser().parse(arguments);

        case ToggleSubtaskCommand.COMMAND_ALIAS:
            return new ToggleSubtaskCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DeleteSubtaskCommand.COMMAND_WORD:
            return new DeleteSubtaskCommandParser().parse(arguments);

        case DeleteSubtaskCommand.COMMAND_ALIAS:
            return new DeleteSubtaskCommandParser().parse(arguments);

        case AddSubtaskCommand.COMMAND_WORD:
            return new AddSubtaskCommandParser().parse(arguments);

        case AddSubtaskCommand.COMMAND_ALIAS:
            return new AddSubtaskCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindMultipleFieldsCommand.COMMAND_WORD:
            return new FindMultipleFieldsCommandParser().parse(arguments);

        case FindMultipleFieldsCommand.COMMAND_ALIAS:
            return new FindMultipleFieldsCommandParser().parse(arguments);

        case FindNameCommand.COMMAND_WORD:
            return new FindNameCommandParser().parse(arguments);

        case FindNameCommand.COMMAND_ALIAS:
            return new FindNameCommandParser().parse(arguments);

        case FindDescriptionCommand.COMMAND_WORD:
            return new FindDescriptionCommandParser().parse(arguments);

        case FindDescriptionCommand.COMMAND_ALIAS:
            return new FindDescriptionCommandParser().parse(arguments);

        case FindDeadlineCommand.COMMAND_WORD:
            return new FindDeadlineCommandParser().parse(arguments);

        case FindDeadlineCommand.COMMAND_ALIAS:
            return new FindDeadlineCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListCommand.COMMAND_ALIAS:
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

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
