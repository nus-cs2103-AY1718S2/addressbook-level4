package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

//@@author jingyinno
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        args = args.trim();
        String[] trimmedArgs = args.split("\\s+");
        if (trimmedArgs.length == 1 && trimmedArgs[0].equals(AliasCommand.LIST_ALIAS_COMMAND_WORD)) {
            return new AliasCommand();
        }
        if (trimmedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        try {
            Alias aliasCreated = ParserUtil.parseAlias(trimmedArgs[0], trimmedArgs[1]);
            return new AliasCommand(aliasCreated);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
