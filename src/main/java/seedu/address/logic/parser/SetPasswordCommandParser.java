package seedu.address.logic.parser;
//@@author crizyli
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for the SetPasswordCommand'
 */
public class SetPasswordCommandParser implements Parser<SetPasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetPasswordCommand
     * and returns an SetPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetPasswordCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE));
        }

        return new SetPasswordCommand();
    }
}
