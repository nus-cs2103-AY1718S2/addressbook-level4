package seedu.address.logic.parser;

import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jasmoon
/**
 * Parses input arguments and create a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public final ArrayList<String> availableCommands;

    /**
     * HelpCommandParser constructor - creates an ArrayList which contains all commands open to the help function.
     */
    public HelpCommandParser()  {
        availableCommands = new ArrayList<>();
        availableCommands.add(TaskCommand.COMMAND_WORD);
        availableCommands.add(EventCommand.COMMAND_WORD);
        availableCommands.add(RemoveCommand.COMMAND_WORD);
        availableCommands.add(RemoveCommand.COMMAND_ALIAS);
        availableCommands.add(EditCommand.COMMAND_WORD);
        availableCommands.add(FindCommand.COMMAND_WORD);
        availableCommands.add(CompleteCommand.COMMAND_WORD);
        availableCommands.add(HelpCommand.COMMAND_WORD);
        availableCommands.add(HelpCommand.COMMAND_ALIAS);
        availableCommands.add(ListCommand.COMMAND_WORD);
        availableCommands.add(ListCommand.COMMAND_ALIAS);
        availableCommands.add(UndoCommand.COMMAND_WORD);
        availableCommands.add(UndoCommand.COMMAND_ALIAS);
        availableCommands.add(RedoCommand.COMMAND_WORD);
        availableCommands.add(RedoCommand.COMMAND_ALIAS);
        availableCommands.add(ClearCommand.COMMAND_WORD);
        availableCommands.add(ClearCommand.COMMAND_ALIAS);
        availableCommands.add(ImportCommand.COMMAND_WORD);
        availableCommands.add(ExportCommand.COMMAND_WORD);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public HelpCommand parse(String args) throws ParseException {

        String commandRequest = args.trim();
        if (commandRequest.length() == 0) {
            return new HelpCommand();
        } else {
            if (availableCommands.contains(commandRequest)) {
                return new HelpCommand(args);
            } else {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_HELP_REQUEST, commandRequest));
            }
        }
    }
}
