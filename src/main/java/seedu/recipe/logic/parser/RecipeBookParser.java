package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.recipe.logic.commands.AccessTokenCommand;
import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.logic.commands.ChangeThemeCommand;
import seedu.recipe.logic.commands.ClearCommand;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.DeleteCommand;
import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.ExitCommand;
import seedu.recipe.logic.commands.FindCommand;
import seedu.recipe.logic.commands.GroupCommand;
import seedu.recipe.logic.commands.HelpCommand;
import seedu.recipe.logic.commands.HistoryCommand;
import seedu.recipe.logic.commands.IngredientCommand;
import seedu.recipe.logic.commands.ListCommand;
import seedu.recipe.logic.commands.ParseCommand;
import seedu.recipe.logic.commands.RedoCommand;
import seedu.recipe.logic.commands.SearchCommand;
import seedu.recipe.logic.commands.SelectCommand;
import seedu.recipe.logic.commands.ShareCommand;
import seedu.recipe.logic.commands.TagCommand;
import seedu.recipe.logic.commands.UndoCommand;
import seedu.recipe.logic.commands.UploadCommand;
import seedu.recipe.logic.commands.ViewGroupCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RecipeBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>[\\S\\s]*)");

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

        case ShareCommand.COMMAND_WORD:
            return new ShareCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
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

        case UploadCommand.COMMAND_WORD:
            return new UploadCommandParser().parse(arguments);

        case ChangeThemeCommand.COMMAND_WORD:
            return new ChangeThemeCommand();

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case AccessTokenCommand.COMMAND_WORD:
            return new AccessTokenCommandParser().parse(arguments);

        case GroupCommand.COMMAND_WORD:
            return new GroupCommandParser().parse(arguments);

        case ViewGroupCommand.COMMAND_WORD:
            return new ViewGroupCommandParser().parse(arguments);

        case ParseCommand.COMMAND_WORD:
            return new ParseCommand();

        case IngredientCommand.COMMAND_WORD:
            return new IngredientCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
