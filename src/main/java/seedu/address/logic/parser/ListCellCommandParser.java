//@@author sarahgoh97
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsCellPredicate;

/**
 * Parses the given input and gives a new ListCellCommand object.
 */
public class ListCellCommandParser implements Parser<ListCellCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of ListCellCommand
     * and returns a ListCellCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public ListCellCommand parse(String args) throws ParseException {
        args = args.trim();
        if (args.matches("\\d+-\\d+")) {
            return new ListCellCommand(new AddressContainsCellPredicate(args), args);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCellCommand.MESSAGE_USAGE));
        }
    }
}
