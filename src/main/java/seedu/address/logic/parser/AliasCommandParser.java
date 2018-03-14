package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;


import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    List<String> commands = Arrays.asList(AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD, AliasCommand.COMMAND_WORD);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        args = args.trim();
        String[] trimmedArgs = args.split("\\s+");
        String command = trimmedArgs[0];
        if (trimmedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        } else if (!commands.contains(command)) {
            throw new ParseException(
                    String.format(AliasCommand.MESSAGE_INVALID_COMMAND, AliasCommand.MESSAGE_INVALID_COMMAND_DESCRIPTION));
        }

        Alias alias = new Alias(trimmedArgs[0], trimmedArgs[1]);
        return new AliasCommand(alias);
    }

}
