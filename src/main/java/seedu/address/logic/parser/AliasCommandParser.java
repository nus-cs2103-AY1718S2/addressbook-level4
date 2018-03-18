package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    private List<String> commands = Arrays.asList(AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
            SelectCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, AliasCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        args = args.trim();
        String[] trimmedArgs = args.split("\\s+");
        String command = trimmedArgs[0];
        String alias = trimmedArgs[1];
        if (trimmedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        } else if (!commands.contains(command)) {
            throw new ParseException(
                    String.format(AliasCommand.MESSAGE_INVALID_COMMAND,
                            AliasCommand.MESSAGE_INVALID_COMMAND_DESCRIPTION));
        } else if (commands.contains(alias)) {
            throw new ParseException(
                    String.format(AliasCommand.MESSAGE_INVALID_ALIAS,
                            AliasCommand.MESSAGE_INVALID_ALIAS_DESCRIPTION));
        }

        Alias aliasCreated = new Alias(command, alias);
        return new AliasCommand(aliasCreated);
    }

}
