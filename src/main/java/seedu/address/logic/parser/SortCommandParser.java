package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author neilish3re

/** SortCommand Parser is implemented from Parser
 *
 */
public class SortCommandParser implements Parser<SortCommand> {
    /** Parses given arguments in SortCommand context and returns sortCommand object to execute
     * throws an exception(ParseException) if user input format is invalid
     */

    public SortCommand parse(String args) throws ParseException {
        try {
            boolean isSort = ParserUtil.parseSort(args);
            return new SortCommand(isSort);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}

