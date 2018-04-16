package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.TabPane;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddPictureCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearEventsCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.GoogleCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.JumpToCommand;
import seedu.address.logic.commands.ListAllEventsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResetPictureCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortEventCommand;
import seedu.address.logic.commands.SwitchTabCommand;
import seedu.address.logic.commands.TaskAddCommand;
import seedu.address.logic.commands.TaskClearCommand;
import seedu.address.logic.commands.TaskDeleteCommand;
import seedu.address.logic.commands.TaskEditCommand;
import seedu.address.logic.commands.TaskFindCommand;
import seedu.address.logic.commands.TaskListCommand;
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
    private TabPane tabPane;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws IllegalValueException {
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

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case ExportCommand.COMMAND_WORD:
        case ExportCommand.COMMAND_ALIAS:
            return new ExportCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();

        case GoogleCommand.COMMAND_WORD:
        case GoogleCommand.COMMAND_ALIAS:
            return new GoogleCommandParser().parse(arguments);

        case DeleteByNameCommand.COMMAND_WORD:
            return new DeleteByNameCommandParser().parse(arguments);

        case ConvertCommand.COMMAND_WORD:
        case ConvertCommand.COMMAND_ALIAS:
            return new ConvertCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case FindEventCommand.COMMAND_WORD:
            return new FindEventCommandParser().parse(arguments);

        case ClearEventsCommand.COMMAND_WORD:
            return new ClearEventsCommand();

        case SortEventCommand.COMMAND_WORD:
        case SortEventCommand.COMMAND_WORD_TWO:
            return new SortEventCommandParser().parse(arguments);

        case ListAllEventsCommand.COMMAND_WORD:
        case ListAllEventsCommand.COMMAND_WORD_TWO:
        case ListAllEventsCommand.COMMAND_WORD_THREE:
            return new ListAllEventsCommand();

        case SwitchTabCommand.COMMAND_WORD:
        case SwitchTabCommand.COMMAND_WORD_TWO:
            return new SwitchTabCommand(tabPane);

        case JumpToCommand.COMMAND_WORD:
        case JumpToCommand.COMMAND_ALIAS:
            return new JumpToCommandParser().parse(arguments);

        case AddPictureCommand.COMMAND_WORD:
            return new AddPictureCommandParser().parse(arguments);

        case TaskAddCommand.COMMAND_WORD:
        case TaskAddCommand.COMMAND_ALIAS:
            return new TaskAddCommandParser().parse(arguments);

        case TaskDeleteCommand.COMMAND_WORD:
        case TaskDeleteCommand.COMMAND_ALIAS:
            return new TaskDeleteCommandParser().parse(arguments);

        case TaskEditCommand.COMMAND_WORD:
        case TaskEditCommand.COMMAND_ALIAS:
            return new TaskEditCommandParser().parse(arguments);

        case TaskFindCommand.COMMAND_WORD:
        case TaskFindCommand.COMMAND_ALIAS:
            return new TaskFindCommandParser().parse(arguments);

        case TaskListCommand.COMMAND_WORD:
        case TaskListCommand.COMMAND_ALIAS:
            return new TaskListCommand();

        case TaskClearCommand.COMMAND_WORD:
        case TaskClearCommand.COMMAND_ALIAS:
            return new TaskClearCommand();

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case ResetPictureCommand.COMMAND_WORD:
            return new ResetPictureCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Used for passing in the UI TabPane
     */
    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
}
