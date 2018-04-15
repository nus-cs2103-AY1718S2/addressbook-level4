//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ProcessOrderCommand object
 */
public class LoadCommandParser implements Parser<LoadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context
     * of the ProcessOrderCommand and returns an ProcessOrderCommand object for execution.
     */
    public LoadCommand parse(String args) throws ParseException {
        if (args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }

        return new LoadCommand(args.trim());
    }
}


