//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
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

