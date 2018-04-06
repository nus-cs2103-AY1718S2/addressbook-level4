//@@author ewaldhew
package seedu.address.logic.parser;

import seedu.address.logic.commands.NotifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NotifyCommand object
 */
public class NotifyCommandParser implements Parser<NotifyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotifyCommand
     * and returns an NotifyCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NotifyCommand parse(String args) throws ParseException {
        return new NotifyCommand(args);
    }
}
