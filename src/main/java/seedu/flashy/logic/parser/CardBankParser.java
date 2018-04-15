package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.flashy.logic.commands.AddCardCommand;
import seedu.flashy.logic.commands.AnswerCommand;
import seedu.flashy.logic.commands.ChangeThemeCommand;
import seedu.flashy.logic.commands.ClearCommand;
import seedu.flashy.logic.commands.Command;
import seedu.flashy.logic.commands.DeleteCardCommand;
import seedu.flashy.logic.commands.DeleteCommand;
import seedu.flashy.logic.commands.EditCardCommand;
import seedu.flashy.logic.commands.EditCommand;
import seedu.flashy.logic.commands.ExitCommand;
import seedu.flashy.logic.commands.FindCommand;
import seedu.flashy.logic.commands.HelpCommand;
import seedu.flashy.logic.commands.HistoryCommand;
import seedu.flashy.logic.commands.ListCommand;
import seedu.flashy.logic.commands.RedoCommand;
import seedu.flashy.logic.commands.ScheduleCommand;
import seedu.flashy.logic.commands.SelectCardCommand;
import seedu.flashy.logic.commands.SelectCommand;
import seedu.flashy.logic.commands.ShowDueCommand;
import seedu.flashy.logic.commands.UndoCommand;
import seedu.flashy.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CardBankParser {

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

        case AnswerCommand.COMMAND_WORD:
            return new AnswerCommandParser().parse(arguments);

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
            return new ListCommandParser().parse(arguments);

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        case ShowDueCommand.COMMAND_WORD:
            return new ShowDueCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:

        case ExitCommand.COMMAND_WORD_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ChangeThemeCommand.COMMAND_WORD:
            return new ChangeThemeCommandParser().parse(arguments);

        case AddCardCommand.COMMAND_WORD:
            return new AddCardCommandParser().parse(arguments);

        case DeleteCardCommand.COMMAND_WORD:
            return new DeleteCardCommandParser().parse(arguments);

        case EditCardCommand.COMMAND_WORD:
            return new EditCardCommandParser().parse(arguments);

        case SelectCardCommand.COMMAND_WORD:
            return new SelectCardCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
