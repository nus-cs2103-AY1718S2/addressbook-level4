//@@author ZhangYijiong
package seedu.address.logic.parser;

import seedu.address.logic.commands.ProcessNextCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ProcessOrderCommand object
 */
public class ProcessNextCommandParser implements Parser<ProcessNextCommand> {

    /**
     * Parses the given {@code String} (String is none in this case)of arguments in the context
     * of the ProcessOrderCommand and returns an ProcessOrderCommand object for execution.
     */
    public ProcessNextCommand parse(String args) throws ParseException {
        return new ProcessNextCommand();
    }
}

