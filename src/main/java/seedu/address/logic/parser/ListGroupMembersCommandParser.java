//@@author jas5469
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ListGroupMembersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.group.MembersInGroupPredicate;

/**
 * Parses input arguments and creates a new ListGroupMembersCommand object
 */
public class ListGroupMembersCommandParser implements Parser<ListGroupMembersCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the ListGroupMembersCommand
     * and returns an ListGroupMembersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListGroupMembersCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListGroupMembersCommand.MESSAGE_USAGE));
        }
        try {
            Information information = ParserUtil.parseInformation(args);
            Group group = new Group(information);
            return new ListGroupMembersCommand(new MembersInGroupPredicate(group), group);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
