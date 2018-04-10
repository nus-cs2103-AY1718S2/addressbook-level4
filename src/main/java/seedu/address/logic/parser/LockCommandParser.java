package seedu.address.logic.parser;
//@@author crizyli
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse arguments for LockCommand
 */
public class LockCommandParser implements Parser<LockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LockCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
        }

        return new LockCommand();
    }
}
