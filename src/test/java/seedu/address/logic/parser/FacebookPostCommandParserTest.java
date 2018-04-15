package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FacebookPostCommand;

//@@author tiny767
public class FacebookPostCommandParserTest {
    private FacebookPostCommandParser parser = new FacebookPostCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, FacebookPostCommand.EXAMPLE_POST,
                new FacebookPostCommand(FacebookPostCommand.EXAMPLE_POST));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FacebookPostCommand.MESSAGE_USAGE));
    }
}
