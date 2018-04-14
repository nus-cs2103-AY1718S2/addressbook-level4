package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

//@@author jingyinno
/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {
    private static final String SPLIT_TOKEN = "\\s+";
    private static final int CORRECT_ARGS_LENGTH = 2;
    private static final int COMMAND_INDEX = 0;
    private static final int ALIAS_INDEX = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        String[] trimmedArgs = validateNumberOfArgs(args);
        try {
            Alias aliasCreated = ParserUtil.parseAlias(trimmedArgs[COMMAND_INDEX], trimmedArgs[ALIAS_INDEX]);
            return new AliasCommand(aliasCreated);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns a String Array of valid number of elements after slicing the user input.
     */
    private String[] validateNumberOfArgs(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
        if (splitArgs.length != CORRECT_ARGS_LENGTH) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }
        return splitArgs;
    }
}
