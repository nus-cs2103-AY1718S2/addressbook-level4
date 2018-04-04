package seedu.address.logic.parser;

//@@author jas5469

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ListGroupMembersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainKeywordsPredicate;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListGroupMembersCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ListGroupMembersCommand(new TagContainKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
