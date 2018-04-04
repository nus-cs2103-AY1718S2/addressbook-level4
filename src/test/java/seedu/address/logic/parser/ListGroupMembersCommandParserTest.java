package seedu.address.logic.parser;

//@@author jas5469

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListGroupMembersCommand;
import seedu.address.model.person.TagContainKeywordsPredicate;

public class ListGroupMembersCommandParserTest {

    private ListGroupMembersCommandParser parser = new ListGroupMembersCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListGroupMembersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListGroupMembersCommand() {
        // no leading and trailing whitespaces
        ListGroupMembersCommand expectedListGroupMembersCommand =
                new ListGroupMembersCommand(new TagContainKeywordsPredicate(Arrays.asList("friends", "CS3230")));
        assertParseSuccess(parser, "friends CS3230", expectedListGroupMembersCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t CS3230  \t", expectedListGroupMembersCommand);
    }

}
