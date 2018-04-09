package seedu.address.logic.parser;

//@@author jas5469

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.ListTagMembersCommand;
import seedu.address.model.person.TagContainKeywordsPredicate;

public class ListTagMembersCommandParserTest {

    private ListTagMembersCommandParser parser = new ListTagMembersCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListTagMembersCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListTagMembersCommand() {
        // no leading and trailing whitespaces
        ListTagMembersCommand expectedListTagMembersCommand =
                new ListTagMembersCommand(new TagContainKeywordsPredicate(Arrays.asList("friends", "CS3230")));
        assertParseSuccess(parser, "friends CS3230", expectedListTagMembersCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t CS3230  \t", expectedListTagMembersCommand);
    }

}
