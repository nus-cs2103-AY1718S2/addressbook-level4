package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.organizer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND_OR_NO_USER;
import static seedu.organizer.model.ModelManager.getCurrentlyLoggedInUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.AddQuestionAnswerCommand;
import seedu.organizer.logic.commands.AddSubtaskCommand;
import seedu.organizer.logic.commands.AnswerCommand;
import seedu.organizer.logic.commands.ClearCommand;
import seedu.organizer.logic.commands.Command;
import seedu.organizer.logic.commands.CurrentMonthCommand;
import seedu.organizer.logic.commands.DeleteCommand;
import seedu.organizer.logic.commands.DeleteRecurredTasksCommand;
import seedu.organizer.logic.commands.DeleteSubtaskCommand;
import seedu.organizer.logic.commands.EditCommand;
import seedu.organizer.logic.commands.EditSubtaskCommand;
import seedu.organizer.logic.commands.ExitCommand;
import seedu.organizer.logic.commands.FindDeadlineCommand;
import seedu.organizer.logic.commands.FindDescriptionCommand;
import seedu.organizer.logic.commands.FindMultipleFieldsCommand;
import seedu.organizer.logic.commands.FindNameCommand;
import seedu.organizer.logic.commands.ForgotPasswordCommand;
import seedu.organizer.logic.commands.HelpCommand;
import seedu.organizer.logic.commands.HistoryCommand;
import seedu.organizer.logic.commands.ListCommand;
import seedu.organizer.logic.commands.ListCompletedTasksCommand;
import seedu.organizer.logic.commands.ListUncompletedTasksCommand;
import seedu.organizer.logic.commands.LoginCommand;
import seedu.organizer.logic.commands.LogoutCommand;
import seedu.organizer.logic.commands.NextMonthCommand;
import seedu.organizer.logic.commands.PreviousMonthCommand;
import seedu.organizer.logic.commands.RecurWeeklyCommand;
import seedu.organizer.logic.commands.RedoCommand;
import seedu.organizer.logic.commands.RemoveTagsCommand;
import seedu.organizer.logic.commands.SignUpCommand;
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

        if (getCurrentlyLoggedInUser() == null) {
            switch (commandWord) {

            case SignUpCommand.COMMAND_WORD:
                return new SignUpCommandParser().parse(arguments);

            case SignUpCommand.COMMAND_ALIAS:
                return new SignUpCommandParser().parse(arguments);

            case LoginCommand.COMMAND_WORD:
                return new LoginCommandParser().parse(arguments);

            case LoginCommand.COMMAND_ALIAS:
                return new LoginCommandParser().parse(arguments);

            case ForgotPasswordCommand.COMMAND_WORD:
                return new ForgotPasswordCommandParser().parse(arguments);

            case ForgotPasswordCommand.COMMAND_ALIAS:
                return new ForgotPasswordCommandParser().parse(arguments);

            case AnswerCommand.COMMAND_WORD:
                return new AnswerCommandParser().parse(arguments);

            case AnswerCommand.COMMAND_ALIAS:
                return new AnswerCommandParser().parse(arguments);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            default :
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND_OR_NO_USER);
            }
        }

        switch (commandWord) {

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case LogoutCommand.COMMAND_ALIAS:
            return new LogoutCommand();

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        //@@author agus
        case EditSubtaskCommand.COMMAND_WORD:
            return new EditSubtaskCommandParser().parse(arguments);

        case EditSubtaskCommand.COMMAND_ALIAS:
            return new EditSubtaskCommandParser().parse(arguments);

        case ToggleCommand.COMMAND_WORD:
            return new ToggleCommandParser().parse(arguments);

        case ToggleCommand.COMMAND_ALIAS:
            return new ToggleCommandParser().parse(arguments);

        case ToggleSubtaskCommand.COMMAND_WORD:
            return new ToggleSubtaskCommandParser().parse(arguments);

        case ToggleSubtaskCommand.COMMAND_ALIAS:
            return new ToggleSubtaskCommandParser().parse(arguments);
        //@@author

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        //@@author natania
        case DeleteRecurredTasksCommand.COMMAND_WORD:
            return new DeleteRecurredTasksCommandParser().parse(arguments);

        case DeleteRecurredTasksCommand.COMMAND_ALIAS:
            return new DeleteRecurredTasksCommandParser().parse(arguments);

        //@@author agus
        case DeleteSubtaskCommand.COMMAND_WORD:
            return new DeleteSubtaskCommandParser().parse(arguments);

        case DeleteSubtaskCommand.COMMAND_ALIAS:
            return new DeleteSubtaskCommandParser().parse(arguments);

        case AddSubtaskCommand.COMMAND_WORD:
            return new AddSubtaskCommandParser().parse(arguments);

        case AddSubtaskCommand.COMMAND_ALIAS:
            return new AddSubtaskCommandParser().parse(arguments);
        //@@author

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

        case ListUncompletedTasksCommand.COMMAND_WORD:
            return new ListUncompletedTasksCommand();

        case ListUncompletedTasksCommand.COMMAND_ALIAS:
            return new ListUncompletedTasksCommand();

        case ListCompletedTasksCommand.COMMAND_WORD:
            return new ListCompletedTasksCommand();

        case ListCompletedTasksCommand.COMMAND_ALIAS:
            return new ListCompletedTasksCommand();

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

        case PreviousMonthCommand.COMMAND_WORD:
            return new PreviousMonthCommand();

        case PreviousMonthCommand.COMMAND_ALIAS:
            return new PreviousMonthCommand();

        case NextMonthCommand.COMMAND_WORD:
            return new NextMonthCommand();

        case NextMonthCommand.COMMAND_ALIAS:
            return new NextMonthCommand();

        case AddQuestionAnswerCommand.COMMAND_WORD:
            return new AddQuestionAnswerCommandParser().parse(arguments);

        case CurrentMonthCommand.COMMAND_WORD:
            return new CurrentMonthCommand();

        case CurrentMonthCommand.COMMAND_ALIAS:
            return new CurrentMonthCommand();

        //@@author natania
        case RecurWeeklyCommand.COMMAND_WORD:
            return new RecurWeeklyCommandParser().parse(arguments);

        case RecurWeeklyCommand.COMMAND_ALIAS:
            return new RecurWeeklyCommandParser().parse(arguments);

        case RemoveTagsCommand.COMMAND_WORD:
            return new RemoveTagsCommandParser().parse(arguments);

        case RemoveTagsCommand.COMMAND_ALIAS:
            return new RemoveTagsCommandParser().parse(arguments);
        //@@author

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
