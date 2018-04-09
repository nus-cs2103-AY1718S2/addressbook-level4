package seedu.address.logic.parser;

//@@author jas5469

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ListTagMembersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListTagMembersCommand object
 */
public class ListTagMembersCommandParser implements Parser<ListTagMembersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListTagMembersCommand
     * and returns an ListTagMembersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListTagMembersCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTagMembersCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ListTagMembersCommand(new TagContainKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
