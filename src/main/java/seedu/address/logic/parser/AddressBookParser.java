package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.DeleteTransactionCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListPersonsCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectPersonCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final Model model;

    public AddressBookParser(Model model) {
        this.model = model;
    }

    //@@author
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, CommandException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord.toLowerCase()) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments, model);

        case AddTransactionCommand.COMMAND_WORD:
            return new AddTransactionCommandParser().parse(arguments, model);

        case EditPersonCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, model);

        case SelectPersonCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments, model);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments, model);

        case DeleteTransactionCommand.COMMAND_WORD:
            return new DeleteTransactionCommandParser().parse(arguments, model);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments, model);

        case ListPersonsCommand.COMMAND_WORD:
            return new ListPersonsCommand();

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
