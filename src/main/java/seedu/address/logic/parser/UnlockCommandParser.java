package seedu.address.logic.parser;
//@@author crizyli
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for the UnlockCommand'
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnlockCommand
     * and returns an UnlockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnlockCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
        }

        return new UnlockCommand();
    }
}
