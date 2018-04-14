package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SearchCommand;

//@@author Nethergale
public class SearchCommandParserTest {

    private static final String INVALID_SEARCH_NAME = "a%b2$c";
    private static final String VALID_SEARCH_NAME = "abc";
    private static final String TWITTER_PLATFORM = "twitter";
    private static final String FACEBOOK_PLATFORM_ALIAS = "fb";

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_noPlatformSpecifiedInvalidSearchName_failure() {
        assertParseFailure(parser, INVALID_SEARCH_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPlatformSpecifiedValidSearchName_success() {
        assertParseSuccess(parser, VALID_SEARCH_NAME, new SearchCommand("all", VALID_SEARCH_NAME));
    }

    @Test
    public void parse_validPlatformSpecifiedInvalidSearchName_failure() {
        assertParseFailure(parser, TWITTER_PLATFORM + ", " + INVALID_SEARCH_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validPlatformSpecifiedValidSearchName_success() {
        assertParseSuccess(parser, FACEBOOK_PLATFORM_ALIAS + ", " + VALID_SEARCH_NAME,
                new SearchCommand(FACEBOOK_PLATFORM_ALIAS, VALID_SEARCH_NAME));
    }

    @Test
    public void parse_invalidPlatformSpecifiedValidSearchName_failure() {
        String invalidPlatform = "aha";
        assertParseFailure(parser, invalidPlatform + ", " + VALID_SEARCH_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,  "yo, test, this, command",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }
}
