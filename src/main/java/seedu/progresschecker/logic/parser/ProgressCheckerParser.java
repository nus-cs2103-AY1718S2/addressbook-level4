package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.progresschecker.logic.commands.AddDefaultTasksCommand.DEFAULT_LIST_TITLE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.progresschecker.logic.commands.AddCommand;
import seedu.progresschecker.logic.commands.AddDefaultTasksCommand;
import seedu.progresschecker.logic.commands.AnswerCommand;
import seedu.progresschecker.logic.commands.ClearCommand;
import seedu.progresschecker.logic.commands.CloseIssueCommand;
import seedu.progresschecker.logic.commands.Command;
import seedu.progresschecker.logic.commands.CompleteTaskCommand;
import seedu.progresschecker.logic.commands.CreateIssueCommand;
import seedu.progresschecker.logic.commands.DeleteCommand;
import seedu.progresschecker.logic.commands.EditCommand;
import seedu.progresschecker.logic.commands.EditIssueCommand;
import seedu.progresschecker.logic.commands.ExitCommand;
import seedu.progresschecker.logic.commands.FindCommand;
import seedu.progresschecker.logic.commands.GitLoginCommand;
import seedu.progresschecker.logic.commands.GitLogoutCommand;
import seedu.progresschecker.logic.commands.GoToTaskUrlCommand;
import seedu.progresschecker.logic.commands.HelpCommand;
import seedu.progresschecker.logic.commands.HistoryCommand;
import seedu.progresschecker.logic.commands.ListCommand;
import seedu.progresschecker.logic.commands.ListIssuesCommand;
import seedu.progresschecker.logic.commands.RedoCommand;
import seedu.progresschecker.logic.commands.ReopenIssueCommand;
import seedu.progresschecker.logic.commands.ResetTaskCommand;
import seedu.progresschecker.logic.commands.SelectCommand;
import seedu.progresschecker.logic.commands.SortCommand;
import seedu.progresschecker.logic.commands.ThemeCommand;
import seedu.progresschecker.logic.commands.UndoCommand;
import seedu.progresschecker.logic.commands.UploadCommand;
import seedu.progresschecker.logic.commands.ViewCommand;
import seedu.progresschecker.logic.commands.ViewTaskListCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ProgressCheckerParser {

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
        final String commandWordInLowerCase = commandWord.toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWordInLowerCase) {

        case AddDefaultTasksCommand.COMMAND_WORD:
        case AddDefaultTasksCommand.COMMAND_ALIAS:
            return new AddDefaultTasksCommand(DEFAULT_LIST_TITLE);

        case ViewTaskListCommand.COMMAND_WORD:
        case ViewTaskListCommand.COMMAND_ALIAS:
            return new ViewTaskListCommandParser().parse(arguments);

        case CompleteTaskCommand.COMMAND_WORD:
        case CompleteTaskCommand.COMMAND_ALIAS:
            return new CompleteTaskCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_ALIAS:
            return new ThemeCommandParser().parse(arguments);

        case ResetTaskCommand.COMMAND_WORD:
        case ResetTaskCommand.COMMAND_ALIAS:
            return new ResetTaskCommandParser().parse(arguments);

        case GoToTaskUrlCommand.COMMAND_WORD:
        case GoToTaskUrlCommand.COMMAND_ALIAS:
            return new GoToTaskUrlCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case AnswerCommand.COMMAND_WORD:
        case AnswerCommand.COMMAND_ALIAS:
            return new AnswerCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case EditIssueCommand.COMMAND_WORD:
        case EditIssueCommand.COMMAND_ALIAS:
            return new EditIssueCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case UploadCommand.COMMAND_WORD:
        case UploadCommand.COMMAND_ALIAS:
            return new UploadCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case CreateIssueCommand.COMMAND_WORD:
        case CreateIssueCommand.COMMAND_ALIAS:
            return new CreateIssueParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();

        case ViewCommand.COMMAND_WORD:
        case ViewCommand.COMMAND_ALIAS:
            return new ViewCommandParser().parse(arguments);

        case ReopenIssueCommand.COMMAND_WORD:
        case ReopenIssueCommand.COMMAND_ALIAS:
            return new ReopenIssueCommandParser().parse(arguments);

        case CloseIssueCommand.COMMAND_WORD:
        case CloseIssueCommand.COMMAND_ALIAS:
            return new CloseIssueCommandParser().parse(arguments);

        case GitLoginCommand.COMMAND_WORD:
        case GitLoginCommand.COMMAND_ALIAS:
            return new GitLoginCommandParser().parse(arguments);

        case GitLogoutCommand.COMMAND_WORD:
        case GitLogoutCommand.COMMAND_ALIAS:
            return new GitLogoutCommand();

        case ListIssuesCommand.COMMAND_WORD:
        case ListIssuesCommand.COMMAND_ALIAS:
            return new ListIssuesCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
