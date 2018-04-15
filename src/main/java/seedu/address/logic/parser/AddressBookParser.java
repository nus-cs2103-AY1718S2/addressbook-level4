package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteInterviewCommand;
import seedu.address.logic.commands.DeleteJobCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditJobCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FacebookLoginCommand;
import seedu.address.logic.commands.FacebookPostCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindInterviewCommand;
import seedu.address.logic.commands.FindJobCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListInterviewCommand;
import seedu.address.logic.commands.ListJobsCommand;
import seedu.address.logic.commands.MatchJobCommand;
import seedu.address.logic.commands.PostJobCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SaveReportCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewReportCommand;
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
        case FacebookLoginCommand.COMMAND_WORD:
            return new FacebookLoginCommand();

        case FacebookLoginCommand.COMMAND_ALIAS:
            return new FacebookLoginCommand();

        case FacebookPostCommand.COMMAND_WORD:
            return new FacebookPostCommandParser().parse(arguments);

        case FacebookPostCommand.COMMAND_ALIAS:
            return new FacebookPostCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_ALIAS:
            return new RemarkCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_ALIAS:
            return new ThemeCommandParser().parse(arguments);

        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case PostJobCommand.COMMAND_WORD:
            return new PostJobCommandParser().parse(arguments);

        case PostJobCommand.COMMAND_ALIAS:
            return new PostJobCommandParser().parse(arguments);

        case MatchJobCommand.COMMAND_WORD:
            return new MatchJobCommandParser().parse(arguments);

        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditJobCommand.COMMAND_ALIAS:
            return new EditJobCommandParser().parse(arguments);

        case EditJobCommand.COMMAND_WORD:
            return new EditJobCommandParser().parse(arguments);

        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case DeleteJobCommand.COMMAND_WORD:
            return new DeleteJobCommandParser().parse(arguments);

        case DeleteJobCommand.COMMAND_ALIAS:
            return new DeleteJobCommandParser().parse(arguments);

        case DeleteInterviewCommand.COMMAND_WORD:
            return new DeleteInterviewCommandParser().parse(arguments);

        case DeleteInterviewCommand.COMMAND_ALIAS:
            return new DeleteInterviewCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindJobCommand.COMMAND_WORD:
            return new FindJobCommandParser().parse(arguments);

        case FindInterviewCommand.COMMAND_WORD:
            return new FindInterviewCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListInterviewCommand.COMMAND_WORD:
            return new ListInterviewCommand();

        case ListJobsCommand.COMMAND_WORD:
            return new ListJobsCommand();

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

        case ViewReportCommand.COMMAND_WORD:
            return new ViewReportCommandParser().parse(arguments);

        case ViewReportCommand.COMMAND_ALIAS:
            return new ViewReportCommandParser().parse(arguments);

        case SaveReportCommand.COMMAND_WORD:
            return new SaveReportCommandParser().parse(arguments);

        case SaveReportCommand.COMMAND_ALIAS:
            return new SaveReportCommandParser().parse(arguments);

        case AddInterviewCommand.COMMAND_WORD :
            return new AddInterviewCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
